package com.rosendo.company.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiRequestUtil {

    private static final String API_BASE_URL = "http://localhost:8080"; // Altere para o URL base da sua API

    public static JSONObject sendPostRequest(String endpoint, JSONObject requestData) {
        try {
            URL url = new URL(API_BASE_URL + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestData.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // A solicitação foi bem-sucedida, leia a resposta JSON
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    return new JSONObject(response.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // Trate erros de acordo com o código de resposta
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Trate exceções de conexão aqui
            return null;
        }
    }

    public static String sendGetRequest(String endpoint) {
        try {
            URL url = new URL(API_BASE_URL + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // A solicitação foi bem-sucedida, leia a resposta
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    return response.toString();
                }
            } else {
                // Trate erros de acordo com o código de resposta
                return null; // Ou lance uma exceção adequada
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Trate exceções de conexão aqui
            return null; // Ou lance uma exceção adequada
        }
    }
}
