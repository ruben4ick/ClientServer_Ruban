package org.example;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {

            Message originalMessage = new Message(1, 301, "Hello, World!");
            byte[] key = "1234567887654321".getBytes(StandardCharsets.UTF_8); // 16 bytes key
            byte bSrc = 0x01;

            Encryptor encryptor = new Encryptor();
            byte[] packetBytes = encryptor.encrypt(originalMessage, bSrc, key);

            System.out.println("Packet bytes sent: " + Arrays.toString(packetBytes));

            Decryptor decryptor = new Decryptor();
            Message receivedMessage = decryptor.decrypt(packetBytes, key);

            System.out.println("\nReceived:");
            System.out.println("cType: " + receivedMessage.getCType());
            System.out.println("bUserId: " + receivedMessage.getBUserId());
            System.out.println("message: " + receivedMessage.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
