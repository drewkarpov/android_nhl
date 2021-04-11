package com.example.nhl.model;

import com.google.gson.annotations.SerializedName;

public class Game {

    @SerializedName("_id")
    private String id;

    @SerializedName("score")
    private String score;

    @SerializedName("is_overtime")
    private boolean isOvertime;

    @SerializedName("is_win")
    private boolean isWin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
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

    @Override
    public String toString() {
        String status;
        String ot = "OT";
        if (isWin) {
            status = "win";
        } else {
            status = "lose";
        }
        if (!isOvertime) {
            ot = "";
        }
        return score + "  " + status + "  " + ot;
    }
}
