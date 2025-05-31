package com.luiswiederhold.backend.flashcards;

import com.luiswiederhold.backend.DTO.FlashcardContentDTO;
import com.luiswiederhold.backend.authentication.Context;
import com.luiswiederhold.backend.exception.LowConfidenceException;
import com.luiswiederhold.backend.Utils;

import jakarta.servlet.http.HttpServletRequest;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class FlashCardController {
    private static final Logger logger = LoggerFactory.getLogger(FlashCardController.class);
    private final FlashcardService flashcardService;
    private final FlashcardRepository flashcardRepository;
    private final Context context;

    public FlashCardController(FlashcardService flashcardService, FlashcardRepository flashcardRepository, Context context) {
        this.flashcardService = flashcardService;
        this.flashcardRepository = flashcardRepository;
        this.context = context;
    }

    @PostMapping(value = "/flashcards/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Flashcard createNewFlashcard(@RequestParam("questionImage") MultipartFile questionImage, @RequestParam("answerImage") MultipartFile answerImage, @RequestPart("flashcardDTO") FlashcardContentDTO flashcardContentDTO) throws LowConfidenceException {
        // TODO: handle flashcard with images and latex different to increase perfomance
        // TODO: remove username from FlashCardContentDTO and retrieve it via the Context as the user needs to be authenticated for this route anyways

        if(questionImage != null && !Utils.verifyFileContentType(questionImage, new ArrayList<>(List.of("image/svg+xml")))){
            logger.error("The file provided for questionImage is not of type image/svg+xml");
            return null;
        }
        if(answerImage != null && !Utils.verifyFileContentType(answerImage, new ArrayList<>(List.of("image/svg+xml")))) {
            logger.error("The file provided for answerImage is not of type image/svg+xml");
            return null;
        }

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

    @PutMapping("/flashcards/update")
    public String updateFlashcard(Flashcard flashcard) {
        // TODO: don't forget the image updates. To save cost hash the images and compare hashes.
        return "";
    }

    @GetMapping("/flashcards/{id}")
    public Flashcard getFlashcard(@PathVariable Long id, HttpServletRequest request) {
        String username = Context.getCurrentUsername();

        Flashcard flashcard = flashcardRepository.findByID(id);

        if(flashcard == null) {
            logger.warn("Flashcard with ID: " + id + " doesn't exist");
            return null;
        }

        if(!context.usernamesMatchOrAdmin(flashcard.getUsername())) {
            logger.warn("User: " + flashcard.getUsername() + " has no permission to view flashcard of user " + username);
            return null;
        }

        return flashcard;
    }

    @GetMapping("/flashcards/all")
    public Iterable<Flashcard> getAllUserFlashcards() {
        String username = Context.getCurrentUsername();

        return flashcardRepository.findAllByUsername(username);
    }

    @GetMapping("/flashcards/all/hierachy")
    public List<Flashcard> getAllEqualHierachyFlashcards(String hierachy) {
        String username = Context.getCurrentUsername();

        return flashcardRepository.findByUsernameAndHierachy(username, hierachy);
    }


    @ExceptionHandler(LowConfidenceException.class)
    public ResponseEntity<String> handleLowConfidence(LowConfidenceException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
