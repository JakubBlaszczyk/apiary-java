package com.pk.account;

import java.util.List;

import com.pk.account.request.CreateAccount;
import com.pk.account.request.UpdateAccount;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service
@AllArgsConstructor
@Slf4j
public class AccountService {
  private AccountRepository accountRepository;

  public List<Account> getAll() {
    return accountRepository.getAll();
  }

  public Account findById(Integer id) {
    return accountRepository.findById(id);
  }

  public Account findByLogin(String login) {
    return accountRepository.findByLogin(login);
  }

  public Boolean deleteById(Integer id) {
    return accountRepository.deleteById(id);
  }

  public Boolean update(UpdateAccount account) {
    Account temp = accountRepository.findById(account.getId());
    if (temp == null) {
      log.warn("Account doesn't exist");
      return false;
    }

    if (account.getEmail() == null || account.getEmail().isBlank()) {
      account.setEmail(temp.getEmail());
    }

    if (account.getLogin() == null || account.getLogin().isBlank()) {
      account.setLogin(temp.getLogin());
    }

    if (account.getPassword() == null || account.getPassword().isBlank()) {
      account.setPassword(temp.getPassword());
    }

    return accountRepository.update(account);
  }

  public Integer save(CreateAccount account) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    account.setPassword(encoder.encode(account.getPassword()));
    return accountRepository.save(account);
  }
}
