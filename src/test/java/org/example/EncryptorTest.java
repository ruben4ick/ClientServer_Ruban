package org.example;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptorTest {

    @Test
    public void testEncrypt() throws Exception {
        byte[] key = "1234567887654321".getBytes(StandardCharsets.UTF_8);
        Message message = new Message(1, 123, "Hello, World!");
        Encryptor encryptor = new Encryptor();
        byte[] sentData = encryptor.encrypt(message, (byte) 0x01, key);

        assertNotNull(sentData);
        assertTrue(sentData.length > 0);
    }

    @Test
    public void testEncryptWithLongMessage() throws Exception {
        byte[] key = "1234567887654321".getBytes(StandardCharsets.UTF_8);
        String longMessage = new String(new char[1000]).replace("\0", "A");
        Message message = new Message(1, 123, longMessage);
        Encryptor encryptor = new Encryptor();
        byte[] sentData = encryptor.encrypt(message, (byte) 0x01, key);

        assertNotNull(sentData);
        assertTrue(sentData.length > 0);
    }

    @Test
    public void testEncryptWithEmptyMessage() throws Exception {
        byte[] key = "1234567887654321".getBytes(StandardCharsets.UTF_8);
        Message message = new Message(1, 123, "");
        Encryptor encryptor = new Encryptor();
        byte[] sentData = encryptor.encrypt(message, (byte) 0x01, key);

        assertNotNull(sentData);
        assertTrue(sentData.length > 0);
    }
}
