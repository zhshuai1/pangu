package com.sepism.pangu.util;

public final class DataNormalizer {
    public static String normalizePhone(String phone) {
        return phone.replaceAll("[^0-9]", "");
    }
}
