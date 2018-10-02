package test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import loginservice.AccountLoginLimitReachedException;
import loginservice.IAccount;
import loginservice.IAccountRepository;
import loginservice.LoginService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
 
public class LoginServiceTest {
   @Mock
   private IAccount account;
   @Mock
   private IAccountRepository accountRepository;
   private LoginService service;
 
   @Before
   public void init() {
      account = mock(IAccount.class);
      accountRepository = mock(IAccountRepository.class);
      when(accountRepository.find(anyString())).thenReturn(account);
      service = new LoginService(accountRepository);
   }
 
   private void willPasswordMatch(boolean value) {
      when(account.passwordMatches(anyString())).thenReturn(value);
   }
 
   @Test
   public void itShouldSetAccountToLoggedInWhenPasswordMatches() {
      willPasswordMatch(true);
      service.login("brett", "password");
      verify(account, times(1)).setLoggedIn(true);
   }
 
   @Test
   public void itShouldNotSetAccountLoggedInIfPasswordDoesNotMatch() {
      willPasswordMatch(false);
      service.login("brett", "password");
      verify(account, never()).setLoggedIn(true);
   }
   
   @Test(expected = AccountLoginLimitReachedException.class)
   public void itShouldNowAllowConcurrentLogins() {
      willPasswordMatch(true);
      when(account.isLoggedIn()).thenReturn(true);
      service.login("brett", "password");
   }
   
}