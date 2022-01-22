package com.pk.event;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import com.pk.event.request.Create;
import com.pk.event.request.Update;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class Persistent implements Repository {

  private static final String EXCEPTION_MESSAGE = "Exception in event persistent";
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<Event> getAll() {
    try {
      return jdbcTemplate.query("SELECT * FROM \"EVENT\"",
          (rs, rowNum) -> new Event(
              rs.getInt(1),
              rs.getInt(2),
              rs.getString(3) != null ? Timestamp.valueOf(rs.getString(3)) : null,
              rs.getString(4) != null ? Timestamp.valueOf(rs.getString(4)) : null,
              rs.getString(5)));
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return Collections.emptyList();
    }
  }

  @Override
  public Event findById(Integer id) {
    try {
      List<Event> events = jdbcTemplate.query(
          "SELECT * FROM EVENT WHERE ID = ?",
          (rs, rowNum) -> new Event(
              rs.getInt(1),
              rs.getInt(2),
              rs.getString(3) != null ? Timestamp.valueOf(rs.getString(3)) : null,
              rs.getString(4) != null ? Timestamp.valueOf(rs.getString(4)) : null,
              rs.getString(5)),
          id);

      if (events.isEmpty()) {
        log.error("No events found");
        return null;
      } else if (events.size() > 1) {
        log.error("Found more than one ");
        return null;
      }
      return events.get(0);
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return null;
    }
  }

  @Override
  public Boolean deleteById(Integer id) {
    try {
      return jdbcTemplate.update(
          "DELETE FROM EVENT WHERE ID = ?", id) > 0;
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return false;
    }
  }

  @Override
  public Boolean update(Update event) {
    try {
      return jdbcTemplate.update("UPDATE EVENT SET ID_APIARY = ?, TIME_START = ?, TIME_END = ?, NOTE = ? WHERE ID = ?",
          event.getIdApiary(),
          event.getStart(),
          event.getEnd(),
          event.getNote(),
          event.getId()) > 0;
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return false;
    }
  }

  @Override
  public Integer save(Create event) {
    String statement = "INSERT INTO EVENT(ID_APIARY, TIME_START, TIME_END, NOTE) VALUES(?, ?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    try {
      jdbcTemplate.update(connection -> {
        PreparedStatement prepState = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
        prepState.setInt(1, event.getIdApiary());
        prepState.setTimestamp(2, event.getStart());
        prepState.setTimestamp(3, event.getEnd());
        prepState.setString(4, event.getNote());
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
