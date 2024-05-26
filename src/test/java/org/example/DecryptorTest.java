package org.example;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class DecryptorTest {

    @Test
    public void testDecrypt() throws Exception {
        byte[] key = "1234567887654321".getBytes(StandardCharsets.UTF_8);
        Message message = new Message(1, 123, "Hello, World!");
        Encryptor encryptor = new Encryptor();
        byte[] sentData = encryptor.encrypt(message, (byte) 0x01, key);
        Decryptor decryptor = new Decryptor();
        Message receivedMessage = decryptor.decrypt(sentData, key);

        assertNotNull(receivedMessage);
        assertEquals(message.getCType(), receivedMessage.getCType());
        assertEquals(message.getBUserId(), receivedMessage.getBUserId());
        assertEquals(message.getMessage(), receivedMessage.getMessage());
    }

    @Test
    public void testDecryptWithWrongKey() throws Exception {
        byte[] key = "1234567887654321".getBytes(StandardCharsets.UTF_8);
        byte[] wrongKey = "6543210987654321".getBytes(StandardCharsets.UTF_8);
        Message message = new Message(1, 123, "Hello, World!");
        Encryptor encryptor = new Encryptor();
        byte[] sentData = encryptor.encrypt(message, (byte) 0x01, key);
        Decryptor decryptor = new Decryptor();

        assertThrows(Exception.class, () -> {
            decryptor.decrypt(sentData, wrongKey);
        });
    }

    @Test
    public void testDecryptWithCorruptedData() throws Exception {
        byte[] key = "1234567887654321".getBytes(StandardCharsets.UTF_8);
        Message message = new Message(1, 123, "Hello, World!");
        Encryptor encryptor = new Encryptor();
        byte[] sentData = encryptor.encrypt(message, (byte) 0x01, key);

        sentData[20] = (byte) (sentData[20] ^ 0xFF);

        Decryptor decryptor = new Decryptor();
        assertThrows(Exception.class, () -> {
            decryptor.decrypt(sentData, key);
        });
    }

    @Test
    public void testDecryptWithEmptyMessage() throws Exception {
        byte[] key = "1234567887654321".getBytes(StandardCharsets.UTF_8);
        Message message = new Message(1, 123, "");
        Encryptor encryptor = new Encryptor();
        byte[] sentData = encryptor.encrypt(message, (byte) 0x01, key);
        Decryptor decryptor = new Decryptor();
        Message receivedMessage = decryptor.decrypt(sentData, key);

        assertNotNull(receivedMessage);
        assertEquals(message.getCType(), receivedMessage.getCType());
        assertEquals(message.getBUserId(), receivedMessage.getBUserId());
        assertEquals(message.getMessage(), receivedMessage.getMessage());
    }
}
