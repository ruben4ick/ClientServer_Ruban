package org.example;

public class Message {
    private int cType;
    private int bUserId;
    private String message;

    public Message(int cType, int bUserId, String message) {
        this.cType = cType;
        this.bUserId = bUserId;
        this.message = message;
    }

    public int getCType() { return cType; }
    public void setCType(int cType) { this.cType = cType; }

    public int getBUserId() { return bUserId; }
    public void setBUserId(int bUserId) { this.bUserId = bUserId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}