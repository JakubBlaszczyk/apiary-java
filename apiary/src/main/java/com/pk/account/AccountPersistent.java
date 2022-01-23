package com.pk.account;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import com.pk.account.request.CreateAccount;
import com.pk.account.request.UpdateAccount;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Repository
public class AccountPersistent implements AccountRepository {

  private static final String EXCEPTION_MESSAGE = "Exception in account persistent";
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<Account> getAll() {
    try {
      return jdbcTemplate.query("SELECT * FROM ACCOUNT",
          (rs, rowNum) -> new Account(
              rs.getInt(1),
              rs.getString(2),
              rs.getString(3),
              rs.getString(4),
              Privilege.stringToPrivilege(rs.getString(5))));
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return Collections.emptyList();
    }
  }

  @Override
  public Account findByLogin(String login) {
    try {
      List<Account> accounts = jdbcTemplate.query(
          "SELECT * FROM ACCOUNT WHERE LOGIN = ?",
          (rs, rowNum) -> new Account(
              rs.getInt(1),
              rs.getString(2),
              rs.getString(3),
              rs.getString(4),
              Privilege.stringToPrivilege(rs.getString(5))),
          login);

      if (accounts.isEmpty()) {
        log.error("No accounts found");
        return null;
      } else if (accounts.size() > 1) {
        log.error("Found more than one account");
        return null;
      }
      return accounts.get(0);
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return null;
    }
  }

  @Override
  public Account findById(Integer id) {
    try {
      List<Account> accounts = jdbcTemplate.query(
          "SELECT * FROM ACCOUNT WHERE ID = ?",
          (rs, rowNum) -> new Account(
              rs.getInt(1),
              rs.getString(2),
              rs.getString(3),
              rs.getString(4),
              Privilege.stringToPrivilege(rs.getString(5))),
          id);

      if (accounts.isEmpty()) {
        log.error("No accounts found");
        return null;
      } else if (accounts.size() > 1) {
        log.error("Found more than one account");
        return null;
      }
      return accounts.get(0);
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return null;
    }
  }

  @Override
  public Boolean deleteById(Integer id) {
    try {
      return jdbcTemplate.update(
          "DELETE FROM ACCOUNT WHERE ID = ?", id) > 0;
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return false;
    }
  }

  @Override
  public Boolean update(UpdateAccount account) {
    try {
      return jdbcTemplate.update("UPDATE ACCOUNT SET LOGIN = ?, PASSWORD = ?, EMAIL = ? WHERE ID = ?",
          account.getLogin(),
          account.getPassword(),
          account.getEmail(),
          account.getId()) > 0;
    } catch (Exception e) {
      log.error(EXCEPTION_MESSAGE, e);
      return false;
    }
  }

  @Override
  public Integer save(CreateAccount account) {
    String statement = "INSERT INTO ACCOUNT(LOGIN, PASSWORD, EMAIL, PRIVILEGE) VALUES(?, ?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    try {
      jdbcTemplate.update(connection -> {
        PreparedStatement prepState = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
        prepState.setString(1, account.getLogin());
        prepState.setString(2, account.getPassword());
        prepState.setString(3, account.getEmail());
        prepState.setObject(4, account.getPrivilege());
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
