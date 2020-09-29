package se.fortnox.rocketfuel.api;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping(path="api")
public interface QuestionResource {

    /**
     * Returns a specific question to the client
     */
    @GetMapping("questions/{questionId}")
    Mono<QuestionDocument> getQuestion(@PathVariable long questionId);

    /**
     * Return a list of latest questions
     *
     * @param options Sorting and limiting options
     * @return questions
     */
    @GetMapping("questions/latest")
    //Mono<List<Question>> getLatestQuestions(CollectionOptions options);
    Mono<List<QuestionDocument>> getLatestQuestions();

    /**
     * Return a list of the highest voted questions
     *
     * @param options Sorting and limiting options
     * @return questions
     */
    @GetMapping("questions/popular")
    //Mono<List<Question>> getPopularQuestions(CollectionOptions options);
    Mono<List<QuestionDocument>> getPopularQuestions();

    /**
     * Return a list of the highest voted questions without any answers
     *
     * @param options Sorting and limiting options
     * @return questions
     */
    @GetMapping("questions/popularunanswered")
    //Mono<List<Question>> getPopularUnansweredQuestions(CollectionOptions options);
    Mono<List<QuestionDocument>> getPopularUnansweredQuestions();

    /**
     * Return a list of the questions that had an answer accepted the most recently
     *
     * @param options Sorting and limiting options
     * @return questions
     */
    @GetMapping("questions/recentlyaccepted")
    //Mono<List<Question>> getRecentlyAcceptedQuestions(CollectionOptions options);
    Mono<List<QuestionDocument>> getRecentlyAcceptedQuestions();

    /**
     * Adds a question and links it to the given userId.
     * @param doc
     */
    @PostMapping("questions")
    public Mono<Void>createQuestion(@RequestBody QuestionDocument questionDocument, ServerHttpResponse response);

    /**
     * Acts like a universal search. It will return questions that can be related to the search term.
     */
    @GetMapping("questions")
    //Mono<List<Question>> getQuestionsBySearchQuery(@RequestParam("search") String searchQuery, CollectionOptions options);
    Mono<List<QuestionDocument>> getQuestionsBySearchQuery(@RequestParam("search") String searchQuery);


    /**
     * Returns all questions for a given user
     */

    @GetMapping("users/{userId}/questions")
    //Mono<List<Question>> getQuestions(@PathVariable long userId, CollectionOptions options);
    Mono<List<QuestionDocument>> getQuestions(@PathVariable long userId);

    /**
     * Updates the question with the given questionId
     *
     * Only title and question can be updated.
     * Requires the invoker to be the creator of the ques..
     */
    @PutMapping("questions/{questionId}")
    Mono<QuestionDocument> updateQuestion(@PathVariable long questionId, QuestionDocument questionDocument);

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
    Mono<QuestionDocument> getQuestionById(long questionId);
}
