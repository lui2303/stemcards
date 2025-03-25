package com.luiswiederhold.backend.flashcards;

import com.luiswiederhold.backend.DTO.FlashcardContentDTO;
import com.luiswiederhold.backend.DTO.FlashcardSideDTO;
import com.luiswiederhold.backend.exception.LowConfidenceException;
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
    @Autowired
    private FlashcardService flashcardService;

    @PostMapping(value = "/create_flashcard", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Flashcard createNewFlashcard(@RequestParam("questionImage") MultipartFile questionImage, @RequestParam("answerImage") MultipartFile answerImage, @RequestPart("flashcardDTO") FlashcardContentDTO flashcardContentDTO) throws LowConfidenceException {
        URI questionURI = null;
        URI answerURI = null;

        String questionLatex = flashcardContentDTO.getQuestionLatex();
        String answerLatex = flashcardContentDTO.getAnswerLatex();

        Long ID = flashcardService.getNextFreeID();

        Flashcard flashcard = Flashcard.createEmptyFlashcard(ID, flashcardContentDTO.getUsername(), flashcardContentDTO.getPathHierachy());

        System.out.println(flashcard.toString());
        // TODO : remove the flascardSideDTO as it is unecessary and only complicates things

        if (questionImage != null) {
            questionLatex = flashcardService.image2Latex(questionImage);

            FlashcardSideDTO flashcardSideDTO = new FlashcardSideDTO(ID, flashcardContentDTO.getUsername(), flashcardContentDTO.getPathHierachy(), false);

            questionURI = flashcardService.storeImage(questionImage, flashcardSideDTO);
        }

        if (answerImage != null) {
            answerLatex = flashcardService.image2Latex(answerImage);

            FlashcardSideDTO flashcardSideDTO = new FlashcardSideDTO(ID, flashcardContentDTO.getUsername(), flashcardContentDTO.getPathHierachy(), true);

            answerURI = flashcardService.storeImage(answerImage, flashcardSideDTO);
        }

        flashcard.setID(ID);
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
