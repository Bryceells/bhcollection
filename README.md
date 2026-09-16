# BH Collection

This is both a collection of my school projects that I'm proud of or actively working on and an old side project I have in learning how to program up AI in different coding languages.

The AI templates are inside the templates folder as they are separate things I made while just tinkering around an older era of AI usage.

## AI-Assisted Data Processing and Template Repository

This repository includes a personal project focused on AI-assisted summarization and query-driven document analysis.

### Project focuses

- AI-assisted summarization
- Natural-language query processing
- Workflow support for document analysis
- Real LLM API integration with a local fallback

### Current featured project

The project in [projects/ai-data-processing](projects/ai-data-processing) demonstrates a lightweight AI-assisted summarization workflow where a user asks a question and the system analyzes a source document to produce a focused answer.

It can:

- accept a document or text passage
- receive a question like "What are the main issues?"
- send the query and text to a real LLM API when configured
- fall back to a local keyword-based summary if no API key is available

### Example usage

```bash
cd projects/ai-data-processing
python -m venv .venv
.venv\Scripts\Activate.ps1
pip install -r requirements.txt
python src/query_summarizer.py
```

### Example queries

- What are the main problems mentioned?
- Which concerns are most important?
- What do the authors plan to improve?
- What risks or issues are highlighted in the text?

### Setup for the real LLM

```bash
$env:OPENAI_API_KEY="your_api_key_here"
$env:LLM_MODEL="gpt-4o-mini"
python src/query_summarizer.py
```

This repository serves as a practical portfolio-style collection for AI-assisted data processing experiments and workflow support tools.