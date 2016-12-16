package com.lovejjfg.indexdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ListView mLv;
    private TextView mTvLetter;
    private ArrayList<Girl> mPersons = new ArrayList<>();
    private ArrayList<String> letters = new ArrayList<>();
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IndexBar indexBar = (IndexBar) findViewById(R.id.index_bar);
        mLv = (ListView) findViewById(R.id.lv);
        mTvLetter = (TextView) findViewById(R.id.tv_letter);
        fillNameAndSort();
        mLv.setAdapter(new IndexAdapter(mPersons));
        indexBar.setLetters(letters);
        indexBar.setOnLetterChangeListener(new IndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(int position, String letter) {
                showTextView(letter);
                for (int i = 0; i < mPersons.size(); i++) {
                    Girl girl = mPersons.get(i);
                    String pinyin = girl.getPinyin();
                    String firstPinyin = String.valueOf(TextUtils.isEmpty(pinyin) ? girl.getName().charAt(0) : pinyin.charAt(0));
                    if (letter.compareToIgnoreCase(firstPinyin) == 0) {
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
                mTvLetter.setVisibility(View.INVISIBLE);
            }
        }, 600);

    }

    // 填充拼音, 排序
    private void fillNameAndSort() {
        for (int i = 0; i < Girls.NAMES.length; i++) {
            Girl girl = new Girl(Girls.NAMES[i]);
            mPersons.add(girl);
            String pinyin = girl.getPinyin();
            String letter;
            if (!TextUtils.isEmpty(pinyin)) {
                letter = pinyin.substring(0, 1).toUpperCase();

            } else {
                letter = girl.getName().substring(0, 1).toUpperCase();
            }
            if (!letters.contains(letter)) {
                letters.add(letter);
            }
        }
        Collections.sort(mPersons);
        Collections.sort(letters);
    }
}
