package com.lovejjfg.indexdemo;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class IndexAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Girl> mGirls;

    public IndexAdapter(ArrayList<Girl> girls) {
        this.mGirls = girls;
    }

    @Override
    public int getCount() {
        return mGirls.size();
    }

    @Override
    public Object getItem(int position) {
        return mGirls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            holder = new ViewHolder();
            holder.mTvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mTvPinyin = (TextView) convertView.findViewById(R.id.tv_pinyin);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Girl person = mGirls.get(position);
        String pinyin = person.getPinyin();
        String name = person.getName();
        String mFirstPinyin = String.valueOf(TextUtils.isEmpty(pinyin) ? name.charAt(0) : pinyin.charAt(0));
        String mPreFirstPinyin;
        if (position == 0) {
            mPreFirstPinyin = "-";
        } else {
            Girl preGirl = mGirls.get(position - 1);
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
        holder.mTvPinyin.setVisibility(mFirstPinyin.compareToIgnoreCase(mPreFirstPinyin) != 0 ? View.VISIBLE
                : View.GONE);
        holder.mTvPinyin.setText(String.valueOf(mFirstPinyin.toUpperCase()));
        holder.mTvName.setText(name);
        return convertView;
    }

    static class ViewHolder {
        TextView mTvName;
        TextView mTvPinyin;
    }

}
