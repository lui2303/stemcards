package com.luiswiederhold.backend.flashcards;


import org.springframework.cglib.core.Local;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Date;

public class Flashcard {
    // TODO:
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

    private Long ID;
    private String answerLatex;
    private URI answerImage;

    private String questionLatex;
    private URI questionImage;

    private LocalDateTime creationDate;
    private LocalDateTime lastUpdatedOn;
    private String hierachy;
    private String username;

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

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getAnswerLatex() {
        return answerLatex;
    }

    public void setAnswerLatex(String answerLatex) {
        this.answerLatex = answerLatex;
    }

    public URI getAnswerImage() {
        return answerImage;
    }

    public void setAnswerImage(URI answerImage) {
        this.answerImage = answerImage;
    }

    public String getQuestionLatex() {
        return questionLatex;
    }

    public void setQuestionLatex(String questionLatex) {
        this.questionLatex = questionLatex;
    }

    public URI getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(URI questionImage) {
        this.questionImage = questionImage;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(LocalDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getHierachy() {
        return hierachy;
    }

    public void setHierachy(String hierachy) {
        this.hierachy = hierachy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
