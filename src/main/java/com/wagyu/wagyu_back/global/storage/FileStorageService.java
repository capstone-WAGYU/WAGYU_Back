package com.wagyu.wagyu_back.global.storage;

import com.wagyu.wagyu_back.global.exception.CustomException;
import com.wagyu.wagyu_back.global.exception.ErrorCode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp");
    private static final long MAX_SIZE = 5 * 1024 * 1024;

    private final String uploadDir;
    private final String publicPathPrefix;

    private Path rootLocation;

    public FileStorageService(
            @Value("${app.upload.dir:uploads}") String uploadDir,
            @Value("${app.upload.public-path:/uploads}") String publicPathPrefix
    ) {
        this.uploadDir = uploadDir;
        this.publicPathPrefix = publicPathPrefix;
    }

    @PostConstruct
    public void init() {
        try {
            this.rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(this.rootLocation);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_ERROR);
        }
    }

    public String store(MultipartFile file, String subDir) {
        if (file == null || file.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
        if (file.getSize() > MAX_SIZE) {
            throw new CustomException(ErrorCode.FILE_TOO_LARGE);
        }

        String original = file.getOriginalFilename();
        String ext = extractExtension(original);
        if (!ALLOWED_EXTENSIONS.contains(ext)) {
            throw new CustomException(ErrorCode.INVALID_FILE_TYPE);
        }

        try {
            Path targetDir = rootLocation.resolve(subDir).normalize();
            if (!targetDir.startsWith(rootLocation)) {
                throw new CustomException(ErrorCode.INVALID_REQUEST);
            }
            Files.createDirectories(targetDir);

            String filename = UUID.randomUUID() + "." + ext;
            Path target = targetDir.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            return publicPathPrefix + "/" + subDir + "/" + filename;
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_ERROR);
        }
    }

    public void delete(String publicUrl) {
        if (publicUrl == null || !publicUrl.startsWith(publicPathPrefix + "/")) {
            return;
        }
        String relative = publicUrl.substring(publicPathPrefix.length() + 1);
        Path target = rootLocation.resolve(relative).normalize();
        if (!target.startsWith(rootLocation)) {
            return;
        }
        try {
            Files.deleteIfExists(target);
        } catch (IOException ignored) {
        }
    }

    public Path getRootLocation() {
        return rootLocation;
    }

    public String getPublicPathPrefix() {
        return publicPathPrefix;
    }

    private String extractExtension(String filename) {
        if (filename == null) return "";
        int idx = filename.lastIndexOf('.');
        if (idx < 0 || idx == filename.length() - 1) return "";
        return filename.substring(idx + 1).toLowerCase();
    }
}
