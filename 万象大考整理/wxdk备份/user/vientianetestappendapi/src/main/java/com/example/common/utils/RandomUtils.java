package com.example.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

public class RandomUtils {
    //随机生成汉字
    public static char getRandomChar() {
        String str = "";
        int hightPos; //
        int lowPos;

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("错误");
        }

        return str.charAt(0);
    }

    public static String randomStr(int len){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<len;i++){
            sb.append(getRandomChar());
        }
        return sb.toString();
    }
    // 随机生成21位答案
    public static String RandomAnswer(String answer) {
        String randAnwser = "";
        ArrayList<Integer> list = new ArrayList<Integer>();
        Random random = new Random();
        for (int i = 0; i < answer.length(); i++) {
            int number = random.nextInt(15);
            while(list.contains(number)) {
                number = random.nextInt(15);
            }
            list.add(number);
        }

        for (int i = 0; i < 15; i++) {
            if (list.contains(i)) {
                int index = list.indexOf(i);
                randAnwser += answer.substring(index, index + 1) + ",";
            } else {
                char randChar = RandomUtils.getRandomChar();
                while(randAnwser.indexOf(randChar)>=0) {
                    randChar =  RandomUtils.getRandomChar();
                }
                randAnwser += randChar + ",";
            }
        }

        if(randAnwser.endsWith(",")){
            return randAnwser.substring(0,randAnwser.length()-1);
        }

        return randAnwser;
    }
}
