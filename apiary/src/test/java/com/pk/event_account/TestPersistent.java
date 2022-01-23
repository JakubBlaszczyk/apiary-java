package com.pk.event_account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class TestPersistent {
  private DataSource dataSource;
  private EventAccountRepository eventAccountRepository;

  @BeforeEach
  public void initialization() {
    this.dataSource = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScripts("schema.sql", "data.sql")
        .build();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    this.eventAccountRepository = new EventAccountPersistent(jdbcTemplate);
  }

  @AfterEach
  public void cleanup() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    jdbcTemplate.execute("DROP ALL OBJECTS");
  }

  @Test
  @Order(0)
  public void testGetAll() {
    List<EventAccount> template = Collections.emptyList();
    this.eventAccountRepository.getAll().stream().forEach((e) -> {
      System.out.println(e.getIdAccount() + " " + e.getIdEvent());
    });
    assertNotEquals(template, this.eventAccountRepository.getAll());
  }

  @Test
  @Order(1)
  public void testFindById() {
    List<EventAccount> template = Collections.emptyList();
    assertNotEquals(template, this.eventAccountRepository.findByIdEvent(1));
    assertNotEquals(template, this.eventAccountRepository.findByIdAccount(2));
    assertNotNull(this.eventAccountRepository.findByIds(1, 3));
    assertEquals(template, this.eventAccountRepository.findByIdEvent(999));
    assertEquals(template, this.eventAccountRepository.findByIdAccount(999));
    assertNull(this.eventAccountRepository.findByIds(999, 999));
  }

  @Test
  @Order(2)
  public void testSave() {
    assertTrue(this.eventAccountRepository
        .save(new EventAccount(2, 1)));
  }

  @Test
  @Order(3)
  public void testDeleteById() {
    assertTrue(this.eventAccountRepository.deleteById(2, 2));
    assertFalse(this.eventAccountRepository.deleteById(999, 999));
  }
}
