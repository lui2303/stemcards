package com.luiswiederhold.backend.flashcards.imagestorage;

import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration("file-storage")
public class DiskImageStorageServiceConfig {
    public static final Path STORAGE_LOCATION = Path.of("src/main/resources/flashcards").toAbsolutePath().normalize(); // in file: URI format
}
