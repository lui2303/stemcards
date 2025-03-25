package com.luiswiederhold.backend.flashcards.imagestorage;

import com.luiswiederhold.backend.flashcards.Flashcard;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Profile("prod")
public class S3ImageStorageService implements ImageStorageService {
    @Override
    public URI constructFlashcardImageURI(Long ID, String username, String hierachy, boolean isAnswer) throws URISyntaxException {
        // creates the relative s3 bucket path for the given user/flashcard e.x. /luis/analysis2/differential_equations/piccard_lindelöf/2200/answer
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("s3://your-bucket-name/");
        stringBuilder.append(username).append("/");
        if(!hierachy.equals("/")) {
            stringBuilder.append(hierachy);
            stringBuilder.append("/");
        }

        stringBuilder.append(ID);
        stringBuilder.append("/");
        if(isAnswer) {
            stringBuilder.append("answer/image.png");
        } else {
            stringBuilder.append("question/image.png");
        }

        return new URI(stringBuilder.toString());
    }

    public URI storeFlashcardContent(MultipartFile image, Long ID, String username, String hierachy, boolean isAnswer) throws URISyntaxException {
        // stores a question/answer in the corresponding s3 bucket calculated by constructFlashcardImageURI and returns that URI
        return new URI("");
    }

    @Override
    public Flashcard getFlashcard(Long ID) {
        return null;
    }
}
