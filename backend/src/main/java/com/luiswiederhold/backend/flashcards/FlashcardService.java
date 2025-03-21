package com.luiswiederhold.backend.flashcards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Path;

@Service
public class FlashcardService {
    @Autowired
    private PostgresFlashcardStorageService postgresFlashcardStorageService;
    @Autowired
    private S3FlashcardStorageService s3FlashcardStorageService;


    public URI constructFlashcardImageURI(Flashcard flashcard) {
        // creates the relative s3 bucket path for the given user/flashcard e.x. /luis/analysis2/diferential_equations/piccard_lindelöf/2200/answer
        return null;
    }

    public URI storeImage(boolean answer, MultipartFile image) {
        return null;
    }

    public String image2Latex(MultipartFile image) {
        // API call to Mathpix Snip to convert image to Latex
        return "";
    }

    public Flashcard storeFlashcard(Flashcard flashcard) {

        // store flashcard in postgres database

        return flashcard;
    }
}
