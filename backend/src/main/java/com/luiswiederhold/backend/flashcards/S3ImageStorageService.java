package com.luiswiederhold.backend.flashcards;

import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;

public class S3ImageStorageService implements ImageStorageService {
    public URI constructFlashcardImageURI(Flashcard flashcard, boolean answer) throws URISyntaxException {
        // creates the relative s3 bucket path for the given user/flashcard e.x. /luis/analysis2/differential_equations/piccard_lindelöf/2200/answer
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("s3://your-bucket-name/");
        stringBuilder.append(flashcard.getUsername()).append("/");
        if(!flashcard.getHierachy().equals("/")) {
            stringBuilder.append(flashcard.getHierachy());
            stringBuilder.append("/");
        }

        stringBuilder.append(flashcard.getID());
        stringBuilder.append("/");
        if(answer) {
            stringBuilder.append("answer/image.png");
        } else {
            stringBuilder.append("question/image.png");
        }

        return new URI(stringBuilder.toString());
    }

    public URI storeFlashcardContent(MultipartFile image, boolean answer) throws URISyntaxException {
        // stores a question/answer in the corresponding s3 bucket calculated by constructFlashcardImageURI and returns that URI
        return new URI("");
    }
}
