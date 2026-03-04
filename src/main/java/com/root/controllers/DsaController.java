package com.root.controllers;

import com.root.dto.DsaOverallProgressDTO;
import com.root.dto.DsaSheetDetailDTO;
import com.root.dto.DsaSheetSummaryDTO;
import com.root.services.DsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dsa")
public class DsaController {

    @Autowired
    private DsaService dsaService;

    // ── Public endpoints (browsing) ────────────────────────────

    /**
     * GET /api/dsa/sheets
     * Lists all DSA practice paths/sheets.
     * If authenticated, includes user's solved count per sheet.
     */
    @GetMapping("/sheets")
    public ResponseEntity<List<DsaSheetSummaryDTO>> getAllSheets(Authentication authentication) {
        String username = (authentication != null) ? authentication.getName() : null;
        return ResponseEntity.ok(dsaService.getAllSheets(username));
    }

    /**
     * GET /api/dsa/sheets/{sheetId}
     * Gets sheet detail with all problems.
     * If authenticated, each problem shows solved status.
     */
    @GetMapping("/sheets/{sheetId}")
    public ResponseEntity<DsaSheetDetailDTO> getSheetDetail(
            @PathVariable Long sheetId,
            Authentication authentication) {
        String username = (authentication != null) ? authentication.getName() : null;
        return ResponseEntity.ok(dsaService.getSheetDetail(sheetId, username));
    }

    // ── Authenticated endpoints (progress tracking) ────────────

    /**
     * POST /api/dsa/problems/{problemId}/solve
     * Marks a problem as solved for the logged-in user.
     */
    @PostMapping("/problems/{problemId}/solve")
    public ResponseEntity<Map<String, Object>> markProblemSolved(
            @PathVariable Long problemId,
            Authentication authentication) {
        return ResponseEntity.ok(dsaService.markProblemSolved(authentication.getName(), problemId));
    }

    /**
     * DELETE /api/dsa/problems/{problemId}/solve
     * Unmarks a problem as solved.
     */
    @DeleteMapping("/problems/{problemId}/solve")
    public ResponseEntity<Map<String, Object>> unmarkProblemSolved(
            @PathVariable Long problemId,
            Authentication authentication) {
        return ResponseEntity.ok(dsaService.unmarkProblemSolved(authentication.getName(), problemId));
    }

    /**
     * GET /api/dsa/sheets/{sheetId}/progress
     * Gets user's progress for a specific sheet.
     */
    @GetMapping("/sheets/{sheetId}/progress")
    public ResponseEntity<Map<String, Object>> getSheetProgress(
            @PathVariable Long sheetId,
            Authentication authentication) {
        return ResponseEntity.ok(dsaService.getSheetProgress(authentication.getName(), sheetId));
    }

    /**
     * GET /api/dsa/my-progress
     * Gets overall DSA progress across all sheets.
     */
    @GetMapping("/my-progress")
    public ResponseEntity<DsaOverallProgressDTO> getOverallProgress(
            Authentication authentication) {
        return ResponseEntity.ok(dsaService.getOverallProgress(authentication.getName()));
    }
}
