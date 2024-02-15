package com.mauto.myapplication.batteryscanning

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.mauto.myapplication.R

import java.io.IOException

class ScannedBarcodeActivity : AppCompatActivity() {
    var surfaceView: SurfaceView? = null
    private var barcodeDetector: BarcodeDetector? = null
    private var cameraSource: CameraSource? = null
    var txtBarcodeValue: TextView? = null
    var intentData = ""
    var order_barcode = ""
    var page: String? = "delivery"
    var btnAction: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanned_barcode)
        initViews()
    }

    private fun initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue)
        surfaceView = findViewById(R.id.surfaceView)
        btnAction = findViewById(R.id.button)
        val intent = intent
        page = intent.getStringExtra("page")
        //        if (page.equalsIgnoreCase("delivery")){
//            order_barcode = intent.getStringExtra("barcode_text");
//        }
        btnAction?.setOnClickListener(View.OnClickListener {
            if (intentData.length > 0) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(intentData)))
            }
        })
    }

    private fun initialiseDetectorsAndSources() {
        Toast.makeText(applicationContext, "Barcode scanner started", Toast.LENGTH_SHORT).show()
        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()
        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()
        surfaceView!!.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            this@ScannedBarcodeActivity,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraSource?.start(surfaceView!!.holder)
                    } else {
                        ActivityCompat.requestPermissions(
                            this@ScannedBarcodeActivity, arrayOf(
                                Manifest.permission.CAMERA
                            ), REQUEST_CAMERA_PERMISSION
                        )
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource?.stop()
            }
        })
        barcodeDetector?.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
//                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            override fun receiveDetections(detections: Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() != 0) {
                    txtBarcodeValue!!.post {
                        if (barcodes.valueAt(0).rawValue.length > 0) {
                            txtBarcodeValue!!.removeCallbacks(null)
                            intentData = barcodes.valueAt(0).displayValue
                            Log.d("batteriy_code", intentData)
                            //                                if (order_barcode.equalsIgnoreCase(intentData)){
                            txtBarcodeValue!!.text = "Barcode Detected..!"
                            val intent_result = Intent()
                            intent_result.putExtra("code", intentData)
                            setResult(RESULT_OK, intent_result)
                            finish()
                            //                                } else {
                            //                                    txtBarcodeValue.setText("Barcode Not Matched, Try Agin..!");
                            //                                }
                            //                                if (page.equalsIgnoreCase("delivery")){
                            //
                            //                                } else {
                            //                                    txtBarcodeValue.setText("Barcode Detected..!");
                            //                                    Intent intent_result = new Intent();
                            //                                    intent_result.putExtra("code", intentData);
                            //                                    setResult(Activity.RESULT_OK, intent_result);
                            //                                    finish();
                            //                                }
                        } else {
                            //                                isEmail = false;
                            btnAction!!.text = "LAUNCH URL"
                            intentData = barcodes.valueAt(0).displayValue
                            //                                txtBarcodeValue.setText(intentData);
                        }
                    }
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        cameraSource!!.release()
    }

    override fun onResume() {
        super.onResume()
        initialiseDetectorsAndSources()
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 201
    }
}