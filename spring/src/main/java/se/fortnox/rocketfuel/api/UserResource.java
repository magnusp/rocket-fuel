 package se.fortnox.rocketfuel.api;

 import org.springframework.http.server.reactive.ServerHttpResponse;
 import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
 import org.springframework.web.bind.annotation.CookieValue;
 import org.springframework.web.bind.annotation.DeleteMapping;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import reactor.core.publisher.Mono;

 import javax.validation.constraints.NotNull;
 import java.security.Principal;

 @RequestMapping(path="/api/users")
 public interface UserResource {

     /**
      * returns the current signed in user.
      *
      * @param auth
      * @return
      */
     @GetMapping("me")
     Mono<UserDocument> getCurrent(Principal principal);
     /**
      * Creates a user
      * @param userDocument user that will be created/updated.
      * @return
      */
     @PostMapping
     Mono<Integer> createUser(UserDocument userDocument);

     /**
      * Returns the user by email
      * @param email that belongs to the user
      * @return
      */
     @GetMapping("email/{email}")
     Mono<UserDocument> getUserByEmail(@NotNull @PathVariable String email, @RequestParam boolean createIfMissing);

     /**
      * Returns the user by userId
      * @param userId that belongs to the user
      * @return
      */
     @GetMapping("id/{userId}")
     Mono<UserDocument> getUserById(@PathVariable long userId);

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
     Mono<UserDocument> signIn(JwtAuthenticationToken authenticationToken, ServerHttpResponse serverHttpResponse);

     /**
      * Signs out the user by telling client that the cookie shall be removed and is invalid.
      *
      * Set-Cookie header is used to perform the action.
      *
      * @return the userId
      * @param serverHttpResponse
      */
     @DeleteMapping("authenticate")
     Mono<Long> signOut(@CookieValue String applicationToken, ServerHttpResponse serverHttpResponse);
 }
