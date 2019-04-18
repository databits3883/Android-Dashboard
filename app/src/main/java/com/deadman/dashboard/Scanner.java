package com.deadman.dashboard;

import android.app.Activity;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.zxing.Result;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends Activity implements ZXingScannerView.ResultHandler {

  File qr_string = new File(
      Environment.getExternalStorageDirectory() + File.separator + "FRC" + File.separator + "qr_string.txt");

  private ZXingScannerView mScannerView;

  @Override
  public void onCreate(Bundle state) {
    super.onCreate(state);
    mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view

    setContentView(R.layout.scanner);

    ViewGroup contentFrame = findViewById(R.id.content_frame);
    mScannerView = new ZXingScannerView(this);
    contentFrame.addView(mScannerView);
  }

  @Override
  public void onResume() {
    super.onResume();
    mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
    mScannerView.startCamera();          // Start camera on resume

    // Go Full screen
    View decorView = getWindow().getDecorView();
    int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    decorView.setSystemUiVisibility(uiOptions);
  }

  @Override
  public void onPause() {
    super.onPause();
    mScannerView.stopCamera();           // Stop camera on pause
  }

  // Function to create the stats.csv from the data gotten from the QR Code
  @Override
  public void handleResult(Result rawResult) {
    String results;
    results = rawResult.getText();
    try {
      FileOutputStream stream = new FileOutputStream(qr_string);
      stream.write(results.getBytes());
      stream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    rescan(qr_string.getAbsolutePath());

    this.onBackPressed();
  }


  // Function to scan the edited file so it shows up right away in MTP/OTG
  public void rescan(String file) {
    MediaScannerConnection.scanFile(this,
        new String[]{file}, null,
        (path, uri) -> {
          Log.i("ExternalStorage", "Scanned " + path + ":");
          Log.i("ExternalStorage", "-> uri=" + uri);
        });
  }
}