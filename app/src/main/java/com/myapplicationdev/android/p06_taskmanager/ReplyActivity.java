package com.myapplicationdev.android.p06_taskmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.RemoteInput;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class ReplyActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        Intent newIntent = getIntent();
        int id = newIntent.getIntExtra("id", -1);

        CharSequence reply = null;
        Intent intent = getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null){
            reply = remoteInput.getCharSequence("status");
            
        }
        if (reply != null){
            Toast.makeText(ReplyActivity.this,"You have indicated: " + reply,Toast.LENGTH_SHORT).show();
        }

    }
}
