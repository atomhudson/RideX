package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.dto.DigiLockerVerificationDto;
import com.RideX.carpooling.model.GovtIDVerifyRequest;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.GovernmentIDVerifyRequestRepository;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.CustomUUIDService;
import com.RideX.carpooling.services.DigiLockerService;
import com.RideX.carpooling.services.EmailServices;
import com.RideX.carpooling.services.ProfileCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class DigiLockerServiceImplementation implements DigiLockerService {

    private static final Logger logger = LoggerFactory.getLogger(DigiLockerServiceImplementation.class);
    private static final int BONUS_TRUST_COINS = 50;

    @Value("${digilocker.client.id:RideXDigiLockerClientId}")
    private String clientId;

    @Value("${digilocker.client.secret:RideXDigiLockerSecretKey}")
    private String clientSecret;

    @Value("${digilocker.redirect.uri:http://localhost:8080/user/digilocker/callback}")
    private String redirectUri;

    @Value("${digilocker.sandbox.enabled:true}")
    private boolean sandboxEnabled;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GovernmentIDVerifyRequestRepository govtIdVerifyRequestRepository;

    @Autowired
    private ProfileCacheService profileCacheService;

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private CustomUUIDService customUUIDService;

    @Override
    public String getInitiateAuthUrl(String state) {
        if (sandboxEnabled || clientId.contains("RideXDigiLocker")) {
            return "/user/digilocker/portal?state=" + (state != null ? state : "sandbox_init");
        }
        // MeriPehchaan / DigiLocker Live OAuth 2.0 URL
        return "https://digilocker.meripehchaan.gov.in/public/oauth2/1/authorize"
                + "?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&state=" + (state != null ? state : "ridex_kyc");
    }

    @Override
    public DigiLockerVerificationDto getSimulatedDocument(String docType, User user) {
        String name = (user != null && user.getfName() != null)
                ? (user.getfName() + " " + (user.getlName() != null ? user.getlName() : ""))
                : "Verified Citizen";

        if ("DrivingLicense".equalsIgnoreCase(docType) || "DL".equalsIgnoreCase(docType)) {
            String dlNum = "DL-04" + (1990 + (int)(Math.random() * 30)) + "00" + (10000 + (int)(Math.random() * 89999));
            return new DigiLockerVerificationDto(
                    "Driving License",
                    dlNum,
                    name,
                    "12-07-1996",
                    "Male",
                    "MoRTH - Ministry of Road Transport & Highways, Govt. of India"
            );
        } else if ("PAN".equalsIgnoreCase(docType)) {
            String panNum = "ABCDE" + (1000 + (int)(Math.random() * 8999)) + "F";
            return new DigiLockerVerificationDto(
                    "PAN Card",
                    panNum,
                    name,
                    "15-08-1995",
                    "Male",
                    "Income Tax Department, Govt. of India"
            );
        }

        // Default: Aadhaar Card
        String aadhaarNum = "XXXX-XXXX-" + (1000 + (int)(Math.random() * 8999));
        return new DigiLockerVerificationDto(
                "Aadhaar Card",
                aadhaarNum,
                name,
                "01-01-1997",
                "Male",
                "UIDAI - Unique Identification Authority of India"
        );
    }

    @Override
    public boolean verifyAndApproveUser(User user, DigiLockerVerificationDto docDto) {
        if (user == null || docDto == null) {
            return false;
        }

        try {
            user.setGovtIdVerified(true);
            user.setGovtIdType("DigiLocker " + docDto.getDocType());
            user.setGovtIdNumber(docDto.getDocNumber());
            user.setGovtIdUpload("/images/digilocker_verified.png");

            // Award 50 Bonus Trust Coins for completing instant verified KYC
            int currentCoins = user.getCoins();
            user.setCoins(currentCoins + BONUS_TRUST_COINS);
            user.setDateUpdate(new Date());

            // Sync with GovtIDVerifyRequest table
            GovtIDVerifyRequest req = govtIdVerifyRequestRepository.findByUserId(user.getUserId());
            if (req == null) {
                req = new GovtIDVerifyRequest();
                req.setRequestId("GIVR" + customUUIDService.getUUID());
                req.setUserId(user.getUserId());
            }
            req.setVerified(true);
            req.setUpdated(true);
            req.setGovtIdType("DigiLocker " + docDto.getDocType());
            req.setGovtIdUniqueNumber(docDto.getDocNumber());
            req.setGovtIdImageUrl("/images/digilocker_verified.png");
            req.setGovtIdImagePublicId("DIGILOCKER_AUTO_VERIFIED");
            govtIdVerifyRequestRepository.save(req);

            user.setGovtIdVerifyRequest(req.getRequestId());
            userRepository.save(user);

            // Evict user caches
            profileCacheService.evictForUser(user.getUserId(), user.getEmail());

            logger.info("DigiLocker KYC successful for user: {} with document: {}", user.getEmail(), docDto.getDocType());

            // Send confirmation email
            try {
                emailServices.sendEmail(
                        user.getEmail(),
                        "🛡️ DigiLocker KYC Verified — Welcome to RideX Trusted Tier!",
                        "Congratulations " + user.getfName() + "!\n\n"
                                + "Your identity has been digitally verified via DigiLocker (" + docDto.getDocType() + ": " + docDto.getDocNumber() + ").\n\n"
                                + "🌟 Benefits Activated:\n"
                                + "• Verified Driver & Passenger Trust Badge on your profile.\n"
                                + "• +" + BONUS_TRUST_COINS + " Reward Trust Coins added to your wallet (Current balance: " + user.getCoins() + " coins).\n"
                                + "• Priority ride bookings and verified status for all co-travellers.\n\n"
                                + "Thank you for making carpooling safer with RideX!"
                );
            } catch (Exception ex) {
                logger.warn("Could not dispatch DigiLocker confirmation email: {}", ex.getMessage());
            }

            return true;
        } catch (Exception e) {
            logger.error("Failed to approve DigiLocker KYC for user {}: {}", user.getUserId(), e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isSandboxEnabled() {
        return sandboxEnabled || clientId.contains("RideXDigiLocker");
    }

    @Override
    public int getBonusTrustCoins() {
        return BONUS_TRUST_COINS;
    }

    @Override
    public Map<String, Object> getConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("sandboxEnabled", isSandboxEnabled());
        config.put("bonusCoins", BONUS_TRUST_COINS);
        config.put("issuer", "DigiLocker (National e-Governance Division, Govt. of India)");
        return config;
    }
}
