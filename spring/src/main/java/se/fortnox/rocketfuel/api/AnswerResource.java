package se.fortnox.rocketfuel.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(path="/api", consumes = "application/json")
public interface AnswerResource {

    /**
     * Creates an answer to a question
     */
    @PostMapping("questions/{questionId}/answers")
    Mono<AnswerDocument> createAnswer(AnswerDocument answerDocument, @PathVariable long questionId);

    /**
     * Returns all answers for a given question
     *
     * @param questionId the question that we want answers from
     * @return answers for a question
     */
    @GetMapping("questions/{questionId}/answers")
    Mono<List<AnswerDocument>> getAnswers(@PathVariable long questionId);

    /**
     * Marks a given answer as answered. The method will mark the question as well as answered.
     * @param answerId the answer id
     * @return nothing
     */
    @PatchMapping("answers/{answerId}/accept")
    Mono<Void> markAsAcceptedAnswer(@PathVariable long answerId);

    /**
     * Updates a answer
     *
     * Requires the invoker to be the creator of the answer.
     *
     * Only title and answer can be answered.
     *
     * @param answerId the answers unique id
     * @param answerDocument the new state of the answer
     * @return nothing
     */
    @PutMapping("answers/{answerId}")
    Mono<Void> updateAnswer(@PathVariable long answerId, @RequestBody AnswerDocument answerDocument);

    /**
     * Deletes an answer
     *
     * Requires the invoker to be the creator of the answer.
     * @param auth
     * @param answerId
     * @return nothing
     */
    @DeleteMapping("answers/{answerId}")
    Mono<Void> deleteAnswer(@PathVariable long answerId);

    /**
     * Upvotes (+1) an answer.
     * Upvoting an existing downvote will result in a neutral (0) vote.
     * Upvoting an existing upvote will result in error.
     * @param auth
     * @param answerId
     * @return
     */
    @PostMapping("answers/{answerId}/upvote")
    Mono<Void> upVoteAnswer(@PathVariable long answerId);

    /**
     * Downvotes (-1) an answer.
     * Downvoting an existing upvote will result in a neutral (0) vote.
     * Downvoting and existing downvote will result in error.
     * @param auth
     * @param answerId
     * @return
     */
    @PostMapping("answers/{answerId}/downvote")
    Mono<Void> downVoteAnswer(@PathVariable long answerId);
}
