package com.pk.apiary_account;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@org.springframework.stereotype.Controller
public class ApiaryAccountController {

  private ApiaryAccountService apiaryAccountService;

  @GetMapping("/ApiaryAccount")
  public String getPage() {
    return "apiary_account";
  }

  @PostMapping("/ApiaryAccount")
  public Boolean connectApiaryAccount(ApiaryAccount apiaryAccount) {
    return apiaryAccountService.save(apiaryAccount);
  }

  @DeleteMapping("/ApiaryAccount")
  public Boolean deleteApiaryAccount(ApiaryAccount apiaryAccount) {
    return apiaryAccountService.deleteByObject(apiaryAccount);
  }

  @GetMapping("/ApiaryAccounts")
  public List<ApiaryAccount> getApiaryAccounts() {
    return apiaryAccountService.getAll();
  }

  @GetMapping("/ApiaryAccount/specific")
  public ApiaryAccount getApiaryAccount(ApiaryAccount apiaryAccount) {
    return apiaryAccountService.findByIds(apiaryAccount.getIdApiary(), apiaryAccount.getIdAccount());
  }
}
