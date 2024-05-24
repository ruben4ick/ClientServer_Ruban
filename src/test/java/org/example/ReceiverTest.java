package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiverTest {

    @Test
    public void testReceive() throws Exception {
        byte[] key = "1234567887654321".getBytes();
        Message message = new Message(1, 123, "Hello, World!");
        Packet packet = new Packet((byte) 0x13, (byte) 0x01, message);
        Sender sender = new Sender();
        byte[] sentData = sender.send(packet, key);
        Receiver receiver = new Receiver();
        Packet receivedPacket = receiver.receive(sentData, key);

        assertNotNull(receivedPacket);
        assertEquals(packet.getBMagic(), receivedPacket.getBMagic());
        assertEquals(packet.getBSrc(), receivedPacket.getBSrc());
        assertEquals(packet.getBPktId(), receivedPacket.getBPktId());
        assertEquals(packet.getBMsg().getCType(), receivedPacket.getBMsg().getCType());
        assertEquals(packet.getBMsg().getBUserId(), receivedPacket.getBMsg().getBUserId());
        assertEquals(packet.getBMsg().getMessage(), receivedPacket.getBMsg().getMessage());
    }

    @Test
    public void testReceiveWithWrongKey() throws Exception {
        byte[] key = "1234567887654321".getBytes();
        byte[] wrongKey = "6543210987654321".getBytes();
        Message message = new Message(1, 123, "Hello, World!");
        Packet packet = new Packet((byte) 0x13, (byte) 0x01, message);
        Sender sender = new Sender();
        byte[] sentData = sender.send(packet, key);
        Receiver receiver = new Receiver();

        assertThrows(Exception.class, () -> {
            receiver.receive(sentData, wrongKey);
        });
    }

    @Test
    public void testReceiveWithCorruptedData() throws Exception {
        byte[] key = "1234567887654321".getBytes();
        Message message = new Message(1, 123, "Hello, World!");
        Packet packet = new Packet((byte) 0x13, (byte) 0x01, message);
        Sender sender = new Sender();
        byte[] sentData = sender.send(packet, key);

        sentData[20] = (byte) (sentData[20] ^ 0xFF);

        Receiver receiver = new Receiver();
        assertThrows(Exception.class, () -> {
            receiver.receive(sentData, key);
        });
    }

    @Test
    public void testReceiveWithEmptyMessage() throws Exception {
        byte[] key = "1234567887654321".getBytes();
        Message message = new Message(1, 123, "");
        Packet packet = new Packet((byte) 0x13, (byte) 0x01, message);
        Sender sender = new Sender();
        byte[] sentData = sender.send(packet, key);
        Receiver receiver = new Receiver();
        Packet receivedPacket = receiver.receive(sentData, key);

        assertNotNull(receivedPacket);
        assertEquals(packet.getBMagic(), receivedPacket.getBMagic());
        assertEquals(packet.getBSrc(), receivedPacket.getBSrc());
        assertEquals(packet.getBPktId(), receivedPacket.getBPktId());
        assertEquals(packet.getBMsg().getCType(), receivedPacket.getBMsg().getCType());
        assertEquals(packet.getBMsg().getBUserId(), receivedPacket.getBMsg().getBUserId());
        assertEquals(packet.getBMsg().getMessage(), receivedPacket.getBMsg().getMessage());
    }
}