package com.luiswiederhold.backend.flashcards;

import com.luiswiederhold.backend.flashcards.DTOs.FlashcardSideDTO;
import com.luiswiederhold.backend.flashcards.exception.LowConfidenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
// TODO: Username can't contain /
@Service
public class FlashcardService {
    @Autowired
    private PostgresFlashcardStorageService postgresFlashcardStorageService;

    @Autowired
    private ImageStorageService imageStorageService;

    public FlashcardService(PostgresFlashcardStorageService postgresFlashcardStorageService) {
        this.postgresFlashcardStorageService = postgresFlashcardStorageService;
    }

    public URI storeImage(MultipartFile image, FlashcardSideDTO flashcardSideDTO) {
        URI res;
        try {
            res = imageStorageService.storeFlashcardContent(image, flashcardSideDTO);
        }catch (URISyntaxException e) {
            System.out.println(e);
            res = null;
        }

        return res;
    }

    public String image2Latex(MultipartFile image) throws LowConfidenceException {
        // API call to Mathpix Snip to convert image to Latex
        return "";
    }

    public Flashcard storeFlashcard(Flashcard flashcard) {
        // store flashcard in postgres database
        return flashcard;
    }
}
