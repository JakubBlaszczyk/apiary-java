package com.pk.account;

import java.util.List;

import com.pk.account.request.CreateAccount;
import com.pk.account.request.UpdateAccount;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class AccountController {

  private AccountService accountService;

  @GetMapping("/login")
  public String logIn() {
    return "LogIn";
  }

  @PostMapping("/register")
  public @ResponseBody Integer registerAccount(CreateAccount account) {
    return accountService.save(account);
  }

  @GetMapping("/accounts")
  public String getRegisterPage(Model model) {
    model.addAttribute("accounts", this.accountService.getAll());
    return "Users";
  }

  @GetMapping("/account")
  public String getAccountPage() {
    return "ManageAccount";
  }

  @PutMapping("/register")
  public @ResponseBody Boolean updateAccount(UpdateAccount account) {
    return accountService.update(account);
  }

  @GetMapping("/account/list")
  public @ResponseBody List<Account> getAllAcunts() {
    return accountService.getAll();
  }

  @GetMapping("/account/list/{username}")
  public @ResponseBody Account getAccounts(@PathVariable String username) {
    return accountService.findByLogin(username);
  }
}
