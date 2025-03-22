package com.luiswiederhold.backend.flashcards;

import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;

public interface ImageStorageService {
    URI constructFlashcardImageURI(Flashcard flashcard, boolean answer) throws URISyntaxException;
    URI storeFlashcardContent(MultipartFile image, boolean answer) throws URISyntaxException;
}
