package org.example;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {

            Message originalMessage = new Message(1, 301, "Hello, World!");
            byte[] key = "1234567887654321".getBytes(StandardCharsets.UTF_8); // 16 bytes key


            byte bMagic = 0x13;
            byte bSrc = 0x01;
            Packet packet = new Packet(bMagic, bSrc, originalMessage);

            Sender sender = new Sender();
            byte[] packetBytes = sender.send(packet, key);

            System.out.println("Packet bytes sent: " + Arrays.toString(packetBytes));

            Receiver receiver = new Receiver();
            Packet receivedPacket = receiver.receive(packetBytes, key);


            System.out.println("\nReceived:");
            System.out.println("bMagic: " + String.format("%02X", receivedPacket.getBMagic()));
            System.out.println("bSrc: " + String.format("%02X", receivedPacket.getBSrc()));
            System.out.println("bPktId: " + receivedPacket.getBPktId());
            System.out.println("wLen: " + receivedPacket.getWLen());
            System.out.println("cType: " + receivedPacket.getBMsg().getCType());
            System.out.println("bUserId: " + receivedPacket.getBMsg().getBUserId());
            System.out.println("message: " + receivedPacket.getBMsg().getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
