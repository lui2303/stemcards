package com.luiswiederhold.backend.DTO;

import com.luiswiederhold.backend.flashcards.Flashcard;

public class FlashcardSideDTO {
    private Long ID;
    private String username;
    private String hierachy;
    private boolean answerSide;

    public FlashcardSideDTO(Long ID, String username, String hierachy, boolean answerSide) {
        this.ID = ID;
        this.username = username;
        this.hierachy = hierachy;
        this.answerSide = answerSide;
    }

    public static FlashcardSideDTO createFromFlashcard(Flashcard flashcard, boolean answerSide) {
        return new FlashcardSideDTO(
                flashcard.getID(),
                flashcard.getUsername(),
                flashcard.getHierachy(),
                answerSide
        );
    }
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHierachy() {
        return hierachy;
    }

    public void setHierachy(String hierachy) {
        this.hierachy = hierachy;
    }

    public boolean isAnswerSide() {
        return answerSide;
    }

    public void setAnswerSide(boolean answerSide) {
        this.answerSide = answerSide;
    }
}
