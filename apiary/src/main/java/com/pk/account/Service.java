package com.pk.account;

import java.util.List;

import com.pk.account.request.Create;
import com.pk.account.request.Update;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service
@AllArgsConstructor
@Slf4j
public class Service {
  private Repository accountRepository;

  public List<Account> getAll() {
    return accountRepository.getAll();
  }

  public Account findById(Integer id) {
    return accountRepository.findById(id);
  }

  public Boolean deleteById(Integer id) {
    return accountRepository.deleteById(id);
  }

  public Boolean update(Update account) {
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

  public Integer save(Create account) {
    return accountRepository.save(account);
  }
}
