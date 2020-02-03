package com.myToDoList.activities

import android.Manifest.permission.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.myToDoList.R
import com.myToDoList.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private val GALLERY = 1
    private val PROFILECAMERA = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        fab_operatorProfile_pic.setOnClickListener { view ->
            if (!PermissionUtils.hasPermissions(
                    this,
                    CAMERA,
                    READ_EXTERNAL_STORAGE,
                    WRITE_EXTERNAL_STORAGE
                )
            ) {
                PermissionUtils.requestCameraPermissions(
                    this,
                    object : PermissionUtils.PermissionsListener {
                        override fun onPermissionRequest(granted: Boolean) {
                            if (granted) takePhotoFromCamera()
                        }
                    })

            } else {

            }
        }

        iv_profile_avatar.setOnClickListener {
            if (!PermissionUtils.hasPermissions(
                    this,
                    READ_EXTERNAL_STORAGE,
                    WRITE_EXTERNAL_STORAGE
                )
            ) {
                PermissionUtils.requestStoragePermissions(
                    this,
                    object : PermissionUtils.PermissionsListener {
                        override fun onPermissionRequest(granted: Boolean) {

                        }
                    })

            } else {
                choosePhotoFromGallary()
            }
        }


    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY);

    }

    fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, PROFILECAMERA)
    }


}
