package com.root.dto;

public class CompilerResponse {
    private String output;
    private String error;
    private String statusCode;
    private String memory;
    private String cpuTime;

    public CompilerResponse() {
    }

    public CompilerResponse(String output, String error, String statusCode, String memory, String cpuTime) {
        this.output = output;
        this.error = error;
        this.statusCode = statusCode;
        this.memory = memory;
        this.cpuTime = cpuTime;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(String cpuTime) {
        this.cpuTime = cpuTime;
    }
}