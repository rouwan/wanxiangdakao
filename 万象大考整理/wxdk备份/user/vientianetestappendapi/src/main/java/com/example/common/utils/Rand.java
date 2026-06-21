package com.example.common.utils;

import java.util.Random;

public class Rand {
    private static String alls = "abcdefghijklmnokprstuvwxyzABCDEFGHIJKLMNOKQRSTUVWXYZ0123456789";
    private static String lowers = "abcdefghijklmnokprstuvwxyz0123456789";
    private static String chars = "abcdefghijklmnokprstuvwxyz";
    private static String numbers = "0123456789";
    private static int BIG_NUMBER = 10000;

    public static String randNumberStr(final int len) {
        return generate(numbers, len);
    }

    public static String randChars(final int len) {
        return generate(chars, len);
    }

    public static String randStr(final int len) {
        return generate(lowers, len);
    }

    public static String randAllStr(final int len) {
        return generate(alls, len);
    }

    public static int nextInt(final int bound) {
        Random random = new Random();
        int number = -1;
        while (number < BIG_NUMBER) {
            number = random.nextInt(BIG_NUMBER + bound);
        }
        return number - BIG_NUMBER;
    }

    private static String generate(final String source, final int len) {
        int length = source.length();
        StringBuilder sb = new StringBuilder();
        Random rand = new Random(System.nanoTime());
        for (int i = 0; i < len; i++) {
            sb.append(source.charAt(rand.nextInt(length)));
        }

        return sb.toString();
    }
}
