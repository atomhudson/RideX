package com.RideX.carpooling.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public class FileValidatorForList implements ConstraintValidator<ValidFile, List<MultipartFile>> {

    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2; // 2MB

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (files == null || files.isEmpty()) {
            return true; // Let @NotEmpty handle this case
        }

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Each file must be non-empty").addConstraintViolation();
                return false;
            }

            if (file.getSize() > MAX_FILE_SIZE) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Each file must be less than 2MB").addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
