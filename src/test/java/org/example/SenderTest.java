package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SenderTest {

    @Test
    public void testSend() throws Exception {
        byte[] key = "1234567887654321".getBytes();
        Message message = new Message(1, 123, "Hello, World!");
        Packet packet = new Packet((byte) 0x13, (byte) 0x01, message);
        Sender sender = new Sender();
        byte[] sentData = sender.send(packet, key);

        assertNotNull(sentData);
        assertTrue(sentData.length > 0);
    }

    @Test
    public void testSendWithLongMessage() throws Exception {
        byte[] key = "1234567887654321".getBytes();
        String longMessage = new String(new char[1000]).replace("\0", "A");
        Message message = new Message(1, 123, longMessage);
        Packet packet = new Packet((byte) 0x13, (byte) 0x01, message);
        Sender sender = new Sender();
        byte[] sentData = sender.send(packet, key);

        assertNotNull(sentData);
        assertTrue(sentData.length > 0);
    }

    @Test
    public void testSendWithEmptyMessage() throws Exception {
        byte[] key = "1234567887654321".getBytes();
        Message message = new Message(1, 123, "");
        Packet packet = new Packet((byte) 0x13, (byte) 0x01, message);
        Sender sender = new Sender();
        byte[] sentData = sender.send(packet, key);

        assertNotNull(sentData);
        assertTrue(sentData.length > 0);
    }
}
