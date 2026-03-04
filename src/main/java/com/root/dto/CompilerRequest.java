package com.root.dto;

public class CompilerRequest {
    private String script;
    private String language;  // "java", "python", "javascript", "cpp"
    private Integer versionIndex;  // Language version index
    private String stdin;  // Standard input for the program

    public CompilerRequest() {
    }

    public CompilerRequest(String script, String language, Integer versionIndex, String stdin) {
        this.script = script;
        this.language = language;
        this.versionIndex = versionIndex;
        this.stdin = stdin;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getVersionIndex() {
        return versionIndex;
    }

    public void setVersionIndex(Integer versionIndex) {
        this.versionIndex = versionIndex;
    }

    public String getStdin() {
        return stdin;
    }

    public void setStdin(String stdin) {
        this.stdin = stdin;
    }
}