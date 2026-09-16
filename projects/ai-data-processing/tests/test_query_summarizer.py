import sys
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / "src"))

import query_summarizer


class QuerySummarizerTests(unittest.TestCase):
    def test_build_summary_prompt_contains_query_and_text(self):
        prompt = query_summarizer.build_summary_prompt(
            "What are the main problems?",
            "The project is delayed because the timeline was underestimated."
        )
        self.assertIn("What are the main problems?", prompt)
        self.assertIn("The project is delayed", prompt)

    def test_local_keyword_summary_returns_expected_result(self):
        text = "The team is facing delays. The project timeline was underestimated. The main issue is communication problems."
        result = query_summarizer.summarize_with_query(text, "What are the main problems?")
        self.assertTrue(len(result) > 0)
        self.assertIn("delays", result.lower())


if __name__ == "__main__":
    unittest.main()
