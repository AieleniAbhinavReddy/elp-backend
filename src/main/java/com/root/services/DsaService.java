package com.root.services;

import com.root.beans.*;
import com.root.dto.*;
import com.root.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DsaService {

    @Autowired
    private DsaSheetRepository sheetRepository;

    @Autowired
    private DsaProblemRepository problemRepository;

    @Autowired
    private DsaProblemProgressRepository progressRepository;

    @Autowired
    private UserRepository userRepository;

    // ── List all DSA sheets ─────────────────────────────────────

    public List<DsaSheetSummaryDTO> getAllSheets(String username) {
        List<DsaSheet> sheets = sheetRepository.findAll();
        Long userId = getUserIdIfPresent(username);

        return sheets.stream().map(sheet -> {
            DsaSheetSummaryDTO dto = new DsaSheetSummaryDTO();
            dto.setId(sheet.getId());
            dto.setTitle(sheet.getTitle());
            dto.setDescription(sheet.getDescription());
            dto.setYoutubePlaylistUrl(sheet.getYoutubePlaylistUrl());
            dto.setTotalProblems(sheet.getTotalProblems());

            if (userId != null) {
                int solved = progressRepository.countByUserIdAndDsaProblem_DsaSheetId(userId, sheet.getId());
                dto.setSolvedCount(solved);
                dto.setProgressPercentage(sheet.getTotalProblems() > 0
                        ? (int) Math.round((solved * 100.0) / sheet.getTotalProblems()) : 0);
            }

            return dto;
        }).collect(Collectors.toList());
    }

    // ── Get sheet detail with all problems ──────────────────────

    public DsaSheetDetailDTO getSheetDetail(Long sheetId, String username) {
        DsaSheet sheet = sheetRepository.findById(sheetId)
                .orElseThrow(() -> new RuntimeException("DSA Sheet not found"));

        List<DsaProblem> problems = problemRepository.findByDsaSheetIdOrderByOrderIndexAsc(sheetId);

        // Get solved problem IDs for the user
        Set<Long> solvedProblemIds = new HashSet<>();
        Long userId = getUserIdIfPresent(username);
        if (userId != null) {
            List<DsaProblemProgress> progressList =
                    progressRepository.findByUserIdAndDsaProblem_DsaSheetId(userId, sheetId);
            solvedProblemIds = progressList.stream()
                    .map(p -> p.getDsaProblem().getId())
                    .collect(Collectors.toSet());
        }

        Set<Long> finalSolvedIds = solvedProblemIds;
        List<DsaProblemDTO> problemDTOs = problems.stream()
                .map(p -> new DsaProblemDTO(
                        p.getId(),
                        p.getTitle(),
                        p.getYoutubeVideoId(),
                        p.getYoutubeVideoUrl(),
                        p.getThumbnailUrl(),
                        p.getPracticeUrl(),
                        p.getPlatform(),
                        p.getOrderIndex(),
                        finalSolvedIds.contains(p.getId())
                ))
                .collect(Collectors.toList());

        int solvedCount = finalSolvedIds.size();

        DsaSheetDetailDTO dto = new DsaSheetDetailDTO();
        dto.setId(sheet.getId());
        dto.setTitle(sheet.getTitle());
        dto.setDescription(sheet.getDescription());
        dto.setYoutubePlaylistUrl(sheet.getYoutubePlaylistUrl());
        dto.setTotalProblems(sheet.getTotalProblems());
        dto.setSolvedCount(solvedCount);
        dto.setProgressPercentage(sheet.getTotalProblems() > 0
                ? (int) Math.round((solvedCount * 100.0) / sheet.getTotalProblems()) : 0);
        dto.setProblems(problemDTOs);

        return dto;
    }

    // ── Mark problem as solved ──────────────────────────────────

    @Transactional
    public Map<String, Object> markProblemSolved(String username, Long problemId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DsaProblem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        // Check if already solved
        Optional<DsaProblemProgress> existing =
                progressRepository.findByUserIdAndDsaProblemId(user.getId(), problemId);
        if (existing.isPresent()) {
            return buildSolveResponse(user.getId(), problem, "Problem already marked as solved");
        }

        DsaProblemProgress progress = new DsaProblemProgress();
        progress.setUser(user);
        progress.setDsaProblem(problem);
        progressRepository.save(progress);

        return buildSolveResponse(user.getId(), problem, "Problem marked as solved");
    }

    // ── Unmark problem ──────────────────────────────────────────

    @Transactional
    public Map<String, Object> unmarkProblemSolved(String username, Long problemId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DsaProblem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        Optional<DsaProblemProgress> existing =
                progressRepository.findByUserIdAndDsaProblemId(user.getId(), problemId);
        if (existing.isEmpty()) {
            return buildSolveResponse(user.getId(), problem, "Problem was not marked as solved");
        }

        progressRepository.delete(existing.get());

        return buildSolveResponse(user.getId(), problem, "Problem unmarked as solved");
    }

    // ── Get progress for a specific sheet ───────────────────────

    public Map<String, Object> getSheetProgress(String username, Long sheetId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DsaSheet sheet = sheetRepository.findById(sheetId)
                .orElseThrow(() -> new RuntimeException("DSA Sheet not found"));

        int totalProblems = sheet.getTotalProblems();
        int solvedCount = progressRepository.countByUserIdAndDsaProblem_DsaSheetId(user.getId(), sheetId);

        List<DsaProblemProgress> progressList =
                progressRepository.findByUserIdAndDsaProblem_DsaSheetId(user.getId(), sheetId);
        List<Long> solvedProblemIds = progressList.stream()
                .map(p -> p.getDsaProblem().getId())
                .collect(Collectors.toList());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("sheetId", sheetId);
        response.put("sheetTitle", sheet.getTitle());
        response.put("totalProblems", totalProblems);
        response.put("solvedCount", solvedCount);
        response.put("progressPercentage", totalProblems > 0
                ? (int) Math.round((solvedCount * 100.0) / totalProblems) : 0);
        response.put("solvedProblemIds", solvedProblemIds);
        return response;
    }

    // ── Get overall DSA progress across all sheets ──────────────

    public DsaOverallProgressDTO getOverallProgress(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<DsaSheet> sheets = sheetRepository.findAll();
        int totalProblems = 0;
        int totalSolved = 0;

        List<DsaSheetSummaryDTO> sheetSummaries = new ArrayList<>();
        for (DsaSheet sheet : sheets) {
            int sheetTotal = sheet.getTotalProblems();
            int sheetSolved = progressRepository.countByUserIdAndDsaProblem_DsaSheetId(user.getId(), sheet.getId());

            totalProblems += sheetTotal;
            totalSolved += sheetSolved;

            DsaSheetSummaryDTO summary = new DsaSheetSummaryDTO();
            summary.setId(sheet.getId());
            summary.setTitle(sheet.getTitle());
            summary.setDescription(sheet.getDescription());
            summary.setYoutubePlaylistUrl(sheet.getYoutubePlaylistUrl());
            summary.setTotalProblems(sheetTotal);
            summary.setSolvedCount(sheetSolved);
            summary.setProgressPercentage(sheetTotal > 0
                    ? (int) Math.round((sheetSolved * 100.0) / sheetTotal) : 0);
            sheetSummaries.add(summary);
        }

        DsaOverallProgressDTO dto = new DsaOverallProgressDTO();
        dto.setTotalSheets(sheets.size());
        dto.setTotalProblems(totalProblems);
        dto.setTotalSolved(totalSolved);
        dto.setOverallPercentage(totalProblems > 0
                ? (int) Math.round((totalSolved * 100.0) / totalProblems) : 0);
        dto.setSheets(sheetSummaries);

        return dto;
    }

    // ── Helpers ─────────────────────────────────────────────────

    private Long getUserIdIfPresent(String username) {
        if (username == null || username.isBlank()) return null;
        return userRepository.findByUsername(username).map(User::getId).orElse(null);
    }

    private Map<String, Object> buildSolveResponse(Long userId, DsaProblem problem, String message) {
        Long sheetId = problem.getDsaSheet().getId();
        DsaSheet sheet = problem.getDsaSheet();

        int totalProblems = sheet.getTotalProblems();
        int solvedCount = progressRepository.countByUserIdAndDsaProblem_DsaSheetId(userId, sheetId);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", message);
        response.put("problemId", problem.getId());
        response.put("problemTitle", problem.getTitle());
        response.put("sheetId", sheetId);
        response.put("sheetTitle", sheet.getTitle());
        response.put("totalProblems", totalProblems);
        response.put("solvedCount", solvedCount);
        response.put("progressPercentage", totalProblems > 0
                ? (int) Math.round((solvedCount * 100.0) / totalProblems) : 0);
        return response;
    }
}
