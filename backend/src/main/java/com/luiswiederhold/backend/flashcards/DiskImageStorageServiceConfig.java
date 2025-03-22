package com.luiswiederhold.backend.flashcards;

import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration("file-storage")
public class DiskImageStorageServiceConfig {
    public final Path STORAGE_LOCATION = Path.of("./././resources/flashcards");
}
