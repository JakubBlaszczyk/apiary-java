package com.pk.apiary_account;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service
@AllArgsConstructor
@Slf4j
public class ApiaryAccountService {
  private ApiaryAccountRepository apiaryAccountRepository;
  private com.pk.apiary.ApiaryService apiaryService;
  private com.pk.account.AccountService accountService;

  public List<ApiaryAccount> getAll() {
    return apiaryAccountRepository.getAll();
  }

  public List<ApiaryAccount> findByIdApiary(Integer id) {
    return apiaryAccountRepository.findByIdApiary(id);
  }

  public List<ApiaryAccount> findByIdAccount(Integer id) {
    return apiaryAccountRepository.findByIdAccount(id);
  }

  public ApiaryAccount findByIds(Integer idApiary, Integer idAccount) {
    return apiaryAccountRepository.findByIds(idApiary, idAccount);
  }

  public Boolean deleteByObject(ApiaryAccount event) {
    return apiaryAccountRepository.deleteById(event.getIdApiary(), event.getIdAccount());
  }

  public Boolean deleteById(Integer idApiary, Integer idAccount) {
    return apiaryAccountRepository.deleteById(idApiary, idAccount);
  }

  public Boolean save(ApiaryAccount apiaryAccount) {
    if (apiaryService.findById(apiaryAccount.getIdApiary()) == null) {
      log.warn("Event doesn't found");
      return false;
    }

    if (accountService.findById(apiaryAccount.getIdAccount()) == null) {
      log.warn("Account doesn't found");
      return false;
    }

    return apiaryAccountRepository.save(apiaryAccount);
  }
}
