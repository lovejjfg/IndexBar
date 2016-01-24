package com.lovejjfg.indexdemo;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class IndexAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<GoodMan> mPersons;
    private Context mContext;
    private String mFirstPinyin;
    private String mPreFirstPinyin;

    public IndexAdapter(Context context, ArrayList<GoodMan> persons) {
        mContext = context;
        mPersons = persons;
    }

    @Override
    public int getCount() {
        return mPersons.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item, null);
            holder = new ViewHolder();
            holder.mTvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mTvPinyin = (TextView) convertView.findViewById(R.id.tv_pinyin);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GoodMan person = mPersons.get(position);
        mFirstPinyin = String.valueOf(person.getPinyin().charAt(0));
        if(position == 0) {
            mPreFirstPinyin = "-";
        }else {
            mPreFirstPinyin = String.valueOf(mPersons.get(position - 1).getPinyin().charAt(0));
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
