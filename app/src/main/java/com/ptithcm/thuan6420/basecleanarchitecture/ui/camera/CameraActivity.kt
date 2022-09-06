package com.ptithcm.thuan6420.basecleanarchitecture.ui.camera

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.FILE_NAME_FORMAT
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.REQUEST_CODE_PERMISSION
import com.ptithcm.thuan6420.basecleanarchitecture.R
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.ActivityCameraBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var imageCapture: ImageCapture ?= null
    private lateinit var outputDirectory: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isPermissionGranted().not()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE_PERMISSION
            )
        }

        outputDirectory = getOutputDirectory()

        binding.btnTakePhoto.setOnClickListener {
            takePhoto()
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply {
                mkdir()
            }
        }

        return if (mediaDir?.exists() == true) mediaDir else filesDir
    }

    private fun takePhoto() {
        imageCapture ?: return
        val photoFile = File(
            outputDirectory,
            "${SimpleDateFormat(FILE_NAME_FORMAT,
                Locale.getDefault()).format(System.currentTimeMillis())}.jpg"
        )

        val outputOption = ImageCapture
            .OutputFileOptions.Builder(photoFile)
            .build()

        imageCapture!!.takePicture(
            outputOption,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val saveUri = Uri.fromFile(photoFile)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("T64", "Save image failed : ", exception)
                }
            }
        )
    }

    private fun isPermissionGranted() = Manifest.permission.CAMERA.all {
        ContextCompat.checkSelfPermission(
            baseContext, it.toString()
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (isPermissionGranted().not()) {
                startCamera()
            } else {
                finish()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider
            .getInstance(this)
        val cameraProvider = cameraProviderFuture.get()

        cameraProviderFuture.addListener({
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(
                        binding.pvPhoto.surfaceProvider
                    )
                }
            imageCapture = ImageCapture.Builder()
                .build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector,
                    preview, imageCapture
                )
            } catch (exception: Exception) {
                Log.e("T64", "start Camera failed :", exception)
            }
        }, ContextCompat.getMainExecutor(this))
    }
}