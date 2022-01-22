package com.pk.event_account;

import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class Persistent implements Repository {

  /**
   *
   */
  private static final String NO_EVENT_ACCOUNTS_FOUND = "No event accounts found";
  private static final String EXCEPTION_MESSAGE = "Exception in event account persistent";
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<EventAccount> getAll() {
    try {
      return jdbcTemplate.query("SELECT * FROM EVENT_ACCOUNT",
          (rs, rowNum) -> new EventAccount(
              rs.getInt(1),
              rs.getInt(2)));
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return Collections.emptyList();
    }
  }

  @Override
  public List<EventAccount> findByIdEvent(Integer id) {
    try {
      List<EventAccount> events = jdbcTemplate.query(
          "SELECT * FROM EVENT_ACCOUNT WHERE ID_EVENT = ?",
          (rs, rowNum) -> new EventAccount(
              rs.getInt(1),
              rs.getInt(2)),
          id);

      if (events.isEmpty()) {
        log.error(NO_EVENT_ACCOUNTS_FOUND);
        return Collections.emptyList();
      }
      return events;
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return Collections.emptyList();
    }
  }

  @Override
  public List<EventAccount> findByIdAccount(Integer id) {
    try {
      List<EventAccount> events = jdbcTemplate.query(
          "SELECT * FROM EVENT_ACCOUNT WHERE ID_ACCOUNT = ?",
          (rs, rowNum) -> new EventAccount(
              rs.getInt(1),
              rs.getInt(2)),
          id);

      if (events.isEmpty()) {
        log.error(NO_EVENT_ACCOUNTS_FOUND);
        return Collections.emptyList();
      }
      return events;
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return Collections.emptyList();
    }
  }

  @Override
  public EventAccount findByIds(Integer idEvent, Integer idAccount) {
    try {
      List<EventAccount> events = jdbcTemplate.query(
          "SELECT * FROM EVENT_ACCOUNT WHERE ID_EVENT = ? AND ID_ACCOUNT = ?",
          (rs, rowNum) -> new EventAccount(
              rs.getInt(1),
              rs.getInt(2)),
          idEvent, idAccount);

      if (events.isEmpty()) {
        log.error(NO_EVENT_ACCOUNTS_FOUND);
        return null;
      } else if (events.size() > 1) {
        log.error("Found more than one");
        return null;
      }
      return events.get(0);
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return null;
    }
  }

  @Override
  public Boolean deleteById(Integer idEvent, Integer idAccount) {
    try {
      return jdbcTemplate.update(
          "DELETE FROM EVENT_ACCOUNT WHERE ID_EVENT = ? AND ID_ACCOUNT = ?", idEvent, idAccount) > 0;
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return false;
    }
  }

  @Override
  public Boolean save(EventAccount event) {
    try {
      return jdbcTemplate.update("INSERT INTO EVENT_ACCOUNT(ID_EVENT, ID_ACCOUNT) VALUES(?, ?)",
          event.getIdEvent(),
          event.getIdAccount()) > 0;
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return false;
    }
  }
}
