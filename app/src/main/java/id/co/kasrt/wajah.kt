import android.media.FaceDetector
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_face_recognition.*
import org.tensorflow.lite.examples.face.FaceDetector
import org.tensorflow.lite.examples.face.FaceDetectorOptions

class wajah : AppCompatActivity() {

    private lateinit var faceDetector: FaceDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_recognition)

        faceDetector = FaceDetector(
            this,
            FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setContourMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
                .build()
        )

        button_detect_face.setOnClickListener {
            detectFace()
        }
    }

    private fun detectFace() {
        val preview = preview
        val overlay = overlay

        faceDetector.setProcessor(object : FaceDetector.Processor {
            override fun release() {}

            override fun receiveDetections(detections: FaceDetector.Detections) {
                overlay.setDetections(detections)
            }
        })

        preview.start(faceDetector)
    }

    override fun onDestroy() {
        super.onDestroy()
        faceDetector.close()
    }
}