package se.fortnox.rocketfuel.document;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateQuestion {
    String title;
    String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    @JsonProperty("question")
    public void setBody(String body) {
        this.body = body;
    }
}
