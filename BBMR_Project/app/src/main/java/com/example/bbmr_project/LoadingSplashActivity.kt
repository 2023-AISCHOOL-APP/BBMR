package com.example.bbmr_project

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bbmr_project.databinding.ActivityMainBinding
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.util.concurrent.ExecutorService
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraController
import com.example.bbmr_project.RetrofitAPI.ImageUploadResponse
import com.example.bbmr_project.RetrofitAPI.RetrofitAPI
import com.example.bbmr_project.databinding.ActivityLoadingSplashBinding
import com.example.bbmr_project.mlkit.faceDetectModel
import com.example.bbmr_project.mlkit.faceDrawable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executors


class LoadingSplashActivity : AppCompatActivity() {

    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var viewBinding: ActivityLoadingSplashBinding
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var faceDetector: FaceDetector
    private lateinit var cameraController: LifecycleCameraController // 231120 --  CameraController 중복 선언으로 인한 수정
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

                    // cameraController.unbind() -- 231120 카메라가 언바인딩 되어 이미지 캡쳐가 불가능하여 주석처리
                }
                val faceDetectModel = faceDetectModel(faceResults[0])
                val faceDrawable = faceDrawable(faceDetectModel)
                previewView.overlay.clear()
                previewView.overlay.add(faceDrawable)
            }
        )

        cameraController.bindToLifecycle(this)

        previewView.controller = cameraController

    }
    // --------- 231120 imageProxyToBitmap 함수 코드 추가
    private fun imageProxyToBitmap(imageProxy: ImageProxy): Bitmap {
        val buffer = imageProxy.planes[0].buffer
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    // ----------- 231120 새로운 이미지 캡쳐 방식 : 카메라 프리뷰와 실제 캡쳐된 이미지가 달라지는 것을 방지하기 위함
    private fun imageCaptureAndSend() {
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(imageProxy: ImageProxy) {
                    // imageProxy를 사용하여 Bitmap으로 변환
                    val bitmap = imageProxyToBitmap(imageProxy)
                    imageProxy.close()

                    // Bitmap을 MultipartBody.Part로 변환 및 업로드 로직
                    uploadBitmap(bitmap)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "이미지 캡처 실패: ${exception.message}", exception)
                }
            }
        )
    }

    private fun uploadBitmap(bitmap: Bitmap) {
        // Bitmap을 ByteArrayOutputStream을 사용하여 바이트 배열로 변환
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        // Bitmap을 MultipartBody.Part로 변환
        val imageRequestBody = RequestBody.create(MediaType.parse("image/png"), byteArray)
        val imagePart = MultipartBody.Part.createFormData("image", "image.png", imageRequestBody)

        // Retrofit API를 사용하여 이미지 업로드
        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.baseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(RetrofitAPI::class.java)
        val call = apiService.uploadImage(imagePart)

        call.enqueue(object : Callback<ImageUploadResponse> {
            override fun onResponse(call: Call<ImageUploadResponse>, response: Response<ImageUploadResponse>) {
                // 231120 -- 결과에 따른 화면 이동을 위해 주석처리
//                if (response.isSuccessful) {
//                    val result = response.body()?.result
//                    // 서버로부터 받은 결과 처리
//                    Log.d(TAG, "결과는: $result 성공")
//                } else {
//                    // 서버로부터 실패 응답을 받았을 때의 처리
//                    Log.d(TAG, "서버 응답 실패")
//                }
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.result
                    handleServerResult(result)  // 결과에 따른 화면 이동 로직
                } else {
                    Log.d(TAG, "서버 응답 실패")
                }
            }

            override fun onFailure(call: Call<ImageUploadResponse>, t: Throwable) {
                // 네트워크 요청 실패 시의 처리
                Log.e(TAG, "이미지 업로드 실패", t)
            }
        })
    }

    // 231120 --- 결과에 따른 화면 이동 로직 추가 함수
    private fun handleServerResult(result: String) {
        when (result) {
            "0" -> moveToSeniorScreen()
            "1" -> moveToGeneralScreen()
            else -> {
                // 예외 처리
            }
        }
    }

    private fun moveToSeniorScreen() {
        // 시니어용 화면으로 이동하는 로직
        val intent = Intent(this, Senior_IntroActivity::class.java)
        startActivity(intent)
    }

    private fun moveToGeneralScreen() {
        // 일반용 화면으로 이동하는 로직
        val intent = Intent(this, Normal_IntroActivity::class.java)
        startActivity(intent)
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