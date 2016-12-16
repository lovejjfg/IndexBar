package com.lovejjfg.indexdemo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Joe on 2016/12/16.
 * Email lovejjfg@gmail.com
 */

public class DigitalUtil {

    static Pattern p = Pattern.compile("[0-9]*");

    public static boolean isDigital(String str) {
        return p.matcher(str).matches();
    }
}
