package com.pk.account;

import java.util.List;

import com.pk.account.request.Create;
import com.pk.account.request.Update;

public interface Repository {

  List<Account> getAll();

  Account findById(Integer id);

  Boolean deleteById(Integer id);

  Boolean update(Update account);

  Integer save(Create account);
}
