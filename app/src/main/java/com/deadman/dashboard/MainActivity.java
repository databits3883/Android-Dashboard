package com.deadman.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener {

  File qr_string = new File(
      Environment.getExternalStorageDirectory() + File.separator + "FRC" + File.separator + "qr_string.txt");


  private final int[] colors = new int[] {
      ColorTemplate.COLORFUL_COLORS [3],
      ColorTemplate.MATERIAL_COLORS [2],
      Color.BLACK,
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AutoPermissions.Companion.loadAllPermissions(this, 1);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Go Full screen
    View decorView = getWindow().getDecorView();
    int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    decorView.setSystemUiVisibility(uiOptions);

    Button qr_button = findViewById(R.id.qr_button);
    qr_button.setOnClickListener(view -> scanner());
  }

  @Override
  public void onResume() {
    super.onResume();


    if (qr_string.exists()) {
      team1();
      team2();
      team3();
      team4();
      team5();
      team6();
      graph_title();
      chart_maker(R.id.chart1, 0, 3);
      chart_maker(R.id.chart2,3,6);
    }
  }

  private String getdata(){
    String qr_info;
      int length = (int) qr_string.length();
      byte[] bytes = new byte[length];

      try {
        FileInputStream in = new FileInputStream(qr_string);
        in.read(bytes);
        in.close();
      } catch (
          IOException e) {
        e.printStackTrace();
      }

    qr_info = new String(bytes);
//    String testing = Integer.toString(qr_info.split(";").length);
//    Toast.makeText(this,testing,Toast.LENGTH_SHORT).show();
//    if (qr_info.split(";").length != 115) {
//      qr_string.delete();
      if (isFirstTime()) {
        new AlertDialog.Builder(this)
            .setMessage("The QR Code scanned was invalid, please rescan it")
            .setTitle("Missing QR Code")
            .setPositiveButton("Ok", (dialog, id) -> {
              reset_isFirstTime();
              scanner();})
            .show();
//      }
    }

    return qr_info;
  }

  private boolean isFirstTime()
  {
    SharedPreferences preferences = getPreferences(MODE_PRIVATE);
    boolean ranBefore = preferences.getBoolean("RanBefore", false);
    if (!ranBefore) {
      // first time
      SharedPreferences.Editor editor = preferences.edit();
      editor.putBoolean("RanBefore", true);
      editor.apply();
    }
    return !ranBefore;
  }

  private void reset_isFirstTime(){
    SharedPreferences preferences = getPreferences(MODE_PRIVATE);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putBoolean("RanBefore", false);
    editor.apply();
  }

  private void scanner(){
    Intent intent = new Intent(this, Scanner.class);
    startActivity(intent);
  }

  private void graph_title(){
      String[] teams = getdata().split(":");
      String[] title_category = teams[0].split(";");
      TextView graph_title = findViewById(R.id.graph_title);
      graph_title.setText(title_category[18]);
  }

  private void chart_maker(int id, int a, int b){
    LineChart chart = findViewById(id);
    chart.setDrawGridBackground(true);
    chart.getDescription().setEnabled(false);
    chart.setDrawBorders(true);
    chart.getAxisLeft().setEnabled(true);
    chart.getAxisLeft().setDrawLabels(true);
    chart.getAxisRight().setDrawAxisLine(false);
    chart.getAxisRight().setDrawGridLines(true);
    chart.getXAxis().setDrawAxisLine(false);
    chart.getXAxis().setDrawGridLines(true);
    chart.getXAxis().setDrawLabels(true);
    chart.getAxisRight().setDrawLabels(false);
    chart.setTouchEnabled(true);
    chart.setDragEnabled(true);
    chart.setScaleEnabled(false);
    chart.setPinchZoom(false);
    Legend legend = chart.getLegend();
    legend.setEnabled(true);
    legend.setTextSize(25f);
    chart.setOnChartValueSelectedListener(this);

    String[] teams = getdata().split(":");

    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    for (int z = a; z < b; z++) {
      ArrayList<Entry> values = new ArrayList<>();
      if (qr_string.exists()) {
        String[] category = teams[z].split(";");
        String[] graph_data = category[19].split("-");

        for (int i = 0; i < graph_data.length; i++) {
          String datapoint = graph_data[i];
          values.add(new Entry(i, Integer.parseInt(datapoint)));
        }

        LineDataSet d = new LineDataSet(values, category[2]);
        d.setLineWidth(2.5f);
        d.setCircleRadius(4f);
        d.setDrawValues(false);

        int color = colors[z % colors.length];
        d.setColor(color);
        d.setCircleColor(color);
        dataSets.add(d);
      }

      LineData data = new LineData(dataSets);
      chart.setData(data);
      chart.invalidate();
    }
  }

  @Override
  public void onValueSelected(Entry e, Highlight h) {
    TextView selected_number = findViewById(R.id.selected_number);
    selected_number.setText(String.valueOf(h.getY()));
  }

  @Override
  public void onNothingSelected() {

  }

  private void team1(){
    TextView team1_field_1 = findViewById(R.id.team1_field_1);
    TextView team1_field_2 = findViewById(R.id.team1_field_2);
    TextView team1_field_3 = findViewById(R.id.team1_field_3);
    TextView team1_field_4 = findViewById(R.id.team1_field_4);
    TextView team1_field_5 = findViewById(R.id.team1_field_5);
    TextView team1_field_6 = findViewById(R.id.team1_field_6);
    TextView team1_field_7 = findViewById(R.id.team1_field_7);
    TextView team1_field_8 = findViewById(R.id.team1_field_8);
    TextView team1_field_9 = findViewById(R.id.team1_field_9);
    TextView team1_field_10 = findViewById(R.id.team1_field_10);
    TextView team1_field_11 = findViewById(R.id.team1_field_11);
    TextView team1_field_12 = findViewById(R.id.team1_field_12);
    TextView team1_field_13 = findViewById(R.id.team1_field_13);
    TextView team1_field_14 = findViewById(R.id.team1_field_14);
    TextView team1_field_15 = findViewById(R.id.team1_field_15);
    TextView team1_field_16 = findViewById(R.id.team1_field_16);
    TextView team1_field_17 = findViewById(R.id.team1_field_17);
    TextView team1_field_18 = findViewById(R.id.team1_field_18);

    String[] teams = getdata().split(":");
      String[] category = teams[0].split(";");
      team1_field_1.setText(category[0]);
      team1_field_2.setText(category[1]);
      team1_field_3.setText(category[2]);
      team1_field_4.setText(category[3]);
      team1_field_5.setText(category[4]);
      team1_field_6.setText(category[5]);
      team1_field_7.setText(category[6]);
      team1_field_8.setText(category[7]);
      team1_field_9.setText(category[8]);
      team1_field_10.setText(category[9]);
      team1_field_11.setText(category[10]);
      team1_field_12.setText(category[11]);
      team1_field_13.setText(category[12]);
      team1_field_14.setText(category[13]);
      team1_field_15.setText(category[14]);
      team1_field_16.setText(category[15]);
      team1_field_17.setText(category[16]);
      team1_field_18.setText(category[17]);
  }

  private void team2(){
    TextView team2_field_1 = findViewById(R.id.team2_field_1);
    TextView team2_field_2 = findViewById(R.id.team2_field_2);
    TextView team2_field_3 = findViewById(R.id.team2_field_3);
    TextView team2_field_4 = findViewById(R.id.team2_field_4);
    TextView team2_field_5 = findViewById(R.id.team2_field_5);
    TextView team2_field_6 = findViewById(R.id.team2_field_6);
    TextView team2_field_7 = findViewById(R.id.team2_field_7);
    TextView team2_field_8 = findViewById(R.id.team2_field_8);
    TextView team2_field_9 = findViewById(R.id.team2_field_9);
    TextView team2_field_10 = findViewById(R.id.team2_field_10);
    TextView team2_field_11 = findViewById(R.id.team2_field_11);
    TextView team2_field_12 = findViewById(R.id.team2_field_12);
    TextView team2_field_13 = findViewById(R.id.team2_field_13);
    TextView team2_field_14 = findViewById(R.id.team2_field_14);
    TextView team2_field_15 = findViewById(R.id.team2_field_15);
    TextView team2_field_16 = findViewById(R.id.team2_field_16);
    TextView team2_field_17 = findViewById(R.id.team2_field_17);
    TextView team2_field_18 = findViewById(R.id.team2_field_18);

    String[] teams = getdata().split(":");
    if (qr_string.exists()) {
      String[] category = teams[1].split(";");
      team2_field_1.setText(category[0]);
      team2_field_2.setText(category[1]);
      team2_field_3.setText(category[2]);
      team2_field_4.setText(category[3]);
      team2_field_5.setText(category[4]);
      team2_field_6.setText(category[5]);
      team2_field_7.setText(category[6]);
      team2_field_8.setText(category[7]);
      team2_field_9.setText(category[8]);
      team2_field_10.setText(category[9]);
      team2_field_11.setText(category[10]);
      team2_field_12.setText(category[11]);
      team2_field_13.setText(category[12]);
      team2_field_14.setText(category[13]);
      team2_field_15.setText(category[14]);
      team2_field_16.setText(category[15]);
      team2_field_17.setText(category[16]);
      team2_field_18.setText(category[17]);
    }
  }

  private void team3(){
    TextView team3_field_1 = findViewById(R.id.team3_field_1);
    TextView team3_field_2 = findViewById(R.id.team3_field_2);
    TextView team3_field_3 = findViewById(R.id.team3_field_3);
    TextView team3_field_4 = findViewById(R.id.team3_field_4);
    TextView team3_field_5 = findViewById(R.id.team3_field_5);
    TextView team3_field_6 = findViewById(R.id.team3_field_6);
    TextView team3_field_7 = findViewById(R.id.team3_field_7);
    TextView team3_field_8 = findViewById(R.id.team3_field_8);
    TextView team3_field_9 = findViewById(R.id.team3_field_9);
    TextView team3_field_10 = findViewById(R.id.team3_field_10);
    TextView team3_field_11 = findViewById(R.id.team3_field_11);
    TextView team3_field_12 = findViewById(R.id.team3_field_12);
    TextView team3_field_13 = findViewById(R.id.team3_field_13);
    TextView team3_field_14 = findViewById(R.id.team3_field_14);
    TextView team3_field_15 = findViewById(R.id.team3_field_15);
    TextView team3_field_16 = findViewById(R.id.team3_field_16);
    TextView team3_field_17 = findViewById(R.id.team3_field_17);
    TextView team3_field_18 = findViewById(R.id.team3_field_18);

    String[] teams = getdata().split(":");
    if (qr_string.exists()) {
      String[] category = teams[2].split(";");
      team3_field_1.setText(category[0]);
      team3_field_2.setText(category[1]);
      team3_field_3.setText(category[2]);
      team3_field_4.setText(category[3]);
      team3_field_5.setText(category[4]);
      team3_field_6.setText(category[5]);
      team3_field_7.setText(category[6]);
      team3_field_8.setText(category[7]);
      team3_field_9.setText(category[8]);
      team3_field_10.setText(category[9]);
      team3_field_11.setText(category[10]);
      team3_field_12.setText(category[11]);
      team3_field_13.setText(category[12]);
      team3_field_14.setText(category[13]);
      team3_field_15.setText(category[14]);
      team3_field_16.setText(category[15]);
      team3_field_17.setText(category[16]);
      team3_field_18.setText(category[17]);
    }
  }

  private void team4(){
    TextView team4_field_1 = findViewById(R.id.team4_field_1);
    TextView team4_field_2 = findViewById(R.id.team4_field_2);
    TextView team4_field_3 = findViewById(R.id.team4_field_3);
    TextView team4_field_4 = findViewById(R.id.team4_field_4);
    TextView team4_field_5 = findViewById(R.id.team4_field_5);
    TextView team4_field_6 = findViewById(R.id.team4_field_6);
    TextView team4_field_7 = findViewById(R.id.team4_field_7);
    TextView team4_field_8 = findViewById(R.id.team4_field_8);
    TextView team4_field_9 = findViewById(R.id.team4_field_9);
    TextView team4_field_10 = findViewById(R.id.team4_field_10);
    TextView team4_field_11 = findViewById(R.id.team4_field_11);
    TextView team4_field_12 = findViewById(R.id.team4_field_12);
    TextView team4_field_13 = findViewById(R.id.team4_field_13);
    TextView team4_field_14 = findViewById(R.id.team4_field_14);
    TextView team4_field_15 = findViewById(R.id.team4_field_15);
    TextView team4_field_16 = findViewById(R.id.team4_field_16);
    TextView team4_field_17 = findViewById(R.id.team4_field_17);
    TextView team4_field_18 = findViewById(R.id.team4_field_18);

    String[] teams = getdata().split(":");
    if (qr_string.exists()) {
      String[] category = teams[3].split(";");
      team4_field_1.setText(category[0]);
      team4_field_2.setText(category[1]);
      team4_field_3.setText(category[2]);
      team4_field_4.setText(category[3]);
      team4_field_5.setText(category[4]);
      team4_field_6.setText(category[5]);
      team4_field_7.setText(category[6]);
      team4_field_8.setText(category[7]);
      team4_field_9.setText(category[8]);
      team4_field_10.setText(category[9]);
      team4_field_11.setText(category[10]);
      team4_field_12.setText(category[11]);
      team4_field_13.setText(category[12]);
      team4_field_14.setText(category[13]);
      team4_field_15.setText(category[14]);
      team4_field_16.setText(category[15]);
      team4_field_17.setText(category[16]);
      team4_field_18.setText(category[17]);
    }
  }

  private void team5(){
    TextView team5_field_1 = findViewById(R.id.team5_field_1);
    TextView team5_field_2 = findViewById(R.id.team5_field_2);
    TextView team5_field_3 = findViewById(R.id.team5_field_3);
    TextView team5_field_4 = findViewById(R.id.team5_field_4);
    TextView team5_field_5 = findViewById(R.id.team5_field_5);
    TextView team5_field_6 = findViewById(R.id.team5_field_6);
    TextView team5_field_7 = findViewById(R.id.team5_field_7);
    TextView team5_field_8 = findViewById(R.id.team5_field_8);
    TextView team5_field_9 = findViewById(R.id.team5_field_9);
    TextView team5_field_10 = findViewById(R.id.team5_field_10);
    TextView team5_field_11 = findViewById(R.id.team5_field_11);
    TextView team5_field_12 = findViewById(R.id.team5_field_12);
    TextView team5_field_13 = findViewById(R.id.team5_field_13);
    TextView team5_field_14 = findViewById(R.id.team5_field_14);
    TextView team5_field_15 = findViewById(R.id.team5_field_15);
    TextView team5_field_16 = findViewById(R.id.team5_field_16);
    TextView team5_field_17 = findViewById(R.id.team5_field_17);
    TextView team5_field_18 = findViewById(R.id.team5_field_18);

    String[] teams = getdata().split(":");
    if (qr_string.exists()) {
      String[] category = teams[4].split(";");
      team5_field_1.setText(category[0]);
      team5_field_2.setText(category[1]);
      team5_field_3.setText(category[2]);
      team5_field_4.setText(category[3]);
      team5_field_5.setText(category[4]);
      team5_field_6.setText(category[5]);
      team5_field_7.setText(category[6]);
      team5_field_8.setText(category[7]);
      team5_field_9.setText(category[8]);
      team5_field_10.setText(category[9]);
      team5_field_11.setText(category[10]);
      team5_field_12.setText(category[11]);
      team5_field_13.setText(category[12]);
      team5_field_14.setText(category[13]);
      team5_field_15.setText(category[14]);
      team5_field_16.setText(category[15]);
      team5_field_17.setText(category[16]);
      team5_field_18.setText(category[17]);
    }
  }

  private void team6(){
    TextView team6_field_1 = findViewById(R.id.team6_field_1);
    TextView team6_field_2 = findViewById(R.id.team6_field_2);
    TextView team6_field_3 = findViewById(R.id.team6_field_3);
    TextView team6_field_4 = findViewById(R.id.team6_field_4);
    TextView team6_field_5 = findViewById(R.id.team6_field_5);
    TextView team6_field_6 = findViewById(R.id.team6_field_6);
    TextView team6_field_7 = findViewById(R.id.team6_field_7);
    TextView team6_field_8 = findViewById(R.id.team6_field_8);
    TextView team6_field_9 = findViewById(R.id.team6_field_9);
    TextView team6_field_10 = findViewById(R.id.team6_field_10);
    TextView team6_field_11 = findViewById(R.id.team6_field_11);
    TextView team6_field_12 = findViewById(R.id.team6_field_12);
    TextView team6_field_13 = findViewById(R.id.team6_field_13);
    TextView team6_field_14 = findViewById(R.id.team6_field_14);
    TextView team6_field_15 = findViewById(R.id.team6_field_15);
    TextView team6_field_16 = findViewById(R.id.team6_field_16);
    TextView team6_field_17 = findViewById(R.id.team6_field_17);
    TextView team6_field_18 = findViewById(R.id.team6_field_18);

    String[] teams = getdata().split(":");
    if (qr_string.exists()) {
      String[] category = teams[5].split(";");
      team6_field_1.setText(category[0]);
      team6_field_2.setText(category[1]);
      team6_field_3.setText(category[2]);
      team6_field_4.setText(category[3]);
      team6_field_5.setText(category[4]);
      team6_field_6.setText(category[5]);
      team6_field_7.setText(category[6]);
      team6_field_8.setText(category[7]);
      team6_field_9.setText(category[8]);
      team6_field_10.setText(category[9]);
      team6_field_11.setText(category[10]);
      team6_field_12.setText(category[11]);
      team6_field_13.setText(category[12]);
      team6_field_14.setText(category[13]);
      team6_field_15.setText(category[14]);
      team6_field_16.setText(category[15]);
      team6_field_17.setText(category[16]);
      team6_field_18.setText(category[17]);
    }
  }

}
