package com.luiswiederhold.backend.flashcards;

import com.luiswiederhold.backend.flashcards.DTOs.FlashcardSideDTO;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;

public interface ImageStorageService {
    URI constructFlashcardImageURI(FlashcardSideDTO flashcardSideDTO) throws URISyntaxException;
    URI storeFlashcardContent(MultipartFile image, FlashcardSideDTO flashcardSideDTO) throws URISyntaxException;
    Flashcard getFlashcard(Long ID);
}
