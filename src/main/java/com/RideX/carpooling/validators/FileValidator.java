package com.RideX.carpooling.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileValidator implements ConstraintValidator<ValidFile, Object> {

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull/@NotEmpty handle if needed
        }

        if (value instanceof MultipartFile file) {
            return validateFile(file, context);
        }

        if (value instanceof List<?> files) {
            for (Object f : files) {
                if (f instanceof MultipartFile file && !validateFile(file, context)) {
                    return false;
                }
            }
            return true;
        }

        return false; // Invalid type
    }

    private boolean validateFile(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File must not be empty").addConstraintViolation();
            return false;
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File must be less than 2MB").addConstraintViolation();
            return false;
        }

        return true;
    }
}

