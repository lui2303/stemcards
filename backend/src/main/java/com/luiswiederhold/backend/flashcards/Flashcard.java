package com.luiswiederhold.backend.flashcards;


import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "flashcards")
public class Flashcard {
    // remove nanotime ID and replace it with a lazy approach by initializing a Flashcard object with null attributes
    // to set later with setters
    // move this to a Entity object to store it in the flashcard database
    // add last_score or score_history for the scores on this flashcard
    // create a algorithm to recommend flashcard with lower scores more often


    @Override
    public String toString() {
        return "Flashcard{" +
                "ID=" + ID +
                ", answerLatex='" + answerLatex + '\'' +
                ", answerImage=" + answerImage +
                ", questionLatex='" + questionLatex + '\'' +
                ", questionImage=" + questionImage +
                ", creationDate=" + creationDate +
                ", lastUpdatedOn=" + lastUpdatedOn +
                ", hierachy='" + hierachy + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Id
    private Long ID;
    @Column(unique = true, nullable = false)
    private String answerLatex;
    @Column
    private URI answerImage;
    @Column(unique = true, nullable = false)
    private String questionLatex;
    @Column
    private URI questionImage;
    @Column(unique = true, nullable = false)
    private LocalDateTime creationDate;
    @Column(unique = true, nullable = false)
    private LocalDateTime lastUpdatedOn;
    @Column(unique = true, nullable = false)
    private String hierachy;
    @Column(unique = true, nullable = false)
    private String username;

    public static Flashcard createEmptyFlashcard(Long ID, String username, String hierachy) {
        return new Flashcard(
                ID,
                "",
                null,
                "",
                null,
                null,
                null,
                hierachy,
                username
        );
    }

    public Flashcard(Long ID, String answerLatex, URI answerImage, String questionLatex, URI questionImage, LocalDateTime creationDate, LocalDateTime lastUpdatedOn, String hierachy, String username) {
        this.answerLatex = answerLatex;
        this.answerImage = answerImage;
        this.questionLatex = questionLatex;
        this.questionImage = questionImage;
        this.creationDate = creationDate;
        this.lastUpdatedOn = lastUpdatedOn;
        this.username = username;
        this.hierachy = hierachy;
    }

    public Flashcard() {}

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

    public void setQuestionImage(URI questionImage) {this.questionImage = questionImage;}

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
