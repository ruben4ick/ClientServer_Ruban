package org.example;

public class Packet {
    private byte bMagic;
    private byte bSrc;
    private long bPktId;
    private int wLen;
    private short headerCrc;
    private byte[] bMsg;
    private short msgCrc;

    public Packet(byte bMagic, byte bSrc,long bPktId, int wLen, short headerCrc, byte[] bMsg, short msgCrc) {
        this.bMagic = bMagic;
        this.bSrc = bSrc;
        this.bPktId = bPktId;
        this.wLen = wLen;
        this.headerCrc = headerCrc;
        this.bMsg = bMsg;
        this.msgCrc = msgCrc;
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

    public short getHeaderCrc() {
        return headerCrc;
    }

    public void setHeaderCrc(short headerCrc) {
        this.headerCrc = headerCrc;
    }

    public byte[] getBMsg() {
        return bMsg;
    }

    public void setBMsg(byte[] bMsg) {
        this.bMsg = bMsg;
    }

    public short getMsgCrc() {
        return msgCrc;
    }

    public void setMsgCrc(short msgCrc) {
        this.msgCrc = msgCrc;
    }
}
