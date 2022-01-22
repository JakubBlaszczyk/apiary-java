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

import com.pk.account.request.Create;
import com.pk.account.request.Update;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class Services {
  private DataSource dataSource;
  private Service accountService;

  @BeforeEach
  public void initialization() {
    this.dataSource = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScripts("schema.sql", "data.sql")
        .build();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    com.pk.account.Repository accountRepository = new Persistent(jdbcTemplate);
    this.accountService = new Service(accountRepository);
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
    this.accountService.getAll().stream().forEach((e) -> {
      System.out.println(e.getId());
    });
    assertNotEquals(template, this.accountService.getAll());
  }

  @Test
  @Order(1)
  public void testFindById() {
    assertNotNull(this.accountService.findById(1));
    assertNotNull(this.accountService.findById(2));
    assertNull(this.accountService.findById(999));
  }

  @Test
  @Order(2)
  public void testSave() {
    assertEquals(this.accountService.getAll().size() + 1, this.accountService
        .save(new Create("test", "test", "test@test.com", Privilege.stringToPrivilege("worker"))));
  }

  @Test
  @Order(3)
  public void testUpdate() {
    Account checker = this.accountService.findById(3);
    assertTrue(this.accountService.update(new Update(3, "updated", null, null)));
    assertEquals("updated", this.accountService.findById(3).getLogin());
    assertEquals(checker.getEmail(), this.accountService.findById(3).getEmail());
  }

  @Test
  @Order(4)
  public void testDeleteById() {
    // need to delete id without any foreign keys
    assertTrue(this.accountService.deleteById(4));
    assertFalse(this.accountService.deleteById(999));
  }
}
