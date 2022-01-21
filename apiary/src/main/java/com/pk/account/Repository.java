package com.pk.account;

import java.util.List;

public interface Repository {

  List<Account> getAll();

  Account findById(Integer id);

  Boolean deleteById(Integer id);

  Boolean update(Account account);

  Integer save(Account account);
}
