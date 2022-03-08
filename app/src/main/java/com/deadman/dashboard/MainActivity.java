package com.deadman.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

  private LineChart chart;

  private final int[] colors = new int[] {
          ColorTemplate.COLORFUL_COLORS [3],
      ColorTemplate.LIBERTY_COLORS  [3],
          ColorTemplate.COLORFUL_COLORS [2],
  };

  private final int[] colors2 = new int[] {
          ColorTemplate.COLORFUL_COLORS [3],
      ColorTemplate.MATERIAL_COLORS [2],
          ColorTemplate.COLORFUL_COLORS [2],
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button qr_button = findViewById(R.id.qr_button);
    qr_button.setOnClickListener(view -> scanner());

    AutoPermissions.Companion.loadAllPermissions(this, 1);
  }

  @Override
  public void onResume() {
    super.onResume();
    // Go Full screen
    View decorView = getWindow().getDecorView();
    int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    decorView.setSystemUiVisibility(uiOptions);

    if (qr_string.exists()) {
      team1();
      team2();
      team3();
      team4();
      team5();
      team6();
      chart1();
      chart2();
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
    String testing = Integer.toString(qr_info.split(";").length);
    Toast.makeText(this,testing,Toast.LENGTH_SHORT).show();
    if (qr_info.split(";").length != 127) {
      qr_string.delete();
      if (isFirstTime()) {
        new AlertDialog.Builder(this)
            .setMessage("The QR Code scanned was invalid, please rescan it")
            .setTitle("Missing QR Code")
            .setPositiveButton("Ok", (dialog, id) -> {
              reset_isFirstTime();
              scanner();})
            .show();
      }
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

  private void team1(){
    TextView team1_title = findViewById(R.id.team1_number);
    TextView team1_defense_avg = findViewById(R.id.team1_defense_avg_data);
    TextView team1_tarmac_move = findViewById(R.id.team1_tarmac_move_data);
    TextView team1_climb_max = findViewById(R.id.team1_climb_max_data);
    TextView team1_auto_max = findViewById(R.id.team1_auto_max_data);
    TextView team1_tele_max = findViewById(R.id.team1_tele_max_data);
    TextView team1_climb_avg = findViewById(R.id.team1_climb_avg_data);
    TextView team1_auto_avg = findViewById(R.id.team1_auto_avg_data);
    TextView team1_tele_avg = findViewById(R.id.team1_tele_avg_data);

    String[] teams = getdata().split(":");
    if (qr_string.exists()) {
      String[] category = teams[0].split(";");
      team1_title.setText(category[1]);
      team1_defense_avg.setText(category[5]);
      team1_tarmac_move.setText(category[7]);
      team1_climb_max.setText(category[9]);
      team1_auto_max.setText(category[13]);
      team1_climb_avg.setText(category[10]);
      team1_tele_max.setText(category[16]);
      team1_auto_avg.setText(category[12]);
      team1_tele_avg.setText(category[15]);
    }
  }

  private void team2(){
    TextView team2_title = findViewById(R.id.team2_number);
    TextView team2_defense_avg = findViewById(R.id.team2_defense_avg_data);
    TextView team2_tarmac_move = findViewById(R.id.team2_tarmac_move_data);
    TextView team2_climb_max = findViewById(R.id.team2_climb_max_data);
    TextView team2_auto_max = findViewById(R.id.team2_auto_max_data);
    TextView team2_tele_max = findViewById(R.id.team2_tele_max_data);
    TextView team2_climb_avg = findViewById(R.id.team2_climb_avg_data);
    TextView team2_auto_avg = findViewById(R.id.team2_auto_avg_data);
    TextView team2_tele_avg = findViewById(R.id.team2_tele_avg_data);

    String[] teams = getdata().split(":");
    if (qr_string.exists()) {
      String[] category = teams[1].split(";");
      team2_title.setText(category[1]);
      team2_defense_avg.setText(category[5]);
      team2_tarmac_move.setText(category[7]);
      team2_climb_max.setText(category[9]);
      team2_auto_max.setText(category[13]);
      team2_climb_avg.setText(category[10]);
      team2_tele_max.setText(category[16]);
      team2_auto_avg.setText(category[12]);
      team2_tele_avg.setText(category[15]);
    }
  }

  private void team3(){
    TextView team3_title = findViewById(R.id.team3_number);
    TextView team3_defense_avg = findViewById(R.id.team3_defense_avg_data);
    TextView team3_tarmac_move = findViewById(R.id.team3_tarmac_move_data);
    TextView team3_climb_max = findViewById(R.id.team3_climb_max_data);
    TextView team3_auto_max = findViewById(R.id.team3_auto_max_data);
    TextView team3_tele_max = findViewById(R.id.team3_tele_max_data);
    TextView team3_climb_avg = findViewById(R.id.team3_climb_avg_data);
    TextView team3_auto_avg = findViewById(R.id.team3_auto_avg_data);
    TextView team3_tele_avg = findViewById(R.id.team3_tele_avg_data);

    String[] teams = getdata().split(":");
    if (qr_string.exists()) {
      String[] category = teams[2].split(";");
      team3_title.setText(category[1]);
      team3_defense_avg.setText(category[5]);
      team3_tarmac_move.setText(category[7]);
      team3_climb_max.setText(category[9]);
      team3_auto_max.setText(category[13]);
      team3_climb_avg.setText(category[10]);
      team3_tele_max.setText(category[16]);
      team3_auto_avg.setText(category[12]);
      team3_tele_avg.setText(category[15]);
    }
  }

  private void team4(){
    TextView team4_title = findViewById(R.id.team4_number);
    TextView team4_defense_avg = findViewById(R.id.team4_defense_avg_data);
    TextView team4_tarmac_move = findViewById(R.id.team4_tarmac_move_data);
    TextView team4_climb_max = findViewById(R.id.team4_climb_max_data);
    TextView team4_auto_max = findViewById(R.id.team4_auto_max_data);
    TextView team4_tele_max = findViewById(R.id.team4_tele_max_data);
    TextView team4_climb_avg = findViewById(R.id.team4_climb_avg_data);
    TextView team4_auto_avg = findViewById(R.id.team4_auto_avg_data);
    TextView team4_tele_avg = findViewById(R.id.team4_tele_avg_data);

    String[] teams = getdata().split(":");
    if (qr_string.exists()) {
      String[] category = teams[3].split(";");
      team4_title.setText(category[1]);
      team4_defense_avg.setText(category[5]);
      team4_tarmac_move.setText(category[7]);
      team4_climb_max.setText(category[9]);
      team4_auto_max.setText(category[13]);
      team4_climb_avg.setText(category[10]);
      team4_tele_max.setText(category[16]);
      team4_auto_avg.setText(category[12]);
      team4_tele_avg.setText(category[15]);
    }
  }

  private void team5(){
    TextView team5_title = findViewById(R.id.team5_number);
    TextView team5_defense_avg = findViewById(R.id.team5_defense_avg_data);
    TextView team5_tarmac_move = findViewById(R.id.team5_tarmac_move_data);
    TextView team5_climb_max = findViewById(R.id.team5_climb_max_data);
    TextView team5_auto_max = findViewById(R.id.team5_auto_max_data);
    TextView team5_tele_max = findViewById(R.id.team5_tele_max_data);
    TextView team5_climb_avg = findViewById(R.id.team5_climb_avg_data);
    TextView team5_auto_avg = findViewById(R.id.team5_auto_avg_data);
    TextView team5_tele_avg = findViewById(R.id.team5_tele_avg_data);

    String[] teams = getdata().split(":");
    if (qr_string.exists()) {
      String[] category = teams[4].split(";");
      team5_title.setText(category[1]);
      team5_defense_avg.setText(category[5]);
      team5_tarmac_move.setText(category[7]);
      team5_climb_max.setText(category[9]);
      team5_auto_max.setText(category[13]);
      team5_climb_avg.setText(category[10]);
      team5_tele_max.setText(category[16]);
      team5_auto_avg.setText(category[12]);
      team5_tele_avg.setText(category[15]);
    }
  }

  private void team6(){
    TextView team6_title = findViewById(R.id.team6_number);
    TextView team6_defense_avg = findViewById(R.id.team6_defense_avg_data);
    TextView team6_tarmac_move = findViewById(R.id.team6_tarmac_move_data);
    TextView team6_climb_max = findViewById(R.id.team6_climb_max_data);
    TextView team6_auto_max = findViewById(R.id.team6_auto_max_data);
    TextView team6_tele_max = findViewById(R.id.team6_tele_max_data);
    TextView team6_climb_avg = findViewById(R.id.team6_climb_avg_data);
    TextView team6_auto_avg = findViewById(R.id.team6_auto_avg_data);
    TextView team6_tele_avg = findViewById(R.id.team6_tele_avg_data);

    String[] teams = getdata().split(":");
    if (qr_string.exists()) {
      String[] category = teams[5].split(";");
      team6_title.setText(category[1]);
      team6_defense_avg.setText(category[5]);
      team6_tarmac_move.setText(category[7]);
      team6_climb_max.setText(category[9]);
      team6_auto_max.setText(category[13]);
      team6_climb_avg.setText(category[10]);
      team6_tele_max.setText(category[16]);
      team6_auto_avg.setText(category[12]);
      team6_tele_avg.setText(category[15]);
    }
  }

  private void scanner(){
    Intent intent = new Intent(this, Scanner.class);
    startActivity(intent);
  }

  private void chartinfo(int id){
    chart = findViewById(id);
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
    chart.setTouchEnabled(true);
    chart.setDragEnabled(true);
    chart.setScaleEnabled(false);
    chart.setPinchZoom(false);
    Legend legend = chart.getLegend();
    legend.setEnabled(false);
    chart.setOnChartValueSelectedListener(this);
  }

  private void chart1(){
    chartinfo(R.id.chart1);
    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    for (int z = 0; z < 3; z++) {
      ArrayList<Entry> values = new ArrayList<>();
      String[] teams = getdata().split(":");
      if (qr_string.exists()) {
        String[] category = teams[z].split(";");
        String[] graph_data = category[3].split("&");

        for (int i = 0; i < graph_data.length; i++) {
          String datapoint = graph_data[i];
          values.add(new Entry(i, Integer.parseInt(datapoint)));
        }

        LineDataSet d = new LineDataSet(values, "Red " + (z + 1));
        d.setLineWidth(2.5f);
        d.setCircleRadius(4f);

        int color = colors[z % colors.length];
        d.setColor(color);
        d.setCircleColor(color);
        d.setDrawValues(true);
        dataSets.add(d);
      }

      LineData data = new LineData(dataSets);
      chart.setData(data);
      chart.invalidate();
    }
  }

  private void chart2(){
    chartinfo(R.id.chart2);
    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    for (int z = 3; z < 6; z++) {
      ArrayList<Entry> values = new ArrayList<>();
      String[] teams = getdata().split(":");
      if (qr_string.exists()) {
        String[] category = teams[z].split(";");
        String[] graph_data = category[3].split("&");

        for (int i = 0; i < graph_data.length; i++) {
          String datapoint = graph_data[i];
          values.add(new Entry(i, Integer.parseInt(datapoint)));
        }

        LineDataSet d = new LineDataSet(values, "Blue " + (z + 1));
        d.setLineWidth(2.5f);
        d.setCircleRadius(4f);

        int color = colors2[z % colors2.length];
        d.setColor(color);
        d.setCircleColor(color);
        d.setDrawValues(true);
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
}
