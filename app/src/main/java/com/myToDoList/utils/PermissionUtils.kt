package com.myToDoList.utils

import android.app.AlertDialog
import android.webkit.PermissionRequest
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import com.karumi.dexter.Dexter
import com.karumi.dexter.Dexter.*
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

object PermissionUtils {

    public interface PermissionsListener {
        fun onPermissionRequest(granted: Boolean)
    }

    fun hasPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) === PackageManager.PERMISSION_GRANTED
    }

    fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) !== PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    private fun showPermissionsDeniedDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("GOTO SETTINGS",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()

                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }


    fun requestStoragePermissions(activity: Activity, listener: PermissionsListener) {

        withActivity(activity)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                /**
                 * Method called whenever Android asks the application to inform the user of the need for the
                 * requested permissions. The request process won't continue until the token is properly used
                 *
                 * @param permissions The permissions that has been requested. Collections of values found in
                 * [android.Manifest.permission]
                 * @param token Token used to continue or cancel the permission request process. The permission
                 * request process will remain blocked until one of the token methods is called
                 */
                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (!report.areAllPermissionsGranted()) {

                        showPermissionsDeniedDialog(
                            activity,
                            "Storage Permissions",
                            "Storage permissions are needed to read and write files"
                        )

                    } else
                        listener.onPermissionRequest(true)
                }

            }).check()
    }

    fun requestCameraPermissions(activity: Activity, listener: PermissionsListener) {

        Dexter.withActivity(activity)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                /**
                 * Method called whenever Android asks the application to inform the user of the need for the
                 * requested permission. The request process won't continue until the token is properly used
                 *
                 * @param permission The permission that has been requested
                 * @param token Token used to continue or cancel the permission request process. The permission
                 * request process will remain blocked until one of the token methods is called
                 */
                override fun onPermissionRationaleShouldBeShown(
                    permission: com.karumi.dexter.listener.PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                 listener.onPermissionRequest(true)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    listener.onPermissionRequest(false)

                    showPermissionsDeniedDialog(
                        activity,
                        "Camera Permission",
                        "Camera permission is needed to take pictures and videos"
                    )
                }

            }).check()
    }


}
