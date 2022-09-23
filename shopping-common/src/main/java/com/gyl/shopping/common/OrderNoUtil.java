package com.gyl.shopping.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrderNoUtil {
    public static String generateNo(){
        List<String> list = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I");
        Collections.shuffle(list);
        String orderNo = "";
        for (int i = 0; i < 2; i++) {
            orderNo += list.get(i);
        }
        orderNo += System.currentTimeMillis();
        return orderNo;
    }
}
