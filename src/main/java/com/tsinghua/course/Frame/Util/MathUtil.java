package com.tsinghua.course.Frame.Util;

import java.util.Random;

public class MathUtil {
    public static String randomString(int length){
        String str="abcdefghijklmnopqrstuvwxyz0123456789";
        Random randomIndex=new Random();
        StringBuffer buffer=new StringBuffer();
        for(int z=0;z<length;z++){
            int index=randomIndex.nextInt(36);
            buffer.append(str.charAt(index));
        }
        return buffer.toString();
    }
}
