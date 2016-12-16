package com.lovejjfg.indexdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lovejjfg.powerrecycle.AdapterLoader;
import com.lovejjfg.powerrecycle.PowerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRv;
    private TextView mTvLetter;
    private ArrayList<Girl> mPersons = new ArrayList<>();
    private ArrayList<String> letters = new ArrayList<>();
    private static final String TAG = RecyclerViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        IndexBar indexBar = (IndexBar) findViewById(R.id.index_bar);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mTvLetter = (TextView) findViewById(R.id.tv_letter);
        fillNameAndSort();
        IndexAdapter indexAdapter = new IndexAdapter();
        mRv.setAdapter(indexAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRv.setLayoutManager(linearLayoutManager);
        mRv.setHasFixedSize(true);
        indexAdapter.setList(mPersons);
        indexBar.setLetters(letters);
        indexBar.setOnLetterChangeListener(new IndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(int position, String letter) {
                showTextView(letter);
                if ("#".equals(letter)) {
                    mRv.scrollToPosition(0);
                    return;
                }
                for (int i = 0; i < mPersons.size(); i++) {
                    Girl girl = mPersons.get(i);
                    String pinyin = girl.getPinyin();
                    String firstPinyin = String.valueOf(TextUtils.isEmpty(pinyin) ? girl.getName().charAt(0) : pinyin.charAt(0));
                    if (letter.compareToIgnoreCase(firstPinyin) == 0) {
                        linearLayoutManager.scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
        indexAdapter.setOnItemClickListener(new AdapterLoader.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Log.e(TAG, "onItemClick: " + mPersons.get(position));
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
            if (DigitalUtil.isDigital(girl.getName())) {
                if (!letters.contains("#")) {
                    letters.add("#");
                }
                continue;
            }

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

    static class IndexAdapter extends PowerAdapter<Girl> {

        @Override
        public RecyclerView.ViewHolder onViewHolderCreate(ViewGroup parent, int viewType) {
            return new IndexHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onViewHolderBind(RecyclerView.ViewHolder holder, int position) {
            ((IndexHolder) holder).onBind(list, position);
        }
    }

    static class IndexHolder extends RecyclerView.ViewHolder {

        private final TextView mTvPinyin;
        private final TextView mTvName;

        public IndexHolder(View itemView) {
            super(itemView);
            mTvPinyin = (TextView) itemView.findViewById(R.id.tv_pinyin);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
        }

        public void onBind(List<Girl> list, int position) {
            Girl person = list.get(position);
            String pinyin = person.getPinyin();
            String name = person.getName();
            String mFirstPinyin = String.valueOf(TextUtils.isEmpty(pinyin) ? name.charAt(0) : pinyin.charAt(0));
            String mPreFirstPinyin;
            if (position == 0) {
                mPreFirstPinyin = "-";
            } else {
                Girl preGirl = list.get(position - 1);
                String prePinyin = preGirl.getPinyin();
                String preName = preGirl.getName();
                mPreFirstPinyin = String.valueOf(TextUtils.isEmpty(prePinyin) ? preName.charAt(0) : prePinyin.charAt(0));
            }
            if (DigitalUtil.isDigital(mFirstPinyin)) {
                mFirstPinyin = "#";
            }
            if (DigitalUtil.isDigital(mPreFirstPinyin)) {
                mPreFirstPinyin = "#";
            }
            mTvPinyin.setVisibility(mFirstPinyin.compareToIgnoreCase(mPreFirstPinyin) != 0 ? View.VISIBLE
                    : View.GONE);
            mTvPinyin.setText(String.valueOf(mFirstPinyin.toUpperCase()));
            mTvName.setText(name);
        }
    }


}
