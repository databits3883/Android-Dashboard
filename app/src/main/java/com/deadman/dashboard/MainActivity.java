package com.deadman.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener {

  File qr_string = new File(
      Environment.getExternalStorageDirectory() + File.separator + "FRC" + File.separator + "qr_string.txt");

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
      score();
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
          FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      qr_info = new String(bytes);
      String testing = Integer.toString(qr_info.split(";").length);
    Toast.makeText(this,testing,Toast.LENGTH_LONG).show();
    if(qr_info.split(";").length != 127){
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

  private void score() {
    TextView score_blue = findViewById(R.id.blue_score);
    TextView score_red = findViewById(R.id.red_score);
    String teams[] = getdata().split(":");
    String category1[] = teams[0].split(";");
    String category2[] = teams[1].split(";");
    String category3[] = teams[2].split(";");
    String category4[] = teams[3].split(";");
    String category5[] = teams[4].split(";");
    String category6[] = teams[5].split(";");
    score_red.setText(Integer.parseInt(category1[4]) + Integer.parseInt(category2[4]) + Integer.parseInt(category3[4]));
    score_blue.setText(Integer.parseInt(category4[4]) + Integer.parseInt(category5[4]) + Integer.parseInt(category6[4]));
  }

  private void team1(){
    TextView team1_title = findViewById(R.id.team1_number);
    TextView team1_defense_avg = findViewById(R.id.team1_defense_avg_data);
    TextView team1_cycles_avg = findViewById(R.id.team1_cycles_avg_data);
    TextView team1_cycles_max = findViewById(R.id.team1_cycles_max_data);
    TextView team1_climb_max = findViewById(R.id.team1_climb_max_data);
    TextView team1_hatch_avg = findViewById(R.id.team1_hatch_avg_data);
    TextView team1_hatch_max = findViewById(R.id.team1_hatch_max_data);
    TextView team1_climb_avg = findViewById(R.id.team1_climb_avg_data);
    TextView team1_cargo_avg = findViewById(R.id.team1_cargo_avg_data);
    TextView team1_cargo_max = findViewById(R.id.team1_cargo_max_data);

    String teams[] = getdata().split(":");
    if (qr_string.exists()) {
      String category[] = teams[0].split(";");
      team1_title.setText(category[1]);
      team1_defense_avg.setText(category[21]);
      team1_cycles_avg.setText(category[5]);
      team1_cycles_max.setText(category[6]);
      team1_climb_max.setText(category[16]);
      team1_hatch_avg.setText(category[12]);
      team1_hatch_max.setText(category[13]);
      team1_climb_avg.setText(category[15]);
      team1_cargo_avg.setText(category[18]);
      team1_cargo_max.setText(category[19]);
    }
  }

  private void team2(){
    TextView team2_title = findViewById(R.id.team2_number);
    TextView team2_defense_avg = findViewById(R.id.team2_defense_avg_data);
    TextView team2_cycles_avg = findViewById(R.id.team2_cycles_avg_data);
    TextView team2_cycles_max = findViewById(R.id.team2_cycles_max_data);
    TextView team2_climb_max = findViewById(R.id.team2_climb_max_data);
    TextView team2_hatch_avg = findViewById(R.id.team2_hatch_avg_data);
    TextView team2_hatch_max = findViewById(R.id.team2_hatch_max_data);
    TextView team2_climb_avg = findViewById(R.id.team2_climb_avg_data);
    TextView team2_cargo_avg = findViewById(R.id.team2_cargo_avg_data);
    TextView team2_cargo_max = findViewById(R.id.team2_cargo_max_data);

    String teams[] = getdata().split(":");
    if (qr_string.exists()) {
      String category[] = teams[1].split(";");
      team2_title.setText(category[1]);
      team2_defense_avg.setText(category[21]);
      team2_cycles_avg.setText(category[5]);
      team2_cycles_max.setText(category[6]);
      team2_climb_max.setText(category[16]);
      team2_hatch_avg.setText(category[12]);
      team2_hatch_max.setText(category[13]);
      team2_climb_avg.setText(category[15]);
      team2_cargo_avg.setText(category[18]);
      team2_cargo_max.setText(category[19]);
    }
  }

  private void team3(){
    TextView team3_title = findViewById(R.id.team3_number);
    TextView team3_defense_avg = findViewById(R.id.team3_defense_avg_data);
    TextView team3_cycles_avg = findViewById(R.id.team3_cycles_avg_data);
    TextView team3_cycles_max = findViewById(R.id.team3_cycles_max_data);
    TextView team3_climb_max = findViewById(R.id.team3_climb_max_data);
    TextView team3_hatch_avg = findViewById(R.id.team3_hatch_avg_data);
    TextView team3_hatch_max = findViewById(R.id.team3_hatch_max_data);
    TextView team3_climb_avg = findViewById(R.id.team3_climb_avg_data);
    TextView team3_cargo_avg = findViewById(R.id.team3_cargo_avg_data);
    TextView team3_cargo_max = findViewById(R.id.team3_cargo_max_data);

    String teams[] = getdata().split(":");
    if (qr_string.exists()) {
      String category[] = teams[2].split(";");
      team3_title.setText(category[1]);
      team3_defense_avg.setText(category[21]);
      team3_cycles_avg.setText(category[5]);
      team3_cycles_max.setText(category[6]);
      team3_climb_max.setText(category[16]);
      team3_hatch_avg.setText(category[12]);
      team3_hatch_max.setText(category[13]);
      team3_climb_avg.setText(category[15]);
      team3_cargo_avg.setText(category[18]);
      team3_cargo_max.setText(category[19]);
    }
  }

  private void team4(){
    TextView team4_title = findViewById(R.id.team4_number);
    TextView team4_defense_avg = findViewById(R.id.team4_defense_avg_data);
    TextView team4_cycles_avg = findViewById(R.id.team4_cycles_avg_data);
    TextView team4_cycles_max = findViewById(R.id.team4_cycles_max_data);
    TextView team4_climb_max = findViewById(R.id.team4_climb_max_data);
    TextView team4_hatch_avg = findViewById(R.id.team4_hatch_avg_data);
    TextView team4_hatch_max = findViewById(R.id.team4_hatch_max_data);
    TextView team4_climb_avg = findViewById(R.id.team4_climb_avg_data);
    TextView team4_cargo_avg = findViewById(R.id.team4_cargo_avg_data);
    TextView team4_cargo_max = findViewById(R.id.team4_cargo_max_data);

    String teams[] = getdata().split(":");
    if (qr_string.exists()) {
      String category[] = teams[3].split(";");
      team4_title.setText(category[1]);
      team4_defense_avg.setText(category[21]);
      team4_cycles_avg.setText(category[5]);
      team4_cycles_max.setText(category[6]);
      team4_climb_max.setText(category[16]);
      team4_hatch_avg.setText(category[12]);
      team4_hatch_max.setText(category[13]);
      team4_climb_avg.setText(category[15]);
      team4_cargo_avg.setText(category[18]);
      team4_cargo_max.setText(category[19]);
    }
  }

  private void team5(){
    TextView team5_title = findViewById(R.id.team5_number);
    TextView team5_defense_avg = findViewById(R.id.team5_defense_avg_data);
    TextView team5_cycles_avg = findViewById(R.id.team5_cycles_avg_data);
    TextView team5_cycles_max = findViewById(R.id.team5_cycles_max_data);
    TextView team5_climb_max = findViewById(R.id.team5_climb_max_data);
    TextView team5_hatch_avg = findViewById(R.id.team5_hatch_avg_data);
    TextView team5_hatch_max = findViewById(R.id.team5_hatch_max_data);
    TextView team5_climb_avg = findViewById(R.id.team5_climb_avg_data);
    TextView team5_cargo_avg = findViewById(R.id.team5_cargo_avg_data);
    TextView team5_cargo_max = findViewById(R.id.team5_cargo_max_data);

    String teams[] = getdata().split(":");
    if (qr_string.exists()) {
      String category[] = teams[4].split(";");
      team5_title.setText(category[1]);
      team5_defense_avg.setText(category[21]);
      team5_cycles_avg.setText(category[5]);
      team5_cycles_max.setText(category[6]);
      team5_climb_max.setText(category[16]);
      team5_hatch_avg.setText(category[12]);
      team5_hatch_max.setText(category[13]);
      team5_climb_avg.setText(category[15]);
      team5_cargo_avg.setText(category[18]);
      team5_cargo_max.setText(category[19]);
    }
  }

  private void team6(){
    TextView team6_title = findViewById(R.id.team6_number);
    TextView team6_defense_avg = findViewById(R.id.team6_defense_avg_data);
    TextView team6_cycles_avg = findViewById(R.id.team6_cycles_avg_data);
    TextView team6_cycles_max = findViewById(R.id.team6_cycles_max_data);
    TextView team6_climb_max = findViewById(R.id.team6_climb_max_data);
    TextView team6_hatch_avg = findViewById(R.id.team6_hatch_avg_data);
    TextView team6_hatch_max = findViewById(R.id.team6_hatch_max_data);
    TextView team6_climb_avg = findViewById(R.id.team6_climb_avg_data);
    TextView team6_cargo_avg = findViewById(R.id.team6_cargo_avg_data);
    TextView team6_cargo_max = findViewById(R.id.team6_cargo_max_data);

    String teams[] = getdata().split(":");
    if (qr_string.exists()) {
      String category[] = teams[5].split(";");
      team6_title.setText(category[1]);
      team6_defense_avg.setText(category[21]);
      team6_cycles_avg.setText(category[5]);
      team6_cycles_max.setText(category[6]);
      team6_climb_max.setText(category[16]);
      team6_hatch_avg.setText(category[12]);
      team6_hatch_max.setText(category[13]);
      team6_climb_avg.setText(category[15]);
      team6_cargo_avg.setText(category[18]);
      team6_cargo_max.setText(category[19]);
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
      String teams[] = getdata().split(":");
      if (qr_string.exists()) {
        String category[] = teams[z].split(";");
        String[] graph_data = category[6].split("-");

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
        d.setDrawValues(false);
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
      String teams[] = getdata().split(":");
      if (qr_string.exists()) {
        String category[] = teams[z].split(";");
        String[] graph_data = category[6].split("-");

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
        d.setDrawValues(false);
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
