import java.net.*;
import java.net.http.*;
import java.util.*;
import org.json.*;  // Make sure you have your json library added to your project

public class ChatGPTBase {

    // Replace with your actual OpenAI API key
    private static final String API_KEY = "your-api-key-here";

    // OpenAI chat completions endpoint
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public static void main(String[] args) {
        // Create a reusable HttpClient instance
        HttpClient client = HttpClient.newHttpClient();

        // Scanner to read user input
        Scanner scanner = new Scanner(System.in);

        // Create a list to hold conversation history (messages)
        List<JSONObject> messages = new ArrayList<>();

        // Optional: Add a system prompt (sets the behavior of the assistant)
        messages.add(new JSONObject()
                .put("role", "system")
                .put("content", "You are a helpful assistant."));

        System.out.println("Start chatting with ChatGPT! Type 'quit' to end the session.");

        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye!");
                break;
            }

            // Add user input to message history
            messages.add(new JSONObject()
                    .put("role", "user")
                    .put("content", userInput));

            try {
                // Create JSON payload with model and full message history
                JSONObject json = new JSONObject()
                        .put("model", "gpt-3.5-turbo")
                        .put("messages", new JSONArray(messages));

                // Build the POST request
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + API_KEY)
                        .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                        .build();

                // Send the request and get the response
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Parse the JSON response
                JSONObject responseJson = new JSONObject(response.body());

                // Extract the assistant's reply from the response
                String reply = responseJson
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                        .trim();

                // Print the reply
                System.out.println("ChatGPT: " + reply);

                // Add assistant's reply to the conversation history
                messages.add(new JSONObject()
                        .put("role", "assistant")
                        .put("content", reply));

            } catch (Exception e) {
                // Print any errors (e.g., JSON parsing, network issues, API errors)
                System.out.println("Oops, something went wrong:");
                e.printStackTrace();
            }
        }

        scanner.close();
    }
}