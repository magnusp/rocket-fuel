package se.fortnox.rocketfuel.api;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(path="/api", consumes = "application/json")
public interface AnswerResource {

    /**
     * Creates an answer to a question
     */
    @PostMapping("questions/{questionId}/answers")
    Mono<Answer> createAnswer(Answer answer, @PathVariable long questionId);

    /**
     * Returns all answers for a given question
     *
     * @param questionId the question that we want answers from
     * @return answers for a question
     */
    @GetMapping("questions/{questionId}/answers")
    Mono<List<Answer>> getAnswers(@PathVariable long questionId);

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
     * @param answer the new state of the answer
     * @return nothing
     */
    @PutMapping("answers/{answerId}")
    Mono<Void> updateAnswer(@PathVariable long answerId, @RequestBody Answer answer);

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

    /**
     * Returns the answer matching a certain slackId
     *
     * @return answers for a question
     */
    Mono<Answer> getAnswerBySlackId(String slackId);

    Mono<Answer> getAnswerById(long answerId);
}
