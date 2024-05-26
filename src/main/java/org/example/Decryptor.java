    package org.example;

    import javax.crypto.Cipher;
    import javax.crypto.spec.SecretKeySpec;
    import java.nio.ByteBuffer;
    import java.nio.charset.StandardCharsets;
    import java.util.Arrays;

    public class Decryptor {
        private static final byte MAGIC_BYTE = 0x13;

        public Message decrypt(byte[] packetData, byte[] key) throws Exception {
            ByteBuffer buffer = ByteBuffer.wrap(packetData);

            byte bMagic = buffer.get();
            if (bMagic != MAGIC_BYTE) {
                throw new IllegalArgumentException("Invalid magic byte");
            }

            byte bSrc = buffer.get();
            long bPktId = buffer.getLong();
            int wLen = buffer.getInt();
            short headerCrc = buffer.getShort();

            byte[] header = Arrays.copyOfRange(packetData, 0, 14);
            CRC16 crc16 = new CRC16();
            crc16.update(header);
            if ((short)crc16.getValue() != headerCrc) {
                throw new IllegalArgumentException("Invalid header CRC");
            }

            byte[] bMsgBytes = Arrays.copyOfRange(packetData, 16, 16 + wLen);
            short msgCrc = buffer.getShort(16 + wLen);

            crc16 = new CRC16();
            crc16.update(bMsgBytes);
            if ((short)crc16.getValue() != msgCrc) {
                throw new IllegalArgumentException("Invalid message CRC");
            }

            ByteBuffer messageBuffer = ByteBuffer.wrap(bMsgBytes);
            int cType = messageBuffer.getInt();
            int bUserId = messageBuffer.getInt();
            byte[] encryptedMessage = new byte[messageBuffer.remaining()];
            messageBuffer.get(encryptedMessage);

            //Message decryption
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedMessage = cipher.doFinal(encryptedMessage);
            String message = new String(decryptedMessage, StandardCharsets.UTF_8);


            return new Message(cType, bUserId, message);
        }
    }