package com.myapplicationdev.android.p06_taskmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.RemoteInput;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReplyActivity extends AppCompatActivity {

    ListView lvReply;
    ArrayList<Task> tasks;
    ArrayAdapter<Task> adapter;
    Button btnAdd2;
    int actReqCode = 1;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        lvReply = findViewById(R.id.lvReply);
        btnAdd2 = findViewById(R.id.btnAdd2);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        int id = prefs.getInt("id",0);






        CharSequence reply = null;
        Intent intent = getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null){
            reply = remoteInput.getCharSequence("status");
            DBHelper db = new DBHelper(this);
            Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
            db.deleteTasks(id);
            tasks = db.getAllTasks();
            adapter = new ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1,tasks);
            lvReply.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            editor.clear();


        }

        if (reply != null){
            Toast.makeText(ReplyActivity.this,"You have indicated: " + reply,Toast.LENGTH_SHORT).show();

        }

        btnAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(i, actReqCode);
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == actReqCode) {
            if (resultCode == RESULT_OK) {
                DBHelper dbh = new DBHelper(getApplicationContext());
                tasks.clear();
                tasks.addAll(dbh.getAllTasks());
                dbh.close();
                adapter.notifyDataSetChanged();
            }else{
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
