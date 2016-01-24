package com.lovejjfg.indexdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ListView mLv;
    private TextView mTvLetter;
    private ArrayList<GoodMan> mPersons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IndexBar indexBar = (IndexBar) findViewById(R.id.index_bar);
        mLv = (ListView) findViewById(R.id.lv);
        mTvLetter = (TextView) findViewById(R.id.tv_letter);
        fillNameAndSort();
        mLv.setAdapter(new IndexAdapter(getApplicationContext(), mPersons));
        indexBar.setOnLetterChangeListener(new IndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
//                ToastUtil.showToast(getApplicationContext(), letter);
                showTextView(letter);
                for(int i=0; i<mPersons.size(); i++) {
                    String firstPinyin = String.valueOf(mPersons.get(i).getPinyin().charAt(0));
                    if(TextUtils.equals(letter, firstPinyin)) {
                        mLv.setSelection(i);
                        break;
                    }
                }
            }
        });
    }

    private Handler mHander = new Handler();
    protected void showTextView(String letter) {
        mTvLetter.setVisibility(View.VISIBLE);
        mTvLetter.setText(letter);
        mHander.removeCallbacksAndMessages(null);
        mHander.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTvLetter.setVisibility(View.GONE);
            }
        }, 500);

    }

    // 填充拼音, 排序
    private void fillNameAndSort() {
        for (int i = 0; i < Cheeses.NAMES.length; i++) {
            GoodMan person = new GoodMan(Cheeses.NAMES[i]);
            mPersons.add(person);
        }
        Collections.sort(mPersons);
    }
}
