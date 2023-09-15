package com.rosendo.company.Utils;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiRequestUtil {

    private static final String API_BASE_URL = "http://localhost:8080"; // Altere para o URL base da sua API

    public static boolean sendPostRequest(String endpoint, JSONObject requestData) {
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
                // A solicitação foi bem-sucedida
                return true;
            } else {
                // Trate erros de acordo com o código de resposta
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Trate exceções de conexão aqui
            return false;
        }
    }
}