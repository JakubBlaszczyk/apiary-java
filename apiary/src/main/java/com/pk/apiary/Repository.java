package com.pk.apiary;

import java.util.List;

import com.pk.apiary.request.Create;

public interface Repository {

  List<Apiary> getAll();

  Apiary findById(Integer id);

  Boolean deleteById(Integer id);

  Boolean update(Apiary apiary);

  Integer save(Create apiary);
}
