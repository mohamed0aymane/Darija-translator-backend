package org.mql.darija.translator.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;

public class TranslatorService {

    private static final String GEMINI_API_KEY = "AIzaSyBQPAjIkr6QyT9hDwbny8_ELZr-zga4bK0";

    private static final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final ObjectMapper mapper = new ObjectMapper();
    public String translate(String text) {
    try {
        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/"
                + "gemini-2.0-flash:generateContent?key=" + GEMINI_API_KEY;

        String payload = """
                {
                    "contents": [{
                        "parts": [{
                            "text": "Translate this text from English to Moroccan Darija. Return ONLY the translation: %s"
                        }]
                    }]
                }
                """.formatted(text);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response =
                http.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode json = mapper.readTree(response.body());

        // Check API errors
        if (json.has("error")) {
            return "Gemini API Error: " + json.get("error").get("message").asText();
        }

        JsonNode candidates = json.get("candidates");
        if (candidates == null || candidates.isEmpty()) {
            return "Invalid Gemini response: " + response.body();
        }

        JsonNode textNode = candidates.get(0)
                .get("content")
                .get("parts")
                .get(0)
                .get("text");

        String cleanText = textNode.asText()
        .replace("\n", "")   
        .replace("\r", "")   
        .trim();  

        return cleanText;


    } catch (Exception e) {
        return "Error: " + e.getMessage();
    }
}

    
}