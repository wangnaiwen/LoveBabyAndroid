package com.wnw.lovebaby.util;

/**
 * Created by wnw on 2016/12/10.
 */

public class TypeConverters {
    /**
     * int convert to float, to string
     */
    public String IntConvertToString(int num){
        String str = "￥";
        int integerPart = num / 100;
        int decimalsPart = num % 100;
        if(decimalsPart < 10){
            str = str + integerPart + ".0" + decimalsPart;
        }else {
            str = str + integerPart + "." + decimalsPart;
        }

        return str;
    }
}
