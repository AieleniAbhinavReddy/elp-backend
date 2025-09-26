package com.root.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YouTubeService {

    @Value("${youtube.api.key}")
    private String apiKey;

    private final String API_URL = "https://www.googleapis.com/youtube/v3/playlistItems";

    public String getPlaylistVideos(String playlistId, int maxResults) {
        String url = String.format(
            "%s?part=snippet&playlistId=%s&maxResults=%d&key=%s",
            API_URL, playlistId, maxResults, apiKey
        );
        RestTemplate restTemplate = new RestTemplate();
        // Returning raw JSON for simplicity. In a production app, you'd map this to DTOs.
        return restTemplate.getForObject(url, String.class);
    }
}