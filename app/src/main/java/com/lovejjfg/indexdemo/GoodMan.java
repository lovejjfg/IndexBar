package com.lovejjfg.indexdemo;

public class GoodMan implements Comparable<GoodMan> {

    private String mName;
    private String mPinyin;
    
    public GoodMan(String name) {
        mName = name;
        mPinyin = PinYinUtil.toPinyin(mName);
    }
    
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }
    public String getPinyin() {
        return mPinyin;
    }
    public void setPinyin(String pinyin) {
        mPinyin = pinyin;
    }

    @Override
    public int compareTo(GoodMan another) {
        return mPinyin.compareTo(another.getPinyin());
    }
}
