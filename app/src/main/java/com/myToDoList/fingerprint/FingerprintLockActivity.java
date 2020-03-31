package com.myToDoList.fingerprint;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import com.myToDoList.R;
import com.myToDoList.constants.Constants;

public class FingerprintLockActivity extends AppCompatActivity {
    private ImageView back;
    private Switch switch_fingerprint;
    private boolean lockCheck;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_lock);


        back = findViewById(R.id.back);
        switch_fingerprint = findViewById(R.id.switch_fingerprint);
        sharedpreferences = this.getSharedPreferences(Constants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        lockCheck = this.sharedpreferences.getBoolean(Constants.LOCKED, false);
        switch_fingerprint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(Constants.LOCKED, true);
                    editor.apply();

                }
                if (!isChecked) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(Constants.LOCKED, false);
                    editor.apply();

                }
            }
        });

        if(lockCheck){
            switch_fingerprint.setChecked(true);
        }

        back.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}
