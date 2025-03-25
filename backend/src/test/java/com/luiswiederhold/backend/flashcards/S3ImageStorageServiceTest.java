package com.luiswiederhold.backend.flashcards;

import com.luiswiederhold.backend.flashcards.imagestorage.S3ImageStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        Flashcard flashcard = new Flashcard(1L,"", null, "", null, "/", "wiederhold.luis@gmx.net");

        try {
            URI expectedURI = new URI("s3://your-bucket-name/wiederhold.luis@gmx.net/1/answer/image.png");
            URI resultURI = s3ImageStorageService.constructFlashcardImageURI(flashcard.getID(), flashcard.getUsername(), flashcard.getHierachy(), true);
            System.out.println();
            assertEquals(expectedURI, resultURI);

        } catch (URISyntaxException e){
            System.out.println("Error");
        }

        try {
            URI excpectedURI = new URI("s3://your-bucket-name/wiederhold.luis@gmx.net/null/question/image.png");

            URI resultURI = s3ImageStorageService.constructFlashcardImageURI(flashcard.getID(), flashcard.getUsername(), flashcard.getHierachy(), false);

            assertEquals(excpectedURI, resultURI);

        } catch (URISyntaxException e){
            System.out.println("Error");
        }

    }

    @Test
    void testConstructFlashcardImageURI_lowerHierachy() {
        Flashcard flashcard = new Flashcard(1L, "", null, "", null, "/", "wiederhold.luis@gmx.net");

        try {
            URI expectedURI = new URI("s3://your-bucket-name/wiederhold.luis@gmx.net/1/answer/image.png");

            URI resultURI = s3ImageStorageService.constructFlashcardImageURI(flashcard.getID(), flashcard.getUsername(), flashcard.getHierachy(), true);

            assertEquals(expectedURI, resultURI);
        }catch (URISyntaxException e) {
            System.out.print(Arrays.toString(e.getStackTrace()));
        }


        try{
            URI expectedURI = new URI("s3://your-bucket-name/wiederhold.luis@gmx.net/1/question/image.png");

            URI resultURI = s3ImageStorageService.constructFlashcardImageURI(flashcard.getID(), flashcard.getUsername(), flashcard.getHierachy(), false);

            assertEquals(expectedURI, resultURI);
        } catch (URISyntaxException e) {
            System.out.print(Arrays.toString(e.getStackTrace()));
        }

    }
}
