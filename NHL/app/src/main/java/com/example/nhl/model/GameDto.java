package com.example.nhl.model;

import com.google.gson.annotations.SerializedName;

public class GameDto {

    @SerializedName("score")
    private String score;

    @SerializedName("is_overtime")
    private boolean isOvertime;

    @SerializedName("is_win")
    private boolean isWin;

    public String getScore() {
        return score;
    }

    public GameDto(String score, boolean isOvertime, boolean isWin) {
        this.score = score;
        this.isOvertime = isOvertime;
        this.isWin = isWin;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public boolean isOvertime() {
        return isOvertime;
    }

    public void setOvertime(boolean overtime) {
        isOvertime = overtime;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }
}
