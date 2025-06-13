package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.helpers.AppConstants;
import com.RideX.carpooling.services.ImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

@Service
public class ImageServiceImplementation implements ImageService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000; // 1 second

    private Cloudinary cloudinary;

    public ImageServiceImplementation(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile contactImage, String filename) {
        logger.info("ImageServiceImplementation :: uploadImage(MultipartFile contactImage: [{}], String filename:[{}]))", contactImage.toString(), filename);
        
        int retryCount = 0;
        while (retryCount < MAX_RETRIES) {
            try {
                byte[] data = new byte[contactImage.getInputStream().available()];
                contactImage.getInputStream().read(data);
                cloudinary.uploader().upload(data, ObjectUtils.asMap(
                        "public_id", filename));

                return this.getUrlFromPublicId(filename);
            } catch (UnknownHostException e) {
                retryCount++;
                logger.error("Failed to connect to Cloudinary (attempt {}/{}): {}", retryCount, MAX_RETRIES, e.getMessage());
                
                if (retryCount < MAX_RETRIES) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(RETRY_DELAY_MS * retryCount);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                } else {
                    logger.error("Failed to upload image after {} attempts", MAX_RETRIES);
                    return null;
                }
            } catch (IOException e) {
                logger.error("Error uploading image: {}", e.getMessage());
                return null;
            }
        }
        return null;
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        logger.info("ImageServiceImplementation :: getUrlFromPublicId(publicId: [{}]))", publicId);
        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(AppConstants.CONTACT_IMAGE_WIDTH)
                                .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                                .crop(AppConstants.CONTACT_IMAGE_CROP))
                .generate(publicId);
    }
}
