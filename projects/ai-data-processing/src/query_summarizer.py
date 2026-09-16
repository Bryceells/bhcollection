"""Query-based AI-assisted summarization with a real LLM API option.

This project supports a modern workflow where a user asks a question and the
program either:

1. calls a real LLM API when an API key is configured, or
2. falls back to a local keyword-based summarizer when no key is available.
"""

from __future__ import annotations

import json
import os
import re
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
DATA_PATH = ROOT / "data" / "sample_documents.json"
MODEL_NAME = os.getenv("LLM_MODEL", "gpt-4o-mini")
API_BASE_URL = os.getenv("OPENAI_BASE_URL", "https://api.openai.com/v1")


def build_summary_prompt(query: str, document_text: str) -> str:
    """Create the instruction prompt for the LLM summarization request."""
    return (
        "You are a helpful research assistant. Answer the user's query using only the "
        "information in the source text. Keep the response concise, factual, and directly "
        "relevant to the query.\n\n"
        f"Query: {query}\n\n"
        f"Source text:\n{document_text}\n\n"
        "Provide the answer as 2-4 short bullet points or a brief paragraph."
    )


def split_sentences(text: str) -> list[str]:
    """Split a document into sentences for targeted analysis."""
    return [sentence.strip() for sentence in re.split(r"(?<=[.!?])\s+", text) if sentence.strip()]


def clean_query(query: str) -> str:
    """Normalize a query into a simpler lowercase token format."""
    return re.sub(r"[^a-z0-9\s]", " ", query.lower()).strip()


def score_sentence(sentence: str, query: str) -> float:
    """Score a sentence by how strongly it matches the query words."""
    query_tokens = set(clean_query(query).split())
    sentence_tokens = set(clean_query(sentence).split())
    if not query_tokens:
        return 0.0

    overlap = len(query_tokens & sentence_tokens)
    if overlap == 0:
        return 0.0

    return overlap / len(query_tokens)


def summarize_with_query_local(document_text: str, query: str, limit: int = 3) -> str:
    """Return the most relevant sentences for the provided query."""
    sentences = split_sentences(document_text)
    scored = []

    for sentence in sentences:
        score = score_sentence(sentence, query)
        if score > 0:
            scored.append((score, sentence))

    if not scored:
        return "No strong matches were found for this query."

    scored.sort(key=lambda item: item[0], reverse=True)
    summary = [sentence for _, sentence in scored[:limit]]
    return " ".join(summary)


def summarize_with_llm(document_text: str, query: str) -> str:
    """Call a real LLM API when OPENAI_API_KEY is configured."""
    api_key = os.getenv("OPENAI_API_KEY")
    if not api_key:
        return summarize_with_query_local(document_text, query)

    try:
        from openai import OpenAI
    except ImportError:
        return summarize_with_query_local(document_text, query)

    client = OpenAI(api_key=api_key, base_url=API_BASE_URL)
    prompt = build_summary_prompt(query, document_text)

    try:
        response = client.chat.completions.create(
            model=MODEL_NAME,
            messages=[
                {"role": "system", "content": "You are a concise research assistant."},
                {"role": "user", "content": prompt},
            ],
            temperature=0.2,
            max_tokens=250,
        )
        return response.choices[0].message.content.strip() or "No summary generated."
    except Exception:
        return summarize_with_query_local(document_text, query)


def summarize_with_query(document_text: str, query: str, limit: int = 3) -> str:
    """Primary summary method. Uses live LLM if configured, otherwise local fallback."""
    if os.getenv("OPENAI_API_KEY"):
        result = summarize_with_llm(document_text, query)
        if result and result.lower() != "no summary generated.":
            return result

    return summarize_with_query_local(document_text, query, limit=limit)


def load_documents(path: Path = DATA_PATH) -> list[dict[str, str]]:
    """Load sample data from disk."""
    with path.open("r", encoding="utf-8") as file:
        return json.load(file)


def run_demo() -> None:
    """Run a small example using query-driven summarization."""
    documents = load_documents()
    queries = [
        "What are the main problems?",
        "What are the user concerns?",
        "What do they plan to improve?",
    ]

    for document in documents:
        print(f"Document: {document['title']}")
        for query in queries:
            result = summarize_with_query(document["text"], query)
            print(f"Query: {query}")
            print(f"Summary: {result}")
            print("-" * 60)
        print()


if __name__ == "__main__":
    run_demo()
