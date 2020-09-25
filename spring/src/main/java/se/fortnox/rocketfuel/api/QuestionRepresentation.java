package se.fortnox.rocketfuel.api;

import java.time.OffsetDateTime;

/**
 * Defines a question. A question is connected to the {@link User} asking it.
 * <p>
 * A question can be answered if it has a accepted answer.
 * <p>
 * {@link AnswerRepresentation}s are linked to a question.
 */
public class QuestionRepresentation {
    private Long id;
    private String question;
    private String title;
    private Long bounty;
    private OffsetDateTime createdAt;
    private boolean answerAccepted;
    private String slackId;
    private String name;
    private String picture;

    public QuestionRepresentation() {
    }

    public QuestionRepresentation(Long id, String question, String title, Long bounty, OffsetDateTime createdAt, boolean answerAccepted, String slackId, String name, String picture) {
        this.id = id;
        this.question = question;
        this.title = title;
        this.bounty = bounty;
        this.createdAt = createdAt;
        this.answerAccepted = answerAccepted;
        this.slackId = slackId;
        this.name = name;
        this.picture = picture;
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

     /*
     public Integer getVotes() {
         return votes;
     }

     public void setVotes(Integer votes) {
         this.votes = votes;
     }*/

    public String getSlackId() {
        return slackId;
    }

    public void setSlackId(String slackId) {
        this.slackId = slackId;
    }

     /*
     public Integer getCurrentUserVote() {
         return currentUserVote;
     }

     public void setCurrentUserVote(Integer currentUserVote) {
         this.currentUserVote = currentUserVote;
     }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAnswerAccepted() {
        return answerAccepted;
    }

    public void setAnswerAccepted(boolean answerAccepted) {
        this.answerAccepted = answerAccepted;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getBounty() {
        return bounty;
    }

    public void setBounty(Long bounty) {
        this.bounty = bounty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
