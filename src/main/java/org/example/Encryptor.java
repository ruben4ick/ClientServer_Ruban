package org.example;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Encryptor {
    private static long currentPktId = 0;

    public byte[] encrypt(Message message, byte bSrc, byte[] key) throws Exception {
        byte bMagic = 0x13;
        long bPktId = getNextPktId();

        byte[] messageBytes = message.getMessage().getBytes(StandardCharsets.UTF_8);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedMessage = cipher.doFinal(messageBytes);

        ByteBuffer messageBuffer = ByteBuffer.allocate(8 + encryptedMessage.length);
        messageBuffer.putInt(message.getCType());
        messageBuffer.putInt(message.getBUserId());
        messageBuffer.put(encryptedMessage);
        byte[] bMsg = messageBuffer.array();

        int wLen = bMsg.length;
        CRC16 crc16 = new CRC16();
        ByteBuffer headerBuffer = ByteBuffer.allocate(14);
        headerBuffer.put(bMagic);
        headerBuffer.put(bSrc);
        headerBuffer.putLong(bPktId);
        headerBuffer.putInt(wLen);
        crc16.update(headerBuffer.array());
        short headerCrc = (short) crc16.getValue();

        crc16 = new CRC16();
        crc16.update(bMsg);
        short msgCrc = (short) crc16.getValue();

        Packet packet = new Packet(bMagic, bSrc, bPktId, wLen, headerCrc, bMsg, msgCrc);

        return PacketToByte(packet);
    }

    private synchronized long getNextPktId() {
        return ++currentPktId;
    }
    private byte[] PacketToByte(Packet packet) {
        ByteBuffer packetBuffer = ByteBuffer.allocate(16 + packet.getWLen() + 2);
        packetBuffer.put(packet.getBMagic());
        packetBuffer.put(packet.getBSrc());
        packetBuffer.putLong(packet.getBPktId());
        packetBuffer.putInt(packet.getWLen());
        packetBuffer.putShort(packet.getHeaderCrc());
        packetBuffer.put(packet.getBMsg());
        packetBuffer.putShort(packet.getMsgCrc());
        return packetBuffer.array();
    }
}
