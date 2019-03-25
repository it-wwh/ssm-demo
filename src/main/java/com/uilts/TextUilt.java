package com.uilts;

public class TextUilt {
    public static boolean isEmptys(String str) {
        if (str == null) {
            return true;
        } else {
            if (str.trim().length() == 0) {
                return true;
            } else {
                return false;
            }
        }
    }
}
