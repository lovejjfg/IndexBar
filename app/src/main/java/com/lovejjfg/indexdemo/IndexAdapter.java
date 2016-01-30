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
        String mFirstPinyin = String.valueOf(person.getPinyin().charAt(0));
        String mPreFirstPinyin;
        if(position == 0) {
            mPreFirstPinyin = "-";
        }else {
            mPreFirstPinyin = String.valueOf(mGirls.get(position - 1).getPinyin().charAt(0));
        }
        holder.mTvPinyin.setVisibility(!TextUtils.equals(mFirstPinyin, mPreFirstPinyin) ? View.VISIBLE
                        : View.GONE);
        holder.mTvPinyin.setText(String.valueOf(person.getPinyin().charAt(0)));
        holder.mTvName.setText(person.getName());
        return convertView;
    }

    static class ViewHolder {
        TextView mTvName;
        TextView mTvPinyin;
    }

}
