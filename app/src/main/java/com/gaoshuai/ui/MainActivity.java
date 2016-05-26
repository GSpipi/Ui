package com.gaoshuai.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mBackground;
    private Button mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
    }

    private void findView() {
        mBackground = ((Button) findViewById(R.id.background));
        mBaiduMap = ((Button) findViewById(R.id.map));
    }

    public void onBackground(View view) {
        startActivity(new Intent().setClass(MainActivity.this, Background.class));
    }

    public void onMap(View view) {

    }

    public void onListviewFenzu(View view) {
        startActivity(new Intent().setClass(MainActivity.this, ListveiwFenzu.class));
    }
}
