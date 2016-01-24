package com.lovejjfg.indexdemo;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {
    private static HanyuPinyinOutputFormat format;
    public static String toPinyin(String s) {
        if(format == null) {
            format = new HanyuPinyinOutputFormat();
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        }
        char c = 0;
        StringBuilder sb = new StringBuilder();
        try {
            char[] charArray = s.toCharArray();
            for(int i=0; i<charArray.length; i++) {
                c = charArray[i];
                String[] pinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if(pinyinStringArray!= null && pinyinStringArray.length>0) {
                    sb.append(pinyinStringArray[0]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
