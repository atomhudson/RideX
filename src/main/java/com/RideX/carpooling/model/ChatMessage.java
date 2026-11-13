package com.RideX.carpooling.model;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Represents a single in-app chat message between a driver and a passenger
 * for a specific ride. Messages are scoped to a rideId + senderId + recipientId.
 */
@Entity
@Table(name = "chat_messages", indexes = {
    @Index(name = "idx_chat_ride",    columnList = "rideId"),
    @Index(name = "idx_chat_sender",  columnList = "senderId"),
    @Index(name = "idx_chat_recipient", columnList = "recipientId")
})
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The ride this conversation belongs to */
    @Column(nullable = false)
    private String rideId;

    /** userId of the sender */
    @Column(nullable = false)
    private String senderId;

    /** Display name of the sender (cached for convenience) */
    private String senderName;

    /** Profile pic URL of the sender (cached for chat bubbles) */
    private String senderAvatar;

    /** userId of the recipient */
    @Column(nullable = false)
    private String recipientId;

    /** The message content */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String messageText;

    /** Whether the recipient has read this message */
    private boolean isRead = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;

    @PrePersist
    protected void onCreate() {
        sentAt = new Date();
    }

    // ─── Getters & Setters ────────────────────────────────────────────

    public Long getId()                 { return id; }
    public void setId(Long id)          { this.id = id; }

    public String getRideId()           { return rideId; }
    public void setRideId(String r)     { this.rideId = r; }

    public String getSenderId()         { return senderId; }
    public void setSenderId(String s)   { this.senderId = s; }

    public String getSenderName()       { return senderName; }
    public void setSenderName(String s) { this.senderName = s; }

    public String getSenderAvatar()     { return senderAvatar; }
    public void setSenderAvatar(String s){ this.senderAvatar = s; }

    public String getRecipientId()      { return recipientId; }
    public void setRecipientId(String r){ this.recipientId = r; }

    public String getMessageText()      { return messageText; }
    public void setMessageText(String m){ this.messageText = m; }

    public boolean isRead()             { return isRead; }
    public void setRead(boolean r)      { this.isRead = r; }

    public Date getSentAt()             { return sentAt; }
    public void setSentAt(Date d)       { this.sentAt = d; }
}
