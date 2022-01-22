package com.pk.apiary;

import java.util.List;

import com.pk.apiary.request.ApiaryCreate;

public interface ApiaryRepository {

  List<Apiary> getAll();

  Apiary findById(Integer id);

  Boolean deleteById(Integer id);

  Boolean update(Apiary apiary);

  Integer save(ApiaryCreate apiary);
}
