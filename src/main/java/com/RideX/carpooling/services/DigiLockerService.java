package com.RideX.carpooling.services;

import com.RideX.carpooling.dto.DigiLockerVerificationDto;
import com.RideX.carpooling.model.User;

import java.util.Map;

/**
 * Service for automated Government KYC via DigiLocker / MeriPehchaan.
 */
public interface DigiLockerService {

    /**
     * Builds the initiate/consent URL (either MeriPehchaan live or RideX Sandbox Portal).
     */
    String getInitiateAuthUrl(String state);

    /**
     * Generates simulated verified document details for Sandbox testing.
     */
    DigiLockerVerificationDto getSimulatedDocument(String docType, User user);

    /**
     * Completes automated KYC verification: updates user flags, adds bonus coins,
     * syncs GovtIDVerifyRequest, evicts profile cache, and sends email confirmation.
     */
    boolean verifyAndApproveUser(User user, DigiLockerVerificationDto docDto);

    /**
     * Returns true if running in Sandbox / Simulator mode.
     */
    boolean isSandboxEnabled();

    /**
     * Trust coins bonus awarded on successful KYC.
     */
    int getBonusTrustCoins();

    /**
     * Returns public gateway configuration for client UI.
     */
    Map<String, Object> getConfig();
}
