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

import com.pk.account.AccountPersistent;
import com.pk.account.AccountService;
import com.pk.apiary.ApiaryPersistent;
import com.pk.apiary.ApiaryService;
import com.pk.event.EventPersistent;
import com.pk.event.EventService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class TestServices {
  private DataSource dataSource;
  private EventAccountService eventAccountService;

  @BeforeEach
  public void initialization() {
    this.dataSource = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScripts("schema.sql", "data.sql")
        .build();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    EventAccountRepository eventAccountRepository = new EventAccountPersistent(jdbcTemplate);
    this.eventAccountService = new EventAccountService(eventAccountRepository,
        new EventService(new EventPersistent(jdbcTemplate),
            new ApiaryService(new ApiaryPersistent(jdbcTemplate))),
        new AccountService(new AccountPersistent(jdbcTemplate)));
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
    this.eventAccountService.getAll().stream().forEach((e) -> {
      System.out.println(e.getIdAccount() + " " + e.getIdEvent());
    });
    assertNotEquals(template, this.eventAccountService.getAll());
  }

  @Test
  @Order(1)
  public void testFindById() {
    List<EventAccount> template = Collections.emptyList();
    assertNotEquals(template, this.eventAccountService.findByIdEvent(1));
    assertNotEquals(template, this.eventAccountService.findByIdAccount(2));
    assertNotNull(this.eventAccountService.findByIds(1, 3));
    assertEquals(template, this.eventAccountService.findByIdEvent(999));
    assertEquals(template, this.eventAccountService.findByIdAccount(999));
    assertNull(this.eventAccountService.findByIds(999, 999));
  }

  @Test
  @Order(2)
  public void testSave() {
    assertTrue(this.eventAccountService
        .save(new EventAccount(2, 3)));
  }

  @Test
  @Order(3)
  public void testDeleteById() {
    assertTrue(this.eventAccountService.deleteById(2, 2));
    assertFalse(this.eventAccountService.deleteById(999, 999));
  }
}
