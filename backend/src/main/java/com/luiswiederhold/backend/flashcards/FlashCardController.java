package com.luiswiederhold.backend.flashcards;

import com.luiswiederhold.backend.flashcards.DTOs.FlashcardDTO;
import com.luiswiederhold.backend.flashcards.exception.LowConfidenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDateTime;

@Controller
public class FlashCardController {
    @Autowired
    private FlashcardService flashcardService;

    @PostMapping("/create_flashcard")
    public Flashcard createNewFlashcard(@RequestParam MultipartFile questionImage, @RequestParam MultipartFile answerImage, @RequestBody FlashcardDTO flashcardDTO) throws LowConfidenceException {
        URI questionURI = null;
        URI answerURI = null;
        String questionLatex = flashcardDTO.getQuestionLatex();
        String answerLatex = flashcardDTO.getAnswerLatex();

        if (questionImage != null) {
            questionLatex = flashcardService.image2Latex(questionImage);

            questionURI = flashcardService.storeImage(false, questionImage);
        }

        if (answerImage != null) {
            answerLatex = flashcardService.image2Latex(answerImage);

            answerURI = flashcardService.storeImage(true, answerImage);
        }

        Flashcard flashcard = new Flashcard(answerLatex, answerURI, questionLatex, questionURI, LocalDateTime.now(), LocalDateTime.now(), flashcardDTO.getUsername());

        return flashcardService.storeFlashcard(flashcard);
    }

    @ExceptionHandler(LowConfidenceException.class)
    public ResponseEntity<String> handleLowConfidence(LowConfidenceException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
