package com.back.backend.utils;

public class Random {
    public static int getRandomValue(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}