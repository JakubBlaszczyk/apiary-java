package com.pk.account;

import java.util.List;

import com.pk.account.request.CreateAccount;
import com.pk.account.request.UpdateAccount;

public interface AccountRepository {

  List<Account> getAll();

  Account findById(Integer id);

  Account findByLogin(String login);

  Boolean deleteById(Integer id);

  Boolean update(UpdateAccount account);

  Integer save(CreateAccount account);
}
