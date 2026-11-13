package com.RideX.carpooling.repositories;

import com.RideX.carpooling.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * Fetch the full conversation thread between two users for a given ride.
     * Returns messages in both directions (A→B and B→A), oldest first.
     */
    @Query("SELECT m FROM ChatMessage m " +
           "WHERE m.rideId = :rideId " +
           "  AND ((m.senderId = :userA AND m.recipientId = :userB) " +
           "    OR (m.senderId = :userB AND m.recipientId = :userA)) " +
           "ORDER BY m.sentAt ASC")
    List<ChatMessage> findConversation(
            @Param("rideId")  String rideId,
            @Param("userA")   String userA,
            @Param("userB")   String userB);

    /**
     * Count unread messages sent TO a specific user for a specific ride.
     * Used to show unread badge on ride cards.
     */
    @Query("SELECT COUNT(m) FROM ChatMessage m " +
           "WHERE m.rideId = :rideId AND m.recipientId = :userId AND m.isRead = false")
    long countUnread(@Param("rideId") String rideId, @Param("userId") String userId);

    /**
     * Mark all messages in a conversation as read (called when recipient opens chat).
     */
    @Modifying
    @Transactional
    @Query("UPDATE ChatMessage m SET m.isRead = true " +
           "WHERE m.rideId = :rideId AND m.recipientId = :userId AND m.isRead = false")
    void markConversationRead(@Param("rideId") String rideId, @Param("userId") String userId);
}
