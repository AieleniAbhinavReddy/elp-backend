package com.root.dto;

import java.util.List;

public class DsaOverallProgressDTO {
    private int totalSheets;
    private int totalProblems;
    private int totalSolved;
    private int overallPercentage;
    private List<DsaSheetSummaryDTO> sheets;

    public DsaOverallProgressDTO() {}

    public int getTotalSheets() { return totalSheets; }
    public void setTotalSheets(int totalSheets) { this.totalSheets = totalSheets; }

    public int getTotalProblems() { return totalProblems; }
    public void setTotalProblems(int totalProblems) { this.totalProblems = totalProblems; }

    public int getTotalSolved() { return totalSolved; }
    public void setTotalSolved(int totalSolved) { this.totalSolved = totalSolved; }

    public int getOverallPercentage() { return overallPercentage; }
    public void setOverallPercentage(int overallPercentage) { this.overallPercentage = overallPercentage; }

    public List<DsaSheetSummaryDTO> getSheets() { return sheets; }
    public void setSheets(List<DsaSheetSummaryDTO> sheets) { this.sheets = sheets; }
}
