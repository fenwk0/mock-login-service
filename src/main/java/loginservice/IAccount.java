package loginservice;

public interface IAccount {
	void setLoggedIn(boolean value);
	boolean passwordMatches(String candidate);
	void setRevoked(boolean b);
	boolean isLoggedIn();
}
