package com.deadman.dashboard;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pedro.library.AutoPermissions;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener {

  private LineChart chart;

  private final int[] colors = new int[] {
      ColorTemplate.MATERIAL_COLORS [0],
      ColorTemplate.LIBERTY_COLORS  [3],
      ColorTemplate.MATERIAL_COLORS [2]
  };

  private final int[] colors2 = new int[] {
      ColorTemplate.PASTEL_COLORS  [3],
      ColorTemplate.COLORFUL_COLORS [3],
      ColorTemplate.MATERIAL_COLORS [3]
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Go Full screen
    View decorView = getWindow().getDecorView();
    int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    decorView.setSystemUiVisibility(uiOptions);

    Button qr_button = findViewById(R.id.qr_button);
    qr_button.setOnClickListener(view -> {scanner();});

    AutoPermissions.Companion.loadAllPermissions(this, 1);

    chart1();
    chart2();
  }

  private void scanner(){
    Intent intent = new Intent(this, Scanner.class);
    startActivity(intent);
  }

  private void chart1(){
    chart = findViewById(R.id.chart1);
    chart.setDrawGridBackground(true);
    chart.getDescription().setEnabled(false);
    chart.setDrawBorders(true);
    chart.getAxisLeft().setEnabled(false);
    chart.getAxisRight().setDrawAxisLine(false);
    chart.getAxisRight().setDrawGridLines(false);
    chart.getXAxis().setDrawAxisLine(false);
    chart.getXAxis().setDrawGridLines(false);
    chart.getXAxis().setDrawLabels(false);
    chart.getAxisRight().setDrawLabels(false);

    // enable touch gestures
    chart.setTouchEnabled(false);

    // enable scaling and dragging
    chart.setDragEnabled(false);
    chart.setScaleEnabled(false);

    // if disabled, scaling can be done on x- and y-axis separately
    chart.setPinchZoom(false);

    Legend legend = chart.getLegend();
    legend.setEnabled(false);

    ArrayList<ILineDataSet> dataSets = new ArrayList<>();


    for (int z = 0; z < 3; z++) {

      ArrayList<Entry> values = new ArrayList<>();

      for (int i = 0; i < 8; i++) {
        double val = (0 + (Math.random() * ((10) + 1)));
        values.add(new Entry(i, (float) val));
      }

      LineDataSet d = new LineDataSet(values, "Red " + (z + 1));
      d.setLineWidth(2.5f);
      d.setCircleRadius(4f);

      int color = colors[z % colors.length];
      d.setColor(color);
      d.setCircleColor(color);
      d.setDrawValues(false);
      dataSets.add(d);
    }

    LineData data = new LineData(dataSets);
    chart.setData(data);
    chart.invalidate();
  }

  private void chart2(){
    chart = findViewById(R.id.chart2);
    chart.setDrawGridBackground(true);
    chart.getDescription().setEnabled(false);
    chart.setDrawBorders(true);
    chart.getAxisLeft().setEnabled(false);
    chart.getAxisRight().setDrawAxisLine(false);
    chart.getAxisRight().setDrawGridLines(false);
    chart.getXAxis().setDrawAxisLine(false);
    chart.getXAxis().setDrawGridLines(false);
    chart.getXAxis().setDrawLabels(false);
    chart.getAxisRight().setDrawLabels(false);

    // enable touch gestures
    chart.setTouchEnabled(false);

    // enable scaling and dragging
    chart.setDragEnabled(false);
    chart.setScaleEnabled(false);

    // if disabled, scaling can be done on x- and y-axis separately
    chart.setPinchZoom(false);

    Legend legend = chart.getLegend();
    legend.setEnabled(false);

    ArrayList<ILineDataSet> dataSets = new ArrayList<>();


    for (int z = 3; z < 6; z++) {

      ArrayList<Entry> values = new ArrayList<>();

      for (int i = 0; i < 8; i++) {
        double val = (0 + (Math.random() * ((10) + 1)));
        values.add(new Entry(i, (float) val));
      }

      LineDataSet d = new LineDataSet(values, "Blue " + (z + 1));
      d.setLineWidth(2.5f);
      d.setCircleRadius(4f);

      int color = colors2[z % colors2.length];
      d.setColor(color);
      d.setCircleColor(color);
      d.setDrawValues(false);
      dataSets.add(d);
    }

    LineData data = new LineData(dataSets);
    chart.setData(data);
    chart.invalidate();
  }

  @Override
  public void onValueSelected(Entry e, Highlight h) {

  }

  @Override
  public void onNothingSelected() {

  }
}
