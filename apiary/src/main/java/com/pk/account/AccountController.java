package com.pk.account;

import java.util.List;

import com.pk.account.request.CreateAccount;
import com.pk.account.request.UpdateAccount;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Controller
@Slf4j
public class AccountController {

  private AccountService accountService;

  @GetMapping("/login")
  public String logIn() {
    return "LogIn";
  }

  @GetMapping("/register")
  public String logInRedirect() {
    return logIn();
  }

  @PostMapping("/register")
  public String registerAccount(CreateAccount account) {
    accountService.save(account);
    return logIn();
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
  public String updateAccount(UpdateAccount account) {
    account.setId(accountService.findByLogin(getCallersUsername()).getId());
    log.info("{} {} {} {}", account.getId(), account.getLogin(), account.getEmail(), account.getPassword(), getCallersUsername());
    accountService.update(account);
    return "ManageAccount";
  }

  @GetMapping("/account/list")
  public @ResponseBody List<Account> getAllAcunts() {
    return accountService.getAll();
  }

  @GetMapping("/account/list/{username}")
  public @ResponseBody Account getAccounts(@PathVariable String username) {
    return accountService.findByLogin(username);
  }

  private String getCallersUsername() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      return ((UserDetails) principal).getUsername();
    } else {
      return principal.toString();
    }
  }
}
