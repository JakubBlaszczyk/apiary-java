package com.pk.event_account;

import java.util.List;

import com.pk.account.AccountService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class EventAccountService {
  private EventAccountRepository eventAccountRepository;
  private com.pk.event.EventService eventService;
  private AccountService accountService;

  public List<EventAccount> getAll() {
    return eventAccountRepository.getAll();
  }

  public List<EventAccount> findByIdEvent(Integer id) {
    return eventAccountRepository.findByIdEvent(id);
  }

  public List<EventAccount> findByIdAccount(Integer id) {
    return eventAccountRepository.findByIdAccount(id);
  }

  public EventAccount findByIds(Integer idEvent, Integer idAccount) {
    return eventAccountRepository.findByIds(idEvent, idAccount);
  }

  public Boolean deleteByObject(EventAccount event) {
    return eventAccountRepository.deleteById(event.getIdEvent(), event.getIdAccount());
  }

  public Boolean deleteById(Integer idEvent, Integer idAccount) {
    return eventAccountRepository.deleteById(idEvent, idAccount);
  }

  public Boolean save(EventAccount eventAccount) {
    if (eventService.findById(eventAccount.getIdEvent()) == null) {
      log.warn("Event doesn't found");
      return false;
    }

    if (accountService.findById(eventAccount.getIdAccount()) == null) {
      log.warn("Account doesn't found");
      return false;
    }

    return eventAccountRepository.save(eventAccount);
  }
}
