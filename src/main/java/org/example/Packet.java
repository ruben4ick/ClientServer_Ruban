package org.example;

public class Packet {
    private static long lastPktId = 0;

    private byte bMagic;
    private byte bSrc;
    private long bPktId;
    private int wLen;
    private short headerCrc;
    private Message bMsg;
    private short msgCrc;

    public Packet(byte bMagic, byte bSrc, int wLen, short headerCrc, Message bMsg, short msgCrc) {
        this.bMagic = bMagic;
        this.bSrc = bSrc;
        this.bPktId = lastPktId;
        this.wLen = wLen;
        this.headerCrc = headerCrc;
        this.bMsg = bMsg;
        this.msgCrc = msgCrc;
    }

    public Packet(byte bMagic, byte bSrc, Message bMsg) {
        this.bMagic = bMagic;
        this.bSrc = bSrc;
        this.bPktId = ++lastPktId;
        this.bMsg = bMsg;
    }

    public byte getBMagic() {
        return bMagic;
    }

    public void setBMagic(byte bMagic) {
        this.bMagic = bMagic;
    }

    public byte getBSrc() {
        return bSrc;
    }

    public void setBSrc(byte bSrc) {
        this.bSrc = bSrc;
    }

    public long getBPktId() {
        return bPktId;
    }

    public void setBPktId(long bPktId) {
        this.bPktId = bPktId;
    }

    public int getWLen() {
        return wLen;
    }

    public void setWLen(int wLen) {
        this.wLen = wLen;
    }

    public int getHeaderCrc() {
        return headerCrc;
    }

    public void setHeaderCrc(short headerCrc) {
        this.headerCrc = headerCrc;
    }

    public Message getBMsg() {
        return bMsg;
    }

    public void setBMsg(Message bMsg) {
        this.bMsg = bMsg;
    }

    public int getMsgCrc() {
        return msgCrc;
    }

    public void setMsgCrc(short msgCrc) {
        this.msgCrc = msgCrc;
    }
}
