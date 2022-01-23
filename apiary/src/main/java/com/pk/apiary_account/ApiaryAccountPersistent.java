package com.pk.apiary_account;

import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Repository
public class ApiaryAccountPersistent implements ApiaryAccountRepository {

  private static final String NO_EVENT_ACCOUNTS_FOUND = "No apiary accounts found";
  private static final String EXCEPTION_MESSAGE = "Exception in apiary account persistent";
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<ApiaryAccount> getAll() {
    try {
      return jdbcTemplate.query("SELECT * FROM APIARY_ACCOUNT",
          (rs, rowNum) -> new ApiaryAccount(
              rs.getInt(1),
              rs.getInt(2)));
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return Collections.emptyList();
    }
  }

  @Override
  public List<ApiaryAccount> findByIdApiary(Integer id) {
    try {
      List<ApiaryAccount> events = jdbcTemplate.query(
        "SELECT * FROM APIARY_ACCOUNT WHERE ID_APIARY = ?",
          (rs, rowNum) -> new ApiaryAccount(
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
  public List<ApiaryAccount> findByIdAccount(Integer id) {
    try {
      List<ApiaryAccount> events = jdbcTemplate.query(
        "SELECT * FROM APIARY_ACCOUNT WHERE ID_ACCOUNT = ?",
          (rs, rowNum) -> new ApiaryAccount(
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
  public ApiaryAccount findByIds(Integer idApiary, Integer idAccount) {
    try {
      List<ApiaryAccount> events = jdbcTemplate.query(
        "SELECT * FROM APIARY_ACCOUNT WHERE ID_APIARY = ? AND ID_ACCOUNT = ?",
          (rs, rowNum) -> new ApiaryAccount(
              rs.getInt(1),
              rs.getInt(2)),
          idApiary, idAccount);

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
  public Boolean deleteById(Integer idApiary, Integer idAccount) {
    try {
      return jdbcTemplate.update(
        "DELETE FROM APIARY_ACCOUNT WHERE ID_APIARY = ? AND ID_ACCOUNT = ?", idApiary, idAccount) > 0;
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return false;
    }
  }

  @Override
  public Boolean save(ApiaryAccount event) {
    try {
      return jdbcTemplate.update("INSERT INTO APIARY_ACCOUNT(ID_APIARY, ID_ACCOUNT) VALUES(?, ?)",
          event.getIdApiary(),
          event.getIdAccount()) > 0;
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return false;
    }
  }
}
