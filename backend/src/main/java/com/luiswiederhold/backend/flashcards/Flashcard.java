package com.luiswiederhold.backend.flashcards;


import org.springframework.cglib.core.Local;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Date;

public class Flashcard {
    // TODO:
    // add upload_flashcard_image endpoint
    // add upload_flashcard_latex endpoint
    // add path to create the flashcard hierachy
    // add last_score or score_history for the scores on this flashcard
    // create a algorithm to recommend flashcard with lower scores more often

    /*
    Schema:

    {
        "is_url_to_ressource": false,
        "value" : "Latex output oder link zu der Datei, die handschrifltich geschriebenes speichert. Ggf. muss dieses image für den browser rescaled werden" TODO: endpoint /rescale implementieren oder direkt im frontend vornehmen
        "creation_date": 23.03.2005,
        "last_updated_on": 23.03.2008,
        "last_revised_on": 23.03.2009,
    }
     */
    private String answerLatex;
    private URI answerImage;

    private String questionLatex;
    private URI questionImage;

    private LocalDateTime creationDate;
    private LocalDateTime lastUpdatedOn;
    private String hierachy;

    public Flashcard(String answerLatex, URI answerImage, String questionLatex, URI questionImage, LocalDateTime creationDate, LocalDateTime lastUpdatedOn, String hierachy, String username) {
        this.answerLatex = answerLatex;
        this.answerImage = answerImage;
        this.questionLatex = questionLatex;
        this.questionImage = questionImage;
        this.creationDate = creationDate;
        this.lastUpdatedOn = lastUpdatedOn;
        this.username = username;
        this.hierachy = hierachy;
    }

    private String username;


}
