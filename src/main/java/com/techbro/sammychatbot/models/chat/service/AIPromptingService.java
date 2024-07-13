package com.techbro.sammychatbot.models.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techbro.sammychatbot.models.chat.dto.AIRequest;
import com.techbro.sammychatbot.models.chat.dto.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class AIPromptingService {

    @Value("${gemini.api.url}")
    private String baseUrl;

    @Value("${gemini.api.key}")
    private String apiKey;


    public ResponseData generateGeminiContent(AIRequest aiRequest) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseData responseData = null;

        try {
            // Prepare the HTTP POST request
            HttpPost httpPost = new HttpPost(baseUrl);

            // Set headers
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("x-goog-api-key", apiKey);

            // Serialize AIResponse to JSON string
            String requestBody = new ObjectMapper().writeValueAsString(aiRequest);

            // Set request body
            StringEntity requestEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
            httpPost.setEntity(requestEntity);

            // Execute the request
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // Parse JSON response to ResponseData object
                String jsonResponse = EntityUtils.toString(entity);
                responseData = new ObjectMapper().readValue(jsonResponse, ResponseData.class);
            }

        } catch (IOException e) {
            log.error("Error calling Gemini API: {}", e.getMessage());

        }

        return responseData;
    }
}