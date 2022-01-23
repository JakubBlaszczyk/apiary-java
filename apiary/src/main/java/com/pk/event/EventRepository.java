package com.pk.event;

import java.util.List;

import com.pk.event.request.EventCreate;
import com.pk.event.request.EventUpdate;

public interface EventRepository {

  List<Event> getAll();

  Event findById(Integer id);

  Boolean deleteById(Integer id);

  Boolean update(EventUpdate event);

  Integer save(EventCreate event);
}
