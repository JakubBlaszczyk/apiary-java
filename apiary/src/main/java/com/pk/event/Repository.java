package com.pk.event;

import java.util.List;

import com.pk.event.request.Create;
import com.pk.event.request.Update;

public interface Repository {

  List<Event> getAll();

  Event findById(Integer id);

  Boolean deleteById(Integer id);

  Boolean update(Update event);

  Integer save(Create event);
}
