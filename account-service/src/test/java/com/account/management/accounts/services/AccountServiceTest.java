package com.account.management.accounts.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

public class AccountServiceTest {

    @DisplayName("Testing uniqueness")
    @Test
    void testUniqueness() {
        for (int i = 0; i < 5; i++) {
            //System.out.println(shortUUID());
            System.out.println(randomString(13));
        }

    }

    static String randomString(int len) {
        //String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String AB = "0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public String shortUUID() {
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }

}
