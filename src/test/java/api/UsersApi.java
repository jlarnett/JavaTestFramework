package api;

import context.TestUser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public final class UsersApi {
    private UsersApi() {
    }

    public static TestUser register(String baseUrl, String password) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("BASE_URL is required to register test users.");
        }

        String seed = UUID.randomUUID().toString().replace("-", "");
        String uniquePart = seed.substring(0, 12);
        String email = "autotest+" + uniquePart + "@example.com";
        String displayName = "autotest_" + uniquePart;
        String endpoint = normalizeBaseUrl(baseUrl) + "/api/users/register";

        String requestBody = "{"
                + "\"email\":\"" + escapeJson(email) + "\","
                + "\"displayName\":\"" + escapeJson(displayName) + "\","
                + "\"password\":\"" + escapeJson(password) + "\","
                + "\"confirmPassword\":\"" + escapeJson(password) + "\""
                + "}";

        HttpRequest request = HttpRequest.newBuilder(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IllegalStateException("Register API failed with status " + response.statusCode() + ": " + response.body());
            }

            String responseBody = response.body();
            if (responseBody != null && responseBody.toLowerCase().contains("\"success\":false")) {
                throw new IllegalStateException("Register API returned unsuccessful response: " + responseBody);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to register test user via " + endpoint, e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to register test user via " + endpoint, e);
        }

        return new TestUser(email, displayName, password);
    }

    private static String normalizeBaseUrl(String baseUrl) {
        if (baseUrl.endsWith("/")) {
            return baseUrl.substring(0, baseUrl.length() - 1);
        }
        return baseUrl;
    }

    private static String escapeJson(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
