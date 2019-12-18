package se.fortnox.rocketfuel.api;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping(path="api", consumes = "application/json")
public interface QuestionResource {

    /**
     * Return a question if found by a slack id
     *
     * @param slackId id from slack
     * @return question
     */
    @GetMapping("questions/byslackid/{slackId}")
    Mono<Question> getQuestionBySlackThreadId(@PathVariable String slackId);

    /**
     * Returns a specific question to the client
     */
    @GetMapping("questions/{questionId}")
    Mono<Question> getQuestion(@PathVariable long questionId);

    /**
     * Return a list of latest questions
     *
     * @param options Sorting and limiting options
     * @return questions
     */
    @GetMapping("questions/latest")
    //Mono<List<Question>> getLatestQuestions(CollectionOptions options);
    Mono<List<Question>> getLatestQuestions();

    /**
     * Return a list of the highest voted questions
     *
     * @param options Sorting and limiting options
     * @return questions
     */
    @GetMapping("questions/popular")
    //Mono<List<Question>> getPopularQuestions(CollectionOptions options);
    Mono<List<Question>> getPopularQuestions();

    /**
     * Return a list of the highest voted questions without any answers
     *
     * @param options Sorting and limiting options
     * @return questions
     */
    @GetMapping("questions/popularunanswered")
    //Mono<List<Question>> getPopularUnansweredQuestions(CollectionOptions options);
    Mono<List<Question>> getPopularUnansweredQuestions();

    /**
     * Return a list of the questions that had an answer accepted the most recently
     *
     * @param options Sorting and limiting options
     * @return questions
     */
    @GetMapping("questions/recentlyaccepted")
    //Mono<List<Question>> getRecentlyAcceptedQuestions(CollectionOptions options);
    Mono<List<Question>> getRecentlyAcceptedQuestions();

    /**
     * Adds a question and links it to the given userId.
     */
    @PostMapping("questions")
    Mono<Question> createQuestion(@RequestBody Question question);

    /**
     * Acts like a universal search. It will return questions that can be related to the search term.
     */
    @GetMapping("questions")
    //Mono<List<Question>> getQuestionsBySearchQuery(@RequestParam("search") String searchQuery, CollectionOptions options);
    Mono<List<Question>> getQuestionsBySearchQuery(@RequestParam("search") String searchQuery);


    /**
     * Returns all questions for a given user
     */

    @GetMapping("users/{userId}/questions")
    //Mono<List<Question>> getQuestions(@PathVariable long userId, CollectionOptions options);
    Mono<List<Question>> getQuestions(@PathVariable long userId);

    /**
     * Updates the question with the given questionId
     *
     * Only title and question can be updated.
     * Requires the invoker to be the creator of the ques..
     */
    @PutMapping("questions/{questionId}")
    Mono<Question> updateQuestion(@PathVariable long questionId, Question question);

    /**
     * Deletes a question. The answers connected to the question will be deleted as well.
     * @param questionId the question to delete
     */
    @DeleteMapping("questions/{questionId}")
    Mono<Void> deleteQuestion(@PathVariable("questionId") long questionId);


    /**
     * Upvotes (+1) a question.
     * Upvoting an existing downvote will result in a neutral (0) vote.
     * Upvoting an existing upvote will result in error.
     * @param auth
     * @param questionId
     * @return
     */
    @PostMapping("questions/{questionId}/upvote")
    Mono<Void> upVoteQuestion(@PathVariable long questionId);

    /**
     * Downvotes (-1) a question.
     * Downvoting an existing upvote will result in a neutral (0) vote.
     * Downvoting and existing downvote will result in error.
     * @param auth
     * @param questionId
     * @return
     */
    @PostMapping("questions/{questionId}/downvote")
    Mono<Void> downVoteQuestion(@PathVariable("questionId") long questionId);


    /**
     * Return a question if found by id
     *
     * @return question
     */
    Mono<Question> getQuestionById(long questionId);
}