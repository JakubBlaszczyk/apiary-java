package com.pk.apiary_account;

import java.util.List;

public interface ApiaryAccountRepository {

  List<ApiaryAccount> getAll();

  List<ApiaryAccount> findByIdApiary(Integer id);

  List<ApiaryAccount> findByIdAccount(Integer id);

  ApiaryAccount findByIds(Integer idApiary, Integer idAccount);

  Boolean deleteById(Integer idApiary, Integer idAccount);

  Boolean save(ApiaryAccount event);
}
