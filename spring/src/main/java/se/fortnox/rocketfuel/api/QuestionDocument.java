package se.fortnox.rocketfuel.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

/**
 * Defines a question. A question is connected to the {@link User} asking it.
 * <p>
 * A question can be answered if it has a accepted answer.
 * <p>
 * {@link AnswerDocument}s are linked to a question.
 */
public class QuestionDocument {
    private String question;
    private String title;
    @JsonProperty("createdBy")
    private String createdByUsername;
    @JsonProperty("userId")
    private Long createdByUserId;
    private OffsetDateTime createdAt;
    private Long votes;
    private boolean answerAccepted;
    private Long id;
    private String picture;

    public QuestionDocument() {
    }

    public QuestionDocument(String question, String title, String createdByUsername, Long createdByUserId, OffsetDateTime createdAt, Long votes, boolean answerAccepted, Long id, String picture) {
        this.question = question;
        this.title = title;
        this.createdByUsername = createdByUsername;
        this.createdByUserId = createdByUserId;
        this.createdAt = createdAt;
        this.votes = votes;
        this.answerAccepted = answerAccepted;
        this.id = id;
        this.picture = picture;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public boolean isAnswerAccepted() {
        return answerAccepted;
    }

    public void setAnswerAccepted(boolean answerAccepted) {
        this.answerAccepted = answerAccepted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
