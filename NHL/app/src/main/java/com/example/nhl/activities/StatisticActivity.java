package com.example.nhl.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.nhl.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        PieChart pieChart = findViewById(R.id.piechart);
        ArrayList<PieEntry> NoOfEmp = new ArrayList<>();
        Intent intent = getIntent();
        int zero = Integer.parseInt(intent.getStringExtra("zero"));
        int low = Integer.parseInt(intent.getStringExtra("low"));
        int hard = Integer.parseInt(intent.getStringExtra("hard"));
        int driver = Integer.parseInt(intent.getStringExtra("driver"));

        int[] color = {Color.rgb(100, 221, 23), Color.rgb(255, 30, 30), Color.rgb(40, 200, 200)
                , Color.rgb(255, 136, 0)
        };

        NoOfEmp.add(new PieEntry(low, 0));
        NoOfEmp.add(new PieEntry(driver - 100, 1));
        NoOfEmp.add(new PieEntry(zero, 2));
        NoOfEmp.add(new PieEntry(hard, 3));

        pieChart.getDescription().setText("Players statistic");
        pieChart.getDescription().setTextSize(25);
        pieChart.getDescription().setTextColor(ContextCompat.getColor(this, R.color.white));
        pieChart.getDescription().setPosition(1000, 200);

        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Players");
        dataSet.setValueTextSize(20);
        dataSet.setValueTextColor(R.color.black);
        dataSet.setColors(color);
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.animateXY(2000, 2000);
    }

}
