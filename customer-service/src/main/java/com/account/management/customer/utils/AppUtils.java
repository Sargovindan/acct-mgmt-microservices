package com.account.management.customer.utils;

import java.util.Random;

public class AppUtils {

    private static final int ACCT_NUM_LENGTH = 13;

    private AppUtils(){}


    public static String generateAccountNumber() {
        return randomString(ACCT_NUM_LENGTH);
    }

    public static String generateCustomerNumber() {
        return randomString(ACCT_NUM_LENGTH);
    }

    private static String randomString(int len) {
        String AB = "0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
