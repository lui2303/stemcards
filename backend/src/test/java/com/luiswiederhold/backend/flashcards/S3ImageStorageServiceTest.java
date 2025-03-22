package com.luiswiederhold.backend.flashcards;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class S3ImageStorageServiceTest {
    private final S3ImageStorageService s3ImageStorageService = new S3ImageStorageService();

    @Test
    void testConstructFlashcardImageURI_HighestHierachy() {
        Flashcard flashcard = new Flashcard("", null, "", null, LocalDateTime.now(), LocalDateTime.now(), "/", "wiederhold.luis@gmx.net");
        try {
            URI expectedURI = new URI("s3://your-bucket-name/wiederhold.luis@gmx.net/null/answer/image.png");
            URI resultURI = s3ImageStorageService.constructFlashcardImageURI(flashcard, true);
            System.out.println();
            assertEquals(expectedURI, resultURI);

        } catch (URISyntaxException e){
            System.out.println("Error");
        }

        try {
            URI excpectedURI = new URI("s3://your-bucket-name/wiederhold.luis@gmx.net/null/question/image.png");

            URI resultURI = s3ImageStorageService.constructFlashcardImageURI(flashcard, false);

            assertEquals(excpectedURI, resultURI);

        } catch (URISyntaxException e){
            System.out.println("Error");
        }

    }

    @Test
    void testConstructFlashcardImageURI_lowerHierachy() {
        Flashcard flashcard = new Flashcard("", null, "", null, LocalDateTime.now(), LocalDateTime.now(), "/", "wiederhold.luis@gmx.net");

        try {
            URI expectedURI = new URI("s3://your-bucket-name/wiederhold.luis@gmx.net/null/answer/image.png");

            URI resultURI = s3ImageStorageService.constructFlashcardImageURI(flashcard, true);

            assertEquals(expectedURI, resultURI);
        }catch (URISyntaxException e) {
            System.out.print(Arrays.toString(e.getStackTrace()));
        }


        try{
            URI expectedURI = new URI("s3://your-bucket-name/wiederhold.luis@gmx.net/null/question/image.png");

            URI resultURI = s3ImageStorageService.constructFlashcardImageURI(flashcard, false);

            assertEquals(expectedURI, resultURI);
        } catch (URISyntaxException e) {
            System.out.print(Arrays.toString(e.getStackTrace()));
        }

    }
}
