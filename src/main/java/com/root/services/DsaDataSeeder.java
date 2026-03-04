package com.root.services;

import com.root.beans.DsaProblem;
import com.root.beans.DsaSheet;
import com.root.repositories.DsaProblemRepository;
import com.root.repositories.DsaSheetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class DsaDataSeeder {

    private static final Logger logger = LoggerFactory.getLogger(DsaDataSeeder.class);

    @Bean
    public CommandLineRunner seedDsaData(
            DsaSheetRepository sheetRepository,
            DsaProblemRepository problemRepository,
            YouTubeService youTubeService) {

        return args -> {
            // Skip if data already exists
            if (sheetRepository.count() > 0) {
                logger.info("DSA sheets already exist in database. Skipping seeder.");
                return;
            }

            if (!youTubeService.isApiKeyConfigured()) {
                logger.warn("========================================================");
                logger.warn("YouTube API key is NOT configured!");
                logger.warn("Set the YOUTUBE_API_KEY environment variable to enable");
                logger.warn("automatic DSA playlist data seeding.");
                logger.warn("Without it, DSA sheets will be empty.");
                logger.warn("========================================================");
                return;
            }

            logger.info("Starting DSA data seeding from YouTube playlists...");

            // Define the 3 practice paths
            seedSheet(sheetRepository, problemRepository, youTubeService,
                    "Love Babbar DSA Sheet",
                    "Complete DSA problem solutions following the Love Babbar 450 DSA Sheet. "
                            + "Covers arrays, strings, linked lists, trees, graphs, dynamic programming, and more.",
                    "PLkyzNHA-yYZVjhoxsSZTQUbmf38_O75vG",
                    "https://www.youtube.com/playlist?list=PLkyzNHA-yYZVjhoxsSZTQUbmf38_O75vG"
            );

            seedSheet(sheetRepository, problemRepository, youTubeService,
                    "Top Array Problems",
                    "Curated collection of the most important array problems frequently asked "
                            + "in coding interviews. Master array manipulation techniques and patterns.",
                    "PLkyzNHA-yYZUporIrxiVFXOm9p70VvSAl",
                    "https://www.youtube.com/playlist?list=PLkyzNHA-yYZUporIrxiVFXOm9p70VvSAl"
            );

            seedSheet(sheetRepository, problemRepository, youTubeService,
                    "GeeksForGeeks Problems",
                    "Solutions to popular GeeksForGeeks problems covering fundamental "
                            + "data structures and algorithms concepts.",
                    "PLkyzNHA-yYZVHVFieI01jmCwT8DXHanLF",
                    "https://www.youtube.com/playlist?list=PLkyzNHA-yYZVHVFieI01jmCwT8DXHanLF"
            );

            logger.info("DSA data seeding complete!");
        };
    }

    private void seedSheet(DsaSheetRepository sheetRepository,
                           DsaProblemRepository problemRepository,
                           YouTubeService youTubeService,
                           String title, String description,
                           String playlistId, String playlistUrl) {

        logger.info("Fetching playlist: {} ...", title);

        // Create the sheet
        DsaSheet sheet = new DsaSheet();
        sheet.setTitle(title);
        sheet.setDescription(description);
        sheet.setYoutubePlaylistId(playlistId);
        sheet.setYoutubePlaylistUrl(playlistUrl);
        sheet.setTotalProblems(0); // will update after fetching
        sheet = sheetRepository.save(sheet);

        // Fetch videos from YouTube
        List<Map<String, Object>> videos = youTubeService.fetchAllPlaylistVideos(playlistId);

        int count = 0;
        for (Map<String, Object> video : videos) {
            String videoTitle = (String) video.get("title");
            String videoId = (String) video.get("videoId");
            String thumbnailUrl = (String) video.get("thumbnailUrl");
            int position = (int) video.get("position");
            String practiceUrl = (String) video.getOrDefault("practiceUrl", null);
            String platform = (String) video.getOrDefault("platform", null);

            DsaProblem problem = new DsaProblem();
            problem.setDsaSheet(sheet);
            problem.setTitle(videoTitle);
            problem.setYoutubeVideoId(videoId);
            problem.setYoutubeVideoUrl("https://www.youtube.com/watch?v=" + videoId);
            problem.setThumbnailUrl(thumbnailUrl);
            problem.setPracticeUrl(practiceUrl);
            problem.setPlatform(platform);
            problem.setOrderIndex(position);

            problemRepository.save(problem);
            count++;
        }

        // Update total count
        sheet.setTotalProblems(count);
        sheetRepository.save(sheet);

        logger.info("  -> Saved {} problems for \"{}\"", count, title);
    }
}
