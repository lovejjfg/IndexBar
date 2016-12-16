package com.lovejjfg.indexdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_lv).setOnClickListener(this);
        findViewById(R.id.bt_rv).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bt_lv:
                intent.setClass(this, ListViewActivity.class);
                break;
            default:
            case R.id.bt_rv:
                intent.setClass(this, RecyclerViewActivity.class);
                break;
        }
        startActivity(intent);

    }
}
