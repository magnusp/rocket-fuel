import api.Auth;
import org.mockito.Mock;

public class MockAuth extends Auth {

	public MockAuth(long userId, String email) {
		this.setUserId(userId);
		this.setEmail(email);
	}

	public MockAuth(long userId) {
		this.setUserId(userId);
	}
}
