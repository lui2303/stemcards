package com.luiswiederhold.backend.flashcards;

import com.luiswiederhold.backend.exception.LowConfidenceException;
import com.luiswiederhold.backend.flashcards.imagestorage.ImageStorageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class FlashcardService {
    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    @Transactional
    public Long getNextFreeID() {
        return flashcardRepository.getNextSequenceValue(); // gets the next free ID value, this process needs to be done manually because the URIs need to know the ID
    }

    public URI storeImage(MultipartFile image, Long ID, String username, String hierachy, boolean isAnswer) {
        // store the image of the flashcard question or the flashcard answer using the ImageStorageService API
        URI res;
        try {
            res = imageStorageService.storeFlashcardContent(image, ID, username, hierachy, isAnswer);
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
        flashcardRepository.save(flashcard);
        return flashcard;
    }
}
