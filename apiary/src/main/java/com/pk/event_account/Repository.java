package com.pk.event_account;

import java.util.List;

public interface Repository {

  List<EventAccount> getAll();

  List<EventAccount> findByIdEvent(Integer id);

  List<EventAccount> findByIdAccount(Integer id);

  EventAccount findByIds(Integer idEvent, Integer idAccount);

  Boolean deleteById(Integer idEvent, Integer idAccount);

  Boolean save(EventAccount event);
}
