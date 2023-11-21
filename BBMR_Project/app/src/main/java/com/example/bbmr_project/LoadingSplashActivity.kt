package com.example.bbmr_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Context
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
import com.example.bbmr_project.RetrofitAPI.ApiService
import com.example.bbmr_project.RetrofitAPI.RetrofitAPI
import com.example.bbmr_project.RetrofitAPI.RfAPI
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.util.concurrent.ExecutorService
import com.example.bbmr_project.databinding.ActivityLoadingSplashBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
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
        var cameraController = LifecycleCameraController(baseContext) // cameraController를 생성
        val previewView: PreviewView = viewBinding.viewFinder // 카메라 미리보기를 표시
        // 카메라 전면 사용하기 -> 얼굴 캡쳐에 사용
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            .build()
        // surfaceProvider에 연결하여 카메라의 실시간 미리보기를 화면에 표시
        val preview = Preview.Builder().build()
        preview.setSurfaceProvider(previewView.surfaceProvider)
        // 성능 모드와 윤곽 모드를 지정 -> Mlkit의 얼굴감지 기능에 사용
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()
        // FaceDetection 클라이언트를 초기화
        faceDetector = FaceDetection.getClient(options)

        // 이미지 캡처 객체를 생성하고 라이프 사이클에 바인딩하는 곳
        // 걍 이미지 캡처하는 곳이라고 보면 됨
        imageCapture = ImageCapture.Builder().build() // imageCapture 객체를 생성, 실제 이미지 캡처 작업에 사용


        cameraController.cameraSelector = cameraSelector
        // 1 프레임 당 실시하는 analyzer
        var captureExecuted = false
        cameraController.setImageAnalysisAnalyzer( // 이미지 분석(setImageAnalysisAnalyzer를 사용)
            ContextCompat.getMainExecutor(this),
            MlKitAnalyzer( // 카메라 프레임마다 얼굴 감지 수행
                listOf(faceDetector),
                COORDINATE_SYSTEM_VIEW_REFERENCED,
                ContextCompat.getMainExecutor(this)
            ) { result: MlKitAnalyzer.Result? ->
                val faceResults = result?.getValue(faceDetector)
                if (faceResults != null && faceResults.isNotEmpty() && !captureExecuted) {
                    imageCaptureAndSend()
                    captureExecuted = true
                } // 감지된 얼굴이 있을 경우 faceResults.isNotEmpty(), imageCaptureAndSend 매서드를 호출하여 이미지 캡처 및 처리를 수행
                previewView.overlay.clear() // 오버레이를 정리하여 화면을 갱신
            }
        )

        cameraController.bindToLifecycle(this) // ameraController를 액티비티의 라이프사이클에 바인딩하여 액티비티와 동기화
        previewView.controller = cameraController
    }

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


        val filePath = saveBitmapToFile(this, croppedBitmap)
        filePath?.let {
            // 파일로부터 MultipartBody.Part 생성
            val multipartBody = createMultipartBodyPartFromFile(it)

            // Retrofit 인스턴스 생성 및 이미지 전송
            val retrofit = Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl)) // 서버 URL 설정
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(ApiService::class.java)
            service.uploadImage(multipartBody).enqueue(object : Callback<RfAPI> {
                override fun onResponse(call: Call<RfAPI>, response: Response<RfAPI>) {
                    val serverResponse = response.body()
                    if (response.isSuccessful) {
                        val serverResponse = response.body()
                        serverResponse?.let {
                            // 서버로부터 받은 결과를 처리
                            Log.d("예측값 -> ","$serverResponse")
                            processServerResponse(it.result)
                        }
                    }

                }
                override fun onFailure(call: Call<RfAPI>, t: Throwable) {
                    // 서버 통신 실패 처리
                    Log.e(TAG, "Server communication failed: ", t)
                }
            })
        }

    }
    private fun processServerResponse(result: String) {
        // 결과에 따라 다른 액션을 수행합니다.
        // 예를 들어, 결과에 따라 다른 Activity를 시작할 수 있습니다.
        when (result) {
            "1" -> {
                // 일반 고객으로 판단된 경우
                val intent = Intent(this, Normal_IntroActivity::class.java)
                startActivity(intent)
                finish()
            }
            "0" -> {
                // 시니어 고객으로 판단된 경우
                val intent = Intent(this, Senior_IntroActivity::class.java)
                startActivity(intent)
                finish()
            }
            else -> {
                // 예외 처리
                Log.e(TAG, "Unexpected server response")
            }
        }
    }
    private fun saveBitmapToFile(context: Context, bitmap: Bitmap): String? {
        val fileName = "image_${System.currentTimeMillis()}.png"
        val file = File(context.filesDir, fileName)
        return try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    private fun createMultipartBodyPartFromFile(filePath: String): MultipartBody.Part {
        val file = File(filePath)
        val mediaType = "image/png".toMediaTypeOrNull()
        val requestBody = mediaType?.let { RequestBody.create(it, file) }
        return MultipartBody.Part.createFormData("image", file.name, requestBody!!)
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