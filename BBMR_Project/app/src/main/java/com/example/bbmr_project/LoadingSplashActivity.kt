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
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bbmr_project.RetrofitAPI.ApiService
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
        // ------ emulator 작업 시 해당 activity 1초 후 자동 이동 ------
//        val Handler = Handler(Looper.getMainLooper())
//        Handler.postDelayed({
//            val intent = Intent(this, Normal_IntroActivity::class.java)
//            startActivity(intent)
//            finish()
//
//        }, 1000)


    }

    private fun startCamera() {
        var cameraController = LifecycleCameraController(baseContext)


        val previewView: PreviewView = viewBinding.viewFinder
        // 카메라 전면 사용하기
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            .build()
        cameraController.cameraSelector = cameraSelector

        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()

        faceDetector = FaceDetection.getClient(options)

        // 이미지 캡처 객체를 생성하고 라이프 사이클에 바인딩하는 곳
        // 걍 이미지 캡처하는 곳이라고 보면 됨
        imageCapture = ImageCapture.Builder().build()

        // 1 프레임 당 실시하는 analyzer
        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(this),
            MlKitAnalyzer(
                listOf(faceDetector),
                COORDINATE_SYSTEM_VIEW_REFERENCED,
                ContextCompat.getMainExecutor(this)
            ) { result: MlKitAnalyzer.Result? ->
                val faceResults = result?.getValue(faceDetector)
                if (faceResults != null && faceResults.isNotEmpty()) {
                    imageCaptureAndSend(cameraController)

                }
                previewView.overlay.clear()
            }

        )
        cameraController.bindToLifecycle(this)
        previewView.controller = cameraController
    }

    private fun imageCaptureAndSend(cameraController: LifecycleCameraController) {
        // 카메라 전면 사용하기
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            .build()
        cameraController.cameraSelector = cameraSelector
        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(this)
        ) { imageProxy ->
            // 여기서 imageProxy를 사용하여 필요한 분석을 수행
            val bitmap = imageProxy.toBitmap()
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val filePath = saveBitmapToFile(this, bitmap)
            Log.d("확인용 로그", "filePath 성공")
            filePath?.let {
                // 파일로부터 MultipartBody.Part 생성
                val multipartBody = createMultipartBodyPartFromFile(it)
                Log.d("확인용 로그", "multipartBody 성공")
                // Retrofit 인스턴스 생성 및 이미지 전송
                val retrofit = Retrofit.Builder()
                    .baseUrl(getString(R.string.baseUrl)) // 서버 URL 설정/
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(ApiService::class.java)
                service.uploadImage(multipartBody).enqueue(object : Callback<RfAPI> {
                    override fun onResponse(call: Call<RfAPI>, response: Response<RfAPI>) {
                        if (response.isSuccessful) {
                            val serverResponse = response.body()
                            serverResponse?.let {
                                // 서버로부터 받은 결과를 처리
                                Log.d("예측값", "$serverResponse")
                                processServerResponse(it.result)
                            }
                        }else{
                            Log.d("서버 응답 코드", "${response.code()}")
                        }
                    }
                    override fun onFailure(call: Call<RfAPI>, t: Throwable) {
                        // 서버 통신 실패 처리
                        Log.e(TAG, "Server communication failed: ", t)
                    }
                })
            }
            // 처리가 완료된 후에는 imageProxy를 해제해야 합니다.
            cameraController.unbind()
            imageProxy.close()
        }
    }


    private fun processServerResponse(result: String) {
        // 결과에 따라 다른 액션을 수행합니다.
        // 예를 들어, 결과에 따라 다른 Activity를 시작할 수 있습니다.
        when (result) {
            "1" -> {
                viewBinding.progressBar.visibility = View.INVISIBLE
                viewBinding.tvguide1.text = "고객님"
                viewBinding.tvguide2.text = "안녕하세요"
                // 일반 고객으로 판단된 경우
                val Handler = Handler(Looper.getMainLooper())
                Handler.postDelayed({
                    val intent = Intent(this, Normal_IntroActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 1200)
            }
            "0" -> {
                // 시니어 고객으로 판단된 경우
                viewBinding.progressBar.visibility = View.INVISIBLE
                viewBinding.tvguide1.text = "시니어님"
                viewBinding.tvguide2.text = "환영합니다"
                val Handler = Handler(Looper.getMainLooper())
                Handler.postDelayed({
                    val intent = Intent(this, SeniorIntroActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 1200)
            }
            else -> {
                // 예외 처리
                Log.e(TAG, "Unexpected server response")
            }
        }
        finish()
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
//        cameraController.unbind()
//        viewBinding.previewView.controller = null
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