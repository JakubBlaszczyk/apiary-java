package com.pk.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import com.pk.account.request.CreateAccount;
import com.pk.account.request.UpdateAccount;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class TestPersistent {
  private DataSource dataSource;
  private AccountRepository accountRepository;

  @BeforeEach
  public void initialization() {
    this.dataSource = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScripts("schema.sql", "data.sql")
        .build();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    this.accountRepository = new AccountPersistent(jdbcTemplate);
  }

  @AfterEach
  public void cleanup() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    jdbcTemplate.execute("DROP ALL OBJECTS");
  }

  @Test
  @Order(0)
  public void testGetAll() {
    List<Account> template = Collections.emptyList();
    this.accountRepository.getAll().stream().forEach((e) -> {
      System.out.println(e.getId());
    });
    assertNotEquals(template, this.accountRepository.getAll());
  }

  @Test
  @Order(1)
  public void testFindById() {
    assertNotNull(this.accountRepository.findById(1));
    assertNotNull(this.accountRepository.findById(2));
    assertNull(this.accountRepository.findById(999));
  }

  @Test
  @Order(2)
  public void testFindByLogin() {
    assertNotNull(this.accountRepository.findByLogin("admin"));
    assertNotNull(this.accountRepository.findByLogin("user1"));
    assertNull(this.accountRepository.findByLogin("deadbeef"));
  }

  @Test
  @Order(3)
  public void testSave() {
    assertEquals(this.accountRepository.getAll().size() + 1, this.accountRepository
        .save(new CreateAccount("test", "test", "test@test.com", Privilege.privilegeToString(Privilege.stringToPrivilege("worker")))));
  }

  @Test
  @Order(4)
  public void testUpdate() {
    assertTrue(this.accountRepository.update(new UpdateAccount(3, "updated", "updated", "updated@updated.com")));
    assertEquals("updated", this.accountRepository.findById(3).getLogin());
  }

  @Test
  @Order(5)
  public void testDeleteById() {
    // need to delete id without any foreign keys restrictions
    assertTrue(this.accountRepository.deleteById(5));
    assertFalse(this.accountRepository.deleteById(999));
  }
}
