package com.example.bbmr_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.util.concurrent.ExecutorService
import com.example.bbmr_project.databinding.ActivityLoadingSplashBinding
import com.example.bbmr_project.mlkit.faceDetectModel
import com.example.bbmr_project.mlkit.faceDrawable
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executors

class LoadingSplashActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityLoadingSplashBinding
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var faceDetector: FaceDetector
    private lateinit var imageCapture: ImageCapture
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoadingSplashBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)
        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
//        // 해당 함수 내에서 res = 1 일 경우 if문으로 해서 액티비티 이동 intent활용 해보면 됨
//        // 다음 인텐트로 이동
//        val intent = Intent("내가 보내야 하는 액티비티 화면", )
//        startActivity(intent)
//        finish()

    }

    private fun startCamera() {
        var cameraController = LifecycleCameraController(baseContext)
        val previewView: PreviewView = viewBinding.viewFinder
        // 카메라 전면 사용하기
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            .build()

        val preview = Preview.Builder().build()
        preview.setSurfaceProvider(previewView.surfaceProvider)

        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()
        val faceDetector = FaceDetection.getClient(options)

        // 이미지 캡처 객체를 생성하고 라이프 사이클에 바인딩하는 곳
        // 걍 이미지 캡처하는 곳이라고 보면 됨
        imageCapture = ImageCapture.Builder().build()




        cameraController.cameraSelector = cameraSelector
        // 1 프레임 당 실시하는 analyzer
        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(this),
            MlKitAnalyzer(
                listOf(faceDetector),
                COORDINATE_SYSTEM_VIEW_REFERENCED,
                ContextCompat.getMainExecutor(this)
            ) { result: MlKitAnalyzer.Result? ->
                val faceResults = result?.getValue(faceDetector)
                if ((faceResults == null) ||
                    (faceResults.size == 0) ||
                    (faceResults.first() == null)
                ) {
                    previewView.overlay.clear()
                    previewView.setOnTouchListener { _, _ -> false } //no-op
                    return@MlKitAnalyzer
                } else {        // 걍 스크린샷 캡처로 갑니다
                    imageCaptureAndSend()

//                    imageCapture.takePicture(
//                        ContextCompat.getMainExecutor(this),
//                        object: ImageCapture.OnImageCapturedCallback() {
//                            override fun onCaptureSuccess(image: ImageProxy) {
//                                // 이미지 성공 시
//
//                                val tag :String = "이미지 캡처: "
//                                Log.d(tag, "성공")
//                            }
//
//                            override fun onError(exception: ImageCaptureException) {
//                                val tag :String = "이미지 캡처: "
//                                Log.e(tag, "실패", exception)
//                            }
//                        }
//                    )

                    cameraController.unbind()
                }
                val faceDetectModel = faceDetectModel(faceResults[0])
                val faceDrawable = faceDrawable(faceDetectModel)
                previewView.overlay.clear()
                previewView.overlay.add(faceDrawable)
            }
        )

        cameraController.bindToLifecycle(this)

        previewView.controller = cameraController
        // cameraController.
    }

    //  private var previewView: PreviewView = viewBinding.viewFinder

    private fun imageCaptureAndSend() {
        val rootView : View = window.decorView.rootView
        rootView.isDrawingCacheEnabled = true
        val bitmap : Bitmap = Bitmap.createBitmap(rootView.drawingCache)
        rootView.isDrawingCacheEnabled = false

        // 스크린 샷의 이미지 짜르기
        val screenHeight = bitmap.height
        val cutHeight = (screenHeight * 0.2).toInt()
        val croppedBitmap = Bitmap.createBitmap(
            bitmap,
            0,
            cutHeight,
            bitmap.width,
            screenHeight - 2 * cutHeight
        )
        val byteArrayOutputStream = ByteArrayOutputStream()
        croppedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        // 이 곳에 byteArray를 받아와서 전송하시면 됩니다!








        val tag: String = "이미지 캡처: "
        Log.d(tag, "성공")
        var res : String = "0"
        val Handler = Handler(Looper.getMainLooper())
        // 일반 고객으로 판단 될 경우
        if (res == "0") {
            viewBinding.progressBar.visibility = View.INVISIBLE
            viewBinding.tvguide1.text = "고객님"
            viewBinding.tvguide2.text = "환영합니다"
//            viewBinding.pb.
            Handler.postDelayed({
                val intent = Intent(this, Normal_IntroActivity::class.java)
                startActivity(intent)
                finish()
            }, 800)

            // 시니어로 판단 될 경우
        } else if (res == "1") {
            viewBinding.progressBar.visibility = View.INVISIBLE
            viewBinding.tvguide1.text = "시니어님"
            viewBinding.tvguide2.text = "환영합니다"
            Handler.postDelayed({
                val intent = Intent(this, Senior_IntroActivity::class.java)
                startActivity(intent)
                finish()
            }, 800)

        }

    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        faceDetector.close()
    }

    companion object {
        private const val TAG = "CameraX-MLKit"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA
            ).toTypedArray()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}