package com.example.nhl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Score {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("score")
    @Expose
    private String score;

    @SerializedName("player")
    @Expose
    private String player;

    @SerializedName("is_win")
    @Expose
    private boolean is_win;

    @Override
    public String toString() {

        String game;
        if (is_win){
            game = "победа";
        }else {
            game = "проигрыш ";
        }
        return  player +" - счет "+ score + " - "+ game;
    }

    public Score( String score, String player, boolean is_win) {
        this.score = score;
        this.player = player;
        this.is_win = is_win;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public boolean isIs_win() {
        return is_win;
    }

    public void setIs_win(boolean is_win) {
        this.is_win = is_win;
    }
}
