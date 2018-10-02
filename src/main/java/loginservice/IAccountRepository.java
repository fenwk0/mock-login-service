package loginservice;

public interface IAccountRepository {
    IAccount find(String accountId);
}
