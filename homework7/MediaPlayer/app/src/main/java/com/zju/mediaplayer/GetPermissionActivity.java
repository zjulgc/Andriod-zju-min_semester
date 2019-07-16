package com.zju.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GetPermissionActivity extends AppCompatActivity {

    private String[] mPermissionsArrays = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_permission);

        Button btn_get = findViewById(R.id.btn_getperm);
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    for(String permission:mPermissionsArrays){
                        if(checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                        {
                            requestPermissions(mPermissionsArrays, 123);
                            Toast.makeText(GetPermissionActivity.this, "You have all permissions!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    Toast.makeText(GetPermissionActivity.this, "You have all permissions!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
