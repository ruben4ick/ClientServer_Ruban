package org.example;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Sender {
    public byte[] send(Packet packet, byte[] key) throws Exception {
        byte[] messageBytes = packet.getBMsg().getMessage().getBytes(StandardCharsets.UTF_8);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedMessage = cipher.doFinal(messageBytes);

        ByteBuffer messageBuffer = ByteBuffer.allocate(8 + encryptedMessage.length);
        messageBuffer.putInt(packet.getBMsg().getCType());
        messageBuffer.putInt(packet.getBMsg().getBUserId());
        messageBuffer.put(encryptedMessage);
        byte[] bMsg = messageBuffer.array();

        int wLen = bMsg.length;
        CRC16 crc16 = new CRC16();
        ByteBuffer headerBuffer = ByteBuffer.allocate(14);
        headerBuffer.put(packet.getBMagic());
        headerBuffer.put(packet.getBSrc());
        headerBuffer.putLong(packet.getBPktId());
        headerBuffer.putInt(wLen);
        crc16.update(headerBuffer.array());
        short headerCrc = (short) crc16.getValue();

        crc16 = new CRC16();
        crc16.update(bMsg);
        short msgCrc = (short) crc16.getValue();

        ByteBuffer packetBuffer = ByteBuffer.allocate(16 + wLen + 2);
        packetBuffer.put(packet.getBMagic());
        packetBuffer.put(packet.getBSrc());
        packetBuffer.putLong(packet.getBPktId());
        packetBuffer.putInt(wLen);
        packetBuffer.putShort(headerCrc);
        packetBuffer.put(bMsg);
        packetBuffer.putShort(msgCrc);

        return packetBuffer.array();
    }
}