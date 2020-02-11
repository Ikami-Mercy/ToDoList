package com.myToDoList.ui.activities

import android.Manifest.permission.*
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.myToDoList.R
import com.myToDoList.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {
    private val GALLERY = 1
    private val PROFILECAMERA = 2
    private var bitmap: Bitmap? = null

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


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == this.PROFILECAMERA && resultCode== Activity.RESULT_OK) {
            bitmap = data?.getExtras()!!.get("data") as Bitmap?
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, 150, 150)
            iv_profile_avatar.setImageBitmap(bitmap)
        }
        else{
            Toast.makeText(this, "Did not get a pic!", Toast.LENGTH_SHORT).show()
        }

        if(resultCode ==this.GALLERY && resultCode== Activity.RESULT_OK){

                Log.e("From gallery", data.toString())

        }

    /*    if (resultCode == this.GALLERY) {
            if(resultCode== Activity.RESULT_OK){
                val contentURI = data.getUri()
            }
            bitmap = data?.getExtras()!!.get("data") as Bitmap?
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, 150, 150)
            iv_profile_avatar.setImageBitmap(bitmap)
        }*/
    }
}
