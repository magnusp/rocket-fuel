 package se.fortnox.rocketfuel.api;

 import org.springframework.web.bind.annotation.DeleteMapping;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestAttribute;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import reactor.core.publisher.Mono;

 import javax.validation.constraints.NotNull;

 @RequestMapping(path="/api/users")
 public interface UserResource {

     /**
      * returns the current signed in user.
      *
      * @param auth
      * @return
      */
     @GetMapping("me")
     Mono<User> getCurrent();
     /**
      * Creates a user
      * @param user user that will be created/updated.
      * @return
      */
     @PostMapping
     Mono<Integer> createUser(User user);

     /**
      * Returns the user by email
      * @param email that belongs to the user
      * @return
      */
     @GetMapping("email/{email}")
     Mono<User> getUserByEmail(@NotNull @PathVariable String email, @RequestParam boolean createIfMissing);

     /**
      * Returns the user by userId
      * @param userId that belongs to the user
      * @return
      */
     @GetMapping("id/{userId}")
     Mono<User> getUserById(@PathVariable long userId);

     /**
      * Returns a application token, that shall be used when the client uses endpoints
      * that needs authentication. The token contains valuable information about the user.
      *
      * A cookie named application will be returned in the request, containing the token, so that
      * further requests will be authenticated, if the client is a browser.
      * @param authorizationToken a OpenId jwt token
      * @return a application token as a jwt
      */
     @PostMapping("authenticate")
     Mono<User> signIn(@RequestAttribute("foo") User requestAttribute);

     /**
      * Signs out the user by telling client that the cookie shall be removed and is invalid.
      *
      * Set-Cookie header is used to perform the action.
      *
      * @return the userId
      */
     @DeleteMapping("authenticate")
     Mono<Long> signOut();
 }
