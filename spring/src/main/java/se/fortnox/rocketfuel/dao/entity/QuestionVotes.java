package se.fortnox.rocketfuel.dao.entity;

public class QuestionVotes {
    Long questionId;
    Long votes;

    public QuestionVotes(Long questionId, Long votes) {
        this.questionId = questionId;
        this.votes = votes;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public Long getVotes() {
        return votes;
    }
}
