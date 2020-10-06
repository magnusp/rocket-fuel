Scenario: Accepting an answer should also mark question as accepted
Given that a question "Stub question" exists
When "Stub question" gets an answer "Stub answer"
And that the creator of "Stub question" marks "Stub answer" as accepted
Then question "Stub question" should be marked as accepted
And answer "Stub answer" should be marked as accepted
