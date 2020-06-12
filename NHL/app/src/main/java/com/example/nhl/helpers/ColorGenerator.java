package com.example.nhl.helpers;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nhl.R;


public class ColorGenerator {

    public static int getColorId(RecyclerView.ViewHolder holder, int priority) {
        int colorId;
        switch (priority) {
            case 1:
                colorId = holder.itemView.getResources().getColor(R.color.driver);
                break;
            case 2:
                colorId = holder.itemView.getResources().getColor(R.color.hard);
                break;

            case 3:
                colorId = holder.itemView.getResources().getColor(R.color.low);
                break;
            case 4:
                colorId = holder.itemView.getResources().getColor(R.color.zero);
                break;
            default:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_blue_bright);
        }
        return colorId;
    }
}
