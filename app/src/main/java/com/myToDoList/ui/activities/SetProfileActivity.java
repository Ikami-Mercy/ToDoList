package com.myToDoList.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.florent37.viewtooltip.ViewTooltip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoast.StyleableToast;
import com.myToDoList.R;
import com.myToDoList.constants.Constants;
import com.myToDoList.utils.Permissions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SetProfileActivity extends AppCompatActivity {
    private CircleImageView iv_profile_avatar;
    private FloatingActionButton fab_operatorProfile_pic, fab_save;
    private int GALLERY = 1, PROFILECAMERA = 2;
    private Bitmap bitmap;
    private Boolean textChanged = false;
    private String profileName, profilePic;
    private ImageView back;
    private EditText et_profile_name;
    private SharedPreferences sharedpreferences;
    private Boolean firstrun = false;
    private Boolean hasSetProfile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        iv_profile_avatar = findViewById(R.id.iv_profile_avatar);
        fab_operatorProfile_pic = findViewById(R.id.fab_operatorProfile_pic);
        fab_save = findViewById(R.id.fab_save);
        et_profile_name = findViewById(R.id.et_profile_name);
        this.sharedpreferences = this.getSharedPreferences(Constants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        this.firstrun = sharedpreferences.getBoolean("firstrun", true);
        this.profilePic = sharedpreferences.getString("profileImage", null);
        this.profileName = sharedpreferences.getString("profileName", null);

        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            onBackPressed();
        });
        if (!firstrun) {
            et_profile_name.setText(profileName);
            iv_profile_avatar.setImageBitmap(decodeBase64(profilePic));
            back.setVisibility(View.VISIBLE);
        }
        iv_profile_avatar.setOnClickListener(v -> {

            if (!Permissions.hasPermissions(this, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)) {
                Permissions.requestStoragePermissions(SetProfileActivity.this, granted -> {
                    if (granted) choosePhotoFromGallary();
                });

            } else {
                choosePhotoFromGallary();
            }

        });

        fab_operatorProfile_pic.setOnClickListener(v -> {
            if (!Permissions.hasPermissions(this, CAMERA)) {
                Permissions.requestCameraPermissions(this, granted -> {
                    if (granted) takePhotoFromCamera();
                });

            } else {
                takePhotoFromCamera();
            }
        });
        et_profile_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                textChanged = true;

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {

                }
            }
        });

        fab_save.setOnClickListener(v -> {

            if (!hasSetProfile && firstrun) {
                ViewTooltip
                        .on(this, iv_profile_avatar)
                        .autoHide(true, 10000)
                        .corner(30)
                        .position(ViewTooltip.Position.LEFT)
                        .text("Choose a profile picture")
                        .arrowWidth(15)
                        .arrowHeight(15)
                        .color(Color.GRAY)
                        .distanceWithView(0)
                        .show();

            } else if (et_profile_name.getText().toString().isEmpty()) {
                et_profile_name.setError("Profile name Required");
            } else if (textChanged || hasSetProfile) {
                sharedpreferences = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if (firstrun) {

                    editor.putString("profileName", et_profile_name.getText().toString());
                    editor.putString("profileImage", encodeTobase64(bitmap));
                    editor.putBoolean("firstrun", false);
                    editor.commit();
                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Saved")
                            .setContentText("Your profile has been succesfully created.")
                            .show();
                } else {
                    if (textChanged) {
                        editor.putString("profileName", et_profile_name.getText().toString());
                    }

                    if (hasSetProfile) {
                        editor.putString("profileImage", encodeTobase64(bitmap));
                    }
                    editor.commit();
                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Saved")
                            .setContentText("Your profile has been succesfully updated.")
                            .show();
                }
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
                finish();
            } else
                Toast.makeText(this, "No edited changes to update!", Toast.LENGTH_SHORT).show();

        });
    }


    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);

    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PROFILECAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY) {
            if (resultCode == RESULT_OK) {
                Uri contentURI = data.getData();


                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    bitmap = ThumbnailUtils.extractThumbnail(bitmap, 150, 150);
                    iv_profile_avatar.setImageBitmap(bitmap);
                    hasSetProfile = true;


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SetProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            // }
        } else if (requestCode == PROFILECAMERA) {

            bitmap = (Bitmap) data.getExtras().get("data");
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, 150, 150);
            iv_profile_avatar.setImageBitmap(bitmap);
            hasSetProfile = true;
            //Write in my sharedprefs
            Toast.makeText(SetProfileActivity.this, "Image Captured!", Toast.LENGTH_SHORT).show();
        }
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }


    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
