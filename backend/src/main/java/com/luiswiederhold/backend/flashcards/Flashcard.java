package com.luiswiederhold.backend.flashcards;


import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "flashcards")
public class Flashcard {
    // add last_score or score_history for the scores on this flashcard
    // create a algorithm to recommend flashcard with lower scores more often
    @Id
    private Long ID;
    @Column(nullable = false)
    private String answerLatex;
    @Column
    private String answerImage; // stores the URI
    @Column(nullable = false)
    private String questionLatex;
    @Column
    private String questionImage; // stores the URI as String
    @Column(nullable = false)
    private LocalDateTime creationDate;
    @Column(nullable = false)
    private LocalDateTime lastUpdatedOn;
    @Column(nullable = false)
    private String hierachy;
    @Column(nullable = false)
    private String username;

    public static Flashcard createEmptyFlashcard(Long ID, String username, String hierachy) {
        return new Flashcard(
                ID,
                "",
                null,
                "",
                null,
                hierachy,
                username
        );
    }

    public Flashcard(Long ID, String answerLatex, String answerImage, String questionLatex, String questionImage, String hierachy, String username) {
        this.answerLatex = answerLatex;
        this.answerImage = answerImage;
        this.questionLatex = questionLatex;
        this.questionImage = questionImage;
        this.username = username;
        this.hierachy = hierachy;
        this.ID = ID;
    }

    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDateTime.now();
        this.lastUpdatedOn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdatedOn = LocalDateTime.now();
    }

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

    public String getAnswerImage() {
        return answerImage;
    }

    public void setAnswerImage(String answerImage) {
        this.answerImage = answerImage;
    }

    public String getQuestionLatex() {
        return questionLatex;
    }

    public void setQuestionLatex(String questionLatex) {
        this.questionLatex = questionLatex;
    }

    public String getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(String questionImage) {this.questionImage = questionImage;}

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
