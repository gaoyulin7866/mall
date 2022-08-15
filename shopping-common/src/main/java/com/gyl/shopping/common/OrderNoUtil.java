package com.gyl.shopping.common;

public class OrderNoUtil {
    public static String generateNo(){
        return String.valueOf(System.currentTimeMillis());
    }
}
