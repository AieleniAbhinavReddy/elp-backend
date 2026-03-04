package com.root.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class YouTubeService {

    private static final Logger logger = LoggerFactory.getLogger(YouTubeService.class);

    @Value("${youtube.api.key}")
    private String apiKey;

    private static final String PLAYLIST_ITEMS_URL = "https://www.googleapis.com/youtube/v3/playlistItems";
    private static final String VIDEOS_URL = "https://www.googleapis.com/youtube/v3/videos";

    private static final Pattern PRACTICE_URL_PATTERN = Pattern.compile(
            "(https?://(?:www\\.)?(?:" +
                    "leetcode\\.com/problems/[^\\s\"<>)\\]]+|" +
                    "practice\\.geeksforgeeks\\.org/problems/[^\\s\"<>)\\]]+|" +
                    "geeksforgeeks\\.org/problems/[^\\s\"<>)\\]]+|" +
                    "codingninjas\\.com/[^\\s\"<>)\\]]+|" +
                    "hackerrank\\.com/[^\\s\"<>)\\]]+|" +
                    "codeforces\\.com/[^\\s\"<>)\\]]+|" +
                    "interviewbit\\.com/problems/[^\\s\"<>)\\]]+|" +
                    "neetcode\\.io/problems/[^\\s\"<>)\\]]+" +
                    "))"
    );

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public YouTubeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    // ── Existing method (unchanged) ─────────────────────────────

    public String getPlaylistVideos(String playlistId, int maxResults) {
        String url = String.format(
                "%s?part=snippet&playlistId=%s&maxResults=%d&key=%s",
                PLAYLIST_ITEMS_URL, playlistId, maxResults, apiKey
        );
        return restTemplate.getForObject(url, String.class);
    }

    // ── New: Fetch all playlist items with pagination ───────────

    public boolean isApiKeyConfigured() {
        return apiKey != null && !apiKey.isBlank();
    }

    /**
     * Fetches ALL videos from a YouTube playlist (handles pagination).
     * Returns a list of maps with keys: title, videoId, thumbnailUrl, position.
     * Then fetches full video descriptions in batches to extract practice URLs.
     */
    public List<Map<String, Object>> fetchAllPlaylistVideos(String playlistId) {
        if (!isApiKeyConfigured()) {
            logger.warn("YouTube API key is not configured. Cannot fetch playlist data.");
            return Collections.emptyList();
        }

        List<Map<String, Object>> allVideos = new ArrayList<>();
        String nextPageToken = null;

        try {
            // Step 1: Fetch all playlist items (get videoIds, titles, positions)
            do {
                String url = String.format(
                        "%s?part=snippet,contentDetails&playlistId=%s&maxResults=50&key=%s%s",
                        PLAYLIST_ITEMS_URL, playlistId, apiKey,
                        nextPageToken != null ? "&pageToken=" + nextPageToken : ""
                );

                String responseBody = restTemplate.getForObject(url, String.class);
                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode items = root.path("items");

                for (JsonNode item : items) {
                    JsonNode snippet = item.path("snippet");
                    String title = snippet.path("title").asText("");
                    String videoId = snippet.path("resourceId").path("videoId").asText("");
                    int position = snippet.path("position").asInt(0);

                    // Skip deleted or private videos
                    if ("Deleted video".equals(title) || "Private video".equals(title) || videoId.isEmpty()) {
                        continue;
                    }

                    String thumbnailUrl = "";
                    JsonNode thumbnails = snippet.path("thumbnails");
                    if (thumbnails.has("medium")) {
                        thumbnailUrl = thumbnails.path("medium").path("url").asText("");
                    } else if (thumbnails.has("default")) {
                        thumbnailUrl = thumbnails.path("default").path("url").asText("");
                    }

                    // Get description from playlist item (may be truncated)
                    String description = snippet.path("description").asText("");

                    Map<String, Object> video = new LinkedHashMap<>();
                    video.put("title", title);
                    video.put("videoId", videoId);
                    video.put("thumbnailUrl", thumbnailUrl);
                    video.put("position", position);
                    video.put("description", description);

                    allVideos.add(video);
                }

                nextPageToken = root.has("nextPageToken") ? root.get("nextPageToken").asText() : null;
            } while (nextPageToken != null);

            // Step 2: For videos where no practice URL was found in playlist description,
            // fetch full video details in batches of 50
            List<Map<String, Object>> needsFullDescription = new ArrayList<>();
            for (Map<String, Object> video : allVideos) {
                String desc = (String) video.get("description");
                String practiceUrl = extractPracticeUrl(desc);
                if (practiceUrl != null) {
                    video.put("practiceUrl", practiceUrl);
                    video.put("platform", determinePlatform(practiceUrl));
                } else {
                    needsFullDescription.add(video);
                }
            }

            // Batch-fetch full descriptions for videos missing practice URLs
            if (!needsFullDescription.isEmpty()) {
                fetchAndApplyFullDescriptions(needsFullDescription);
            }

            logger.info("Fetched {} videos from playlist {}", allVideos.size(), playlistId);
        } catch (Exception e) {
            logger.error("Error fetching playlist {}: {}", playlistId, e.getMessage());
        }

        return allVideos;
    }

    /**
     * Fetches full video descriptions via videos.list API (batch of 50 IDs).
     */
    private void fetchAndApplyFullDescriptions(List<Map<String, Object>> videos) {
        try {
            for (int i = 0; i < videos.size(); i += 50) {
                List<Map<String, Object>> batch = videos.subList(i, Math.min(i + 50, videos.size()));
                StringBuilder ids = new StringBuilder();
                for (Map<String, Object> v : batch) {
                    if (ids.length() > 0) ids.append(",");
                    ids.append(v.get("videoId"));
                }

                String url = String.format(
                        "%s?part=snippet&id=%s&key=%s",
                        VIDEOS_URL, ids.toString(), apiKey
                );

                String responseBody = restTemplate.getForObject(url, String.class);
                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode items = root.path("items");

                // Build a map of videoId -> description
                Map<String, String> descMap = new HashMap<>();
                for (JsonNode item : items) {
                    String videoId = item.path("id").asText("");
                    String desc = item.path("snippet").path("description").asText("");
                    descMap.put(videoId, desc);
                }

                // Apply to our video list
                for (Map<String, Object> v : batch) {
                    String videoId = (String) v.get("videoId");
                    String fullDesc = descMap.get(videoId);
                    if (fullDesc != null) {
                        v.put("description", fullDesc);
                        String practiceUrl = extractPracticeUrl(fullDesc);
                        if (practiceUrl != null) {
                            v.put("practiceUrl", practiceUrl);
                            v.put("platform", determinePlatform(practiceUrl));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error fetching full video descriptions: {}", e.getMessage());
        }
    }

    // ── URL Extraction Helpers ──────────────────────────────────

    /**
     * Extracts the first practice URL from a video description.
     */
    public static String extractPracticeUrl(String description) {
        if (description == null || description.isBlank()) return null;
        Matcher matcher = PRACTICE_URL_PATTERN.matcher(description);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * Determines the coding platform from the URL.
     */
    public static String determinePlatform(String url) {
        if (url == null) return "Unknown";
        if (url.contains("leetcode.com")) return "LeetCode";
        if (url.contains("geeksforgeeks.org")) return "GeeksForGeeks";
        if (url.contains("codingninjas.com")) return "Coding Ninjas";
        if (url.contains("hackerrank.com")) return "HackerRank";
        if (url.contains("codeforces.com")) return "Codeforces";
        if (url.contains("interviewbit.com")) return "InterviewBit";
        if (url.contains("neetcode.io")) return "NeetCode";
        return "Other";
    }
}