package com.wagyu.wagyu_back.global.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class StorageWebConfig implements WebMvcConfigurer {
    private final FileStorageService fileStorageService;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String prefix = fileStorageService.getPublicPathPrefix();
        String location = "file:" + fileStorageService.getRootLocation().toString().replace("\\", "/") + "/";
        registry.addResourceHandler(prefix + "/**")
                .addResourceLocations(location);
    }
}
