package loginservice;

public class LoginService {
	private final IAccountRepository accountRepository;
	private int failedAttempts = 0;

	public LoginService(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public void login(String accountId, String password) {
		IAccount account = accountRepository.find(accountId);

		// if (account.passwordMatches(password))
		// account.setLoggedIn(true);
		if (account.passwordMatches(password)) {
			if (account.isLoggedIn())
				throw new AccountLoginLimitReachedException();
			account.setLoggedIn(true);
		} else
			++failedAttempts;
		if (failedAttempts == 3)
			account.setRevoked(true);
	}

}
