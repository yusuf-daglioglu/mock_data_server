package com.demo.mockservice.util;

import org.jeasy.random.EasyRandom;

public class RandomDataGenerator {

    private static final EasyRandom easyRandom = new EasyRandom();

    public static <T> T generateRandomData(Class<T> clazz) {
        return easyRandom.nextObject(clazz);
    }
}
