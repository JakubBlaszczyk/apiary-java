package com.pk.account;

import java.util.List;

import com.pk.account.request.CreateAccount;
import com.pk.account.request.UpdateAccount;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@org.springframework.stereotype.Controller
public class AccountController {

  private AccountService accountService;

  @PostMapping("/register")
  public Integer registerAccount(CreateAccount account) {
    return accountService.save(account);
  }

  @GetMapping("/register")
  public String getRegisterPage() {
    return "register";
  }

  @PutMapping("/register")
  public Boolean updateAccount(UpdateAccount account) {
    return accountService.update(account);
  }

  @GetMapping("/account")
  public List<Account> getAllAcunts() {
    return accountService.getAll();
  }

  @GetMapping("/account/{username}")
  public Account getAcunts(@PathVariable String username) {
    return accountService.findByLogin(username);
  }
}
