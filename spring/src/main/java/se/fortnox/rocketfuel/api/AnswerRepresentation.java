package se.fortnox.rocketfuel.api;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static java.util.Objects.nonNull;

public class AnswerRepresentation {
    private Long id;
    private Long userId;
    private String picture;
    private OffsetDateTime createdAt;
    private Integer votes;
    private String slackId;
    private Integer currentUserVote;
    private String answer;
    private LocalDateTime acceptedAt;
    private long questionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public String getSlackId() {
        return slackId;
    }

    public void setSlackId(String slackId) {
        this.slackId = slackId;
    }

    public Integer getCurrentUserVote() {
        return currentUserVote;
    }

    public void setCurrentUserVote(Integer currentUserVote) {
        this.currentUserVote = currentUserVote;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAccepted() {
        return nonNull(acceptedAt);
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(LocalDateTime acceptedAt) {
        this.acceptedAt = acceptedAt;
    }
}
