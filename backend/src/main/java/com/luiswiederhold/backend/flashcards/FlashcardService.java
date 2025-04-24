package com.luiswiederhold.backend.flashcards;

import com.luiswiederhold.backend.exception.LowConfidenceException;
import com.luiswiederhold.backend.flashcards.imagestorage.ImageStorageService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class FlashcardService {

    private static final Logger logger = LoggerFactory.getLogger(FlashcardService.class);
    private final FlashcardRepository flashcardRepository;

    private final ImageStorageService imageStorageService;

    public FlashcardService(FlashcardRepository flashcardRepository, ImageStorageService imageStorageService) {
        this.flashcardRepository = flashcardRepository;
        this.imageStorageService = imageStorageService;
    }

    @Transactional
    public Long getNextFreeID() {
        logger.debug("Generating next free flashcard ID");
        Long nextFreeID = flashcardRepository.getNextSequenceValue();
        logger.debug("Next free flashcard ID is: " + nextFreeID);
        return nextFreeID; // gets the next free ID value, this process needs to be done manually because the URIs need to know the ID
    }

    public URI storeImage(MultipartFile image, Long ID, String username, String hierachy, boolean isAnswer) {
        // store the image of the flashcard question or the flashcard answer using the ImageStorageService API
        if(image == null) return null;
        if(image.getSize() == 0L) throw new IllegalArgumentException("File: " + image.getName() + " is empty and therefore isn't allowed to be stored");

        logger.debug("Starting storage of Image: " + image.getName());
        URI targetURI;

        try {
            targetURI = imageStorageService.constructFlashcardImageURI(ID, username, hierachy, isAnswer);
        }catch (URISyntaxException e) {
            logger.error(e.toString());
            System.out.println(e.toString());
            return null;
        }

        try {
            imageStorageService.storeFlashcardContent(image, targetURI);
        }catch (IOException e) {
            logger.error(e.toString());
            System.out.println(e.toString());
            return null;
        }
        return targetURI;
    }

    public String image2Latex(MultipartFile image) throws LowConfidenceException {
        // API call to Mathpix Snip to convert image to Latex
        return "";
    }

    public Flashcard storeFlashcard(Flashcard flashcard) {
        logger.debug("Storing flashcard for user: " + flashcard.getUsername() + "with ID: "+ flashcard.getID());
        flashcardRepository.save(flashcard);
        return flashcard;
    }
}
