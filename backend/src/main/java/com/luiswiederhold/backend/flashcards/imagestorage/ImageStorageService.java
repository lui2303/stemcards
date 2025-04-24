package com.luiswiederhold.backend.flashcards.imagestorage;

import com.luiswiederhold.backend.flashcards.Flashcard;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public interface ImageStorageService {
    URI constructFlashcardImageURI(Long ID, String username, String hierachy, boolean isAnswer) throws URISyntaxException;
    URI storeFlashcardContent(MultipartFile image, URI targetURI) throws IOException;
    Flashcard getFlashcard(Long ID);

    default void convertSvgToPng(MultipartFile image){

    }
}
