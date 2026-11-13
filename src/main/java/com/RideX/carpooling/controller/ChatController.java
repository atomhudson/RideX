package com.RideX.carpooling.controller;

import com.RideX.carpooling.helpers.GetCurrentLoggedInUser;
import com.RideX.carpooling.model.ChatMessage;
import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.ChatRepository;
import com.RideX.carpooling.repositories.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for in-app driver ↔ passenger messaging.
 * All endpoints require authentication (secured via Spring Security).
 *
 * API surface:
 *  GET  /api/chat/messages/{rideId}/{otherUserId}  → fetch conversation thread
 *  POST /api/chat/send                             → send a message
 *  GET  /api/chat/unread/{rideId}                  → count of unread messages
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired private ChatRepository chatRepository;
    @Autowired private RideRepository rideRepository;
    @Autowired private GetCurrentLoggedInUser getCurrentLoggedInUser;

    // ─────────────────────────────────────────────────────────────────
    // GET  /api/chat/messages/{rideId}/{otherUserId}
    // Returns the full conversation between current user and otherUser
    // ─────────────────────────────────────────────────────────────────
    @GetMapping("/messages/{rideId}/{otherUserId}")
    public ResponseEntity<List<ChatMessage>> getMessages(
            @PathVariable String rideId,
            @PathVariable String otherUserId) {

        User me = getCurrentLoggedInUser.getCurrentUser();
        if (me == null) {
            return ResponseEntity.status(401).build();
        }

        // Mark incoming messages as read when the conversation is opened
        chatRepository.markConversationRead(rideId, me.getUserId());

        List<ChatMessage> msgs = chatRepository.findConversation(rideId, me.getUserId(), otherUserId);
        return ResponseEntity.ok(msgs);
    }

    // ─────────────────────────────────────────────────────────────────
    // POST /api/chat/send
    // Body: { rideId, recipientId, messageText }
    // ─────────────────────────────────────────────────────────────────
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> body) {
        String rideId      = body.get("rideId");
        String recipientId = body.get("recipientId");
        String text        = body.get("messageText");

        if (rideId == null || recipientId == null || text == null || text.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "rideId, recipientId, and messageText are required."));
        }

        // Verify ride exists
        Rides ride = rideRepository.findByRideId(rideId);
        if (ride == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Ride not found."));
        }

        User sender = getCurrentLoggedInUser.getCurrentUser();
        if (sender == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized. Please log in to chat."));
        }

        ChatMessage msg = new ChatMessage();
        msg.setRideId(rideId);
        msg.setSenderId(sender.getUserId());
        msg.setSenderName(sender.getfName() + " " + sender.getlName());
        msg.setSenderAvatar(sender.getProfilePic());
        msg.setRecipientId(recipientId);
        msg.setMessageText(text.trim());

        ChatMessage saved = chatRepository.save(msg);

        // Return minimal response so JS can append the bubble immediately
        return ResponseEntity.ok(Map.of(
            "id",           saved.getId(),
            "senderId",     saved.getSenderId(),
            "senderName",   saved.getSenderName(),
            "senderAvatar", saved.getSenderAvatar() != null ? saved.getSenderAvatar() : "",
            "messageText",  saved.getMessageText(),
            "sentAt",       saved.getSentAt().getTime(),
            "isRead",       false
        ));
    }

    // ─────────────────────────────────────────────────────────────────
    // GET  /api/chat/unread/{rideId}
    // Returns the number of unread messages for the current user
    // ─────────────────────────────────────────────────────────────────
    @GetMapping("/unread/{rideId}")
    public ResponseEntity<Map<String, Long>> unreadCount(@PathVariable String rideId) {
        User me = getCurrentLoggedInUser.getCurrentUser();
        if (me == null) {
            return ResponseEntity.ok(Map.of("unread", 0L));
        }
        long count = chatRepository.countUnread(rideId, me.getUserId());
        return ResponseEntity.ok(Map.of("unread", count));
    }
}
