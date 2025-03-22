package com.luiswiederhold.backend.flashcards;

import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;

public class DiskImageStorageService implements ImageStorageService {
    @Override
    public URI constructFlashcardImageURI(Flashcard flashcard, boolean answer) throws URISyntaxException {
        return null;
    }

    @Override
    public URI storeFlashcardContent(MultipartFile image, boolean answer) throws URISyntaxException {
        return null;
    }

    // !!for testing purposes!!

}
