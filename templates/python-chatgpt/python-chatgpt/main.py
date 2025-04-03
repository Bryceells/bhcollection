from openai import OpenAI

# Initialize the OpenAI client with your API key
client = OpenAI(api_key="your-key-here")  # Replace with your actual OpenAI API key

def chat_with_gpt(prompt):
    """
    Sends a message to OpenAI's Chat Completion API and returns the assistant's response.
    """
    try:
        response = client.chat.completions.create(
            model="gpt-3.5-turbo",  # Choose the model (e.g., "gpt-4" if available)
            messages=[
                {"role": "system", "content": "You are a helpful assistant."},  # Sets assistant's behavior
                {"role": "user", "content": prompt}  # User's input message
            ]
        )
        # Return the assistant's reply text
        return response.choices[0].message.content
    except Exception as e:
        # Handle and return any errors that occur during the API call
        return f"Error: {e}"

# Main loop for terminal-based conversation
if __name__ == "__main__":
    print("🤖 ChatGPT CLI - Type 'exit' or 'quit' to end the conversation.")
    while True:
        # Take user input from the terminal
        user_input = input("You: ")

        # Exit condition
        if user_input.lower() in ["exit", "quit"]:
            print("Goodbye! 👋")
            break

        # Send the input to ChatGPT and print the response
        response = chat_with_gpt(user_input)
        print(f"ChatGPT: {response}")