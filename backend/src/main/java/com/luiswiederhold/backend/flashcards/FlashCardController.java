package com.luiswiederhold.backend.flashcards;

import com.luiswiederhold.backend.DTO.FlashcardContentDTO;
import com.luiswiederhold.backend.exception.LowConfidenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
public class FlashCardController {
    private static final Logger logger = LoggerFactory.getLogger(FlashCardController.class);
    @Autowired
    private FlashcardService flashcardService;

    @PostMapping(value = "/create_flashcard", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Flashcard createNewFlashcard(@RequestParam("questionImage") MultipartFile questionImage, @RequestParam("answerImage") MultipartFile answerImage, @RequestPart("flashcardDTO") FlashcardContentDTO flashcardContentDTO) throws LowConfidenceException {
        URI questionURI = null;
        URI answerURI = null;

        String questionLatex = flashcardContentDTO.getQuestionLatex();
        String answerLatex = flashcardContentDTO.getAnswerLatex();

        Long ID = flashcardService.getNextFreeID();

        Flashcard flashcard = Flashcard.createEmptyFlashcard(ID, flashcardContentDTO.getUsername(), flashcardContentDTO.getHierachy());

        if (questionImage != null) {
            logger.debug("questionImage is not null, trying to convert it to latex and store it afterwards...");
            questionLatex = flashcardService.image2Latex(questionImage);

            logger.debug("Detected question Latex: " + questionLatex);

            questionURI = flashcardService.storeImage(questionImage, ID, flashcardContentDTO.getUsername(), flashcardContentDTO.getHierachy(), false);
            if(questionURI == null) logger.warn("Didn't store question Image");
        }

        if (answerImage != null) {
            logger.debug("answerImage is not null, trying to convert it to latex and store it afterwards...");
            answerLatex = flashcardService.image2Latex(answerImage);

            logger.debug("Detected answer Latex: " + questionLatex);
            answerURI = flashcardService.storeImage(answerImage, ID, flashcardContentDTO.getUsername(), flashcardContentDTO.getHierachy(), true);
            if(answerURI == null) logger.warn("Didn't store answer Image");
        }

        flashcard.setAnswerLatex(answerLatex);

        if(answerURI != null) flashcard.setAnswerImage(answerURI.toString());

        flashcard.setQuestionLatex(questionLatex);

        if(questionURI != null) flashcard.setQuestionImage(questionURI.toString()); // convert the URI to String to save it otherwise it is stored in binary format

        flashcard.setCreationDate(LocalDateTime.now());
        flashcard.setLastUpdatedOn(LocalDateTime.now());

        return flashcardService.storeFlashcard(flashcard);
    }

    @ExceptionHandler(LowConfidenceException.class)
    public ResponseEntity<String> handleLowConfidence(LowConfidenceException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
