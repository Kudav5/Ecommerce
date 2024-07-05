package id.co.kasrt

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.io.IOException

class wajah : AppCompatActivity(), SurfaceHolder.Callback {

    private lateinit var surfaceView: SurfaceView
    private lateinit var buttonCapture: Button
    private var camera: Camera? = null
    private lateinit var surfaceHolder: SurfaceHolder

    private val REQUEST_CAMERA_PERMISSION = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wajah)

        surfaceView = findViewById(R.id.surfaceView)
        buttonCapture = findViewById(R.id.deteksi)

        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this)
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        } else {
            openCamera()
        }

        buttonCapture.setOnClickListener {
            captureImage()


        }
        // High-accuracy landmark detection and face classification
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

// Real-time contour detection
        val realTimeOpts = FaceDetectorOptions.Builder()
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        openCamera()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (surfaceHolder.surface == null) {
            return
        }
        camera?.stopPreview()
        startCameraPreview()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        camera?.release()
        camera = null
    }

    private fun openCamera() {
        camera = Camera.open()
        startCameraPreview()
    }

    private fun startCameraPreview() {
        try {
            camera?.apply {
                setPreviewDisplay(surfaceHolder)
                setDisplayOrientation(90)
                startPreview()
            }
        } catch (e: IOException) {
            Log.e("Camera", "Error setting up camera preview", e)
        }
    }

    private fun captureImage() {
        camera?.takePicture(null, null, Camera.PictureCallback { data, _ ->
            val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
            detectFaces(bitmap)
        })
    }

    private fun detectFaces(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .build()

        val detector = FaceDetection.getClient(options)
        detector.process(image)
            .addOnSuccessListener { faces ->
                for (face in faces) {
                    Log.d("Face Detection", "Face detected with bounds: ${face.boundingBox}")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Face Detection", "Error detecting faces: ${e.message}")
            }
    }
}
