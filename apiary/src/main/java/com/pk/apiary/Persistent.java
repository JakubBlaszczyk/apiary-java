package com.pk.apiary;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import com.pk.apiary.request.Create;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class Persistent implements Repository {

  private static final String EXCEPTION_MESSAGE = "Exception in apiary persistent";
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<Apiary> getAll() {
    try {
      return jdbcTemplate.query("SELECT * FROM APIARY",
          (rs, rowNum) -> new Apiary(
              rs.getInt(1),
              rs.getString(2),
              rs.getString(3)));
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return Collections.emptyList();
    }
  }

  @Override
  public Apiary findById(Integer id) {
    try {
      List<Apiary> apiaries = jdbcTemplate.query(
          "SELECT * FROM APIARY WHERE ID = ?",
          (rs, rowNum) -> new Apiary(
              rs.getInt(1),
              rs.getString(2),
              rs.getString(3)),
          id);

      if (apiaries.isEmpty()) {
        log.error("No apiaries found");
        return null;
      } else if (apiaries.size() > 1) {
        log.error("Found more than one apiary");
        return null;
      }
      return apiaries.get(0);
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return null;
    }
  }

  @Override
  public Boolean deleteById(Integer id) {
    try {
      return jdbcTemplate.update(
          "DELETE FROM APIARY WHERE ID = ?", id) > 0;
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return false;
    }
  }

  @Override
  public Boolean update(Apiary apiary) {
    try {
      return jdbcTemplate.update("UPDATE APIARY SET LOCALIZATION = ?, INFORMATION = ? WHERE ID = ?",
          apiary.getLocalization(),
          apiary.getInformation(),
          apiary.getId()) > 0;
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return false;
    }
  }

  @Override
  public Integer save(Create account) {
    String statement = "INSERT INTO APIARY(LOCALIZATION, INFORMATION) VALUES(?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    try {
      jdbcTemplate.update(connection -> {
        PreparedStatement prepState = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
        prepState.setString(1, account.getLocalization());
        prepState.setString(2, account.getInformation());
        return prepState;
      }, keyHolder);
      Number getKey = keyHolder.getKey();
      return getKey != null ? getKey.intValue() : null;
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return null;
    }
  }
}
