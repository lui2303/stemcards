package com.luiswiederhold.backend.DTO;

public class FlashcardContentDTO {
    // DTO for a flashcard that is described by a question in Latex and an answer in Latex
    private String questionLatex; // Latex String
    private String answerLatex;
    private String username;
    private String hierachy;

    public FlashcardContentDTO(String questionLatex, String answerLatex, String username, String pathHierachy) {
        this.questionLatex = questionLatex;
        this.answerLatex = answerLatex;
        this.username = username;
        this.hierachy = pathHierachy;
    }

    public String getQuestionLatex() {
        return questionLatex;
    }

    public void setQuestionLatex(String questionLatex) {
        this.questionLatex = questionLatex;
    }

    public String getAnswerLatex() {
        return answerLatex;
    }

    public void setAnswerLatex(String answerLatex) {
        this.answerLatex = answerLatex;
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

    public void setHierachy(String pathHierachy) {
        this.hierachy = pathHierachy;
    }
}
