package com.shawtypimp.kittenbot.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shawtypimp.kittenbot.exception.ServiceException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class KittenBotClient {

    @Autowired
    private OkHttpClient client;

    @Value("${request.cat.api.url}")
    private String requestUrl;

    @Value("${cat.api.token}")
    private String apiToken;

    private static final Logger LOGGER = LoggerFactory.getLogger(KittenBotClient.class);

    public String makeRequest(String categoryId) throws ServiceException{

        String photoUrl = null;
        var urlBuilder = HttpUrl.parse(requestUrl).newBuilder();
        urlBuilder.addQueryParameter("category_ids", categoryId);

        var request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("x-api-token", apiToken)
                .build();

        try (var response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                var responseBody = response.body().string();
                var objectMapper = new ObjectMapper();

                List<Map<String, Object>> photoList = objectMapper.readValue(responseBody,
                        new TypeReference<>() {});

                if (!photoList.isEmpty()) {

                    Map<String, Object> firstPhoto = photoList.get(0);
                    photoUrl = (String) firstPhoto.get("url");

                    LOGGER.info("Photo URL: {}", photoUrl);
                } else {
                    LOGGER.warn("No photos found in the response");
                }
            } else {
                LOGGER.error("Error: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            LOGGER.error("Ошибка полусения фото", e);
            throw new ServiceException("Error while getting photo", e);
        }
        return photoUrl;
    }
}
