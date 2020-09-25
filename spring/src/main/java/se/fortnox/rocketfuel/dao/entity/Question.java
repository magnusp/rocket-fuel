package se.fortnox.rocketfuel.dao.entity;

import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;

public class Question {
    @Id
    private Long id;
    private String question;
    private String title;
    private Long bounty;
    private OffsetDateTime createdAt;
    private Long userId;
    private Boolean answerAccepted;
    private String slackId;

    public Long getBounty() {
        return bounty;
    }

    public void setBounty(Long bounty) {
        this.bounty = bounty;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getAnswerAccepted() {
        return answerAccepted;
    }

    public void setAnswerAccepted(Boolean answerAccepted) {
        this.answerAccepted = answerAccepted;
    }

    public String getSlackId() {
        return slackId;
    }

    public void setSlackId(String slackId) {
        this.slackId = slackId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
