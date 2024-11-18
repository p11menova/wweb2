package com.example.wweb2.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Result implements Serializable {
    private boolean success;
    private String x;
    private String y;
    private String r;
    private String executionTime;
    public String currentTime = null;


    public Result() {
    }

    public Result(boolean success, String executionTime, String x, String y, String r, String currentTime) {
        this.success = success;
        this.executionTime = executionTime;
        this.x = x;
        this.y = y;
        this.r = r;
        this.currentTime = currentTime;
    }

    // Геттеры и сеттеры
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }


    @Override
    public String toString() {
        return """
                {"x": "%s",
                "y": "%s",
                "r": "%s",
                "success": "%s",
                "execution_time": "%s",
                "current_time": "%s"}""".formatted(x, y,  r,
                success, getExecutionTime(), getCurrentTime());
    }
}
