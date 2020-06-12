package com.example.nhl.helpers;

public class StatusGenerator {

    public static String getStatusByRating(double rating) {
        String status = null;
        if (rating == 1.0) {
            status = "zero";
        } else if (rating == 2.0) {
            status = "low";
        } else if (rating == 3.0) {
            status = "hard";
        } else if (rating == 4.0) {
            status = "driver";
        } else {
            status = "zero";
        }
        return status;
    }

    public static float getRatingByStatus(String status) {
        float rating;
        switch (status) {
            case "low":
                rating = (float) 2.0;
                break;
            case "hard":
                rating = (float) 3.0;
                break;
            case "driver":
                rating = (float) 4.0;
                break;
            default:
                rating = (float) 1.0;
        }
        return rating;
    }
}
