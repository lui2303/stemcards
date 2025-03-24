package com.luiswiederhold.backend.flashcards;

import com.luiswiederhold.backend.flashcards.DTOs.FlashcardSideDTO;
import com.luiswiederhold.backend.exception.LowConfidenceException;
import com.luiswiederhold.backend.flashcards.imagestorage.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;

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
        // store the image of the flashcard question or the flashcard answer using the ImageStorageService API
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
