package com.pk.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import com.pk.event.request.Create;
import com.pk.event.request.Update;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class Services {
  private DataSource dataSource;
  private Service eventService;

  @BeforeEach
  public void initialization() {
    this.dataSource = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScripts("schema.sql", "data.sql")
        .build();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    Repository eventRepository = new Persistent(jdbcTemplate);
    this.eventService = new Service(eventRepository);
  }

  @AfterEach
  public void cleanup() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    jdbcTemplate.execute("DROP ALL OBJECTS");
  }

  @Test
  @Order(0)
  public void testGetAll() {
    List<Event> template = Collections.emptyList();
    this.eventService.getAll().stream().forEach((e) -> {
      System.out.println(e.getId());
    });
    assertNotEquals(template, this.eventService.getAll());
  }

  @Test
  @Order(1)
  public void testFindById() {
    assertNotNull(this.eventService.findById(1));
    assertNotNull(this.eventService.findById(2));
    assertNull(this.eventService.findById(999));
  }

  @Test
  @Order(2)
  public void testSave() {
    assertEquals(this.eventService.getAll().size() + 1, this.eventService
        .save(new Create(2, Timestamp.valueOf(LocalDateTime.now()), null, "test")));
  }

  @Test
  @Order(3)
  public void testUpdate() {
    final Integer INDEX = 2;
    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
    now.setNanos(0);
    Event checker = this.eventService.findById(INDEX);
    assertTrue(this.eventService.update(new Update(INDEX, null, now, null, null)));
    assertEquals(now, this.eventService.findById(INDEX).getStart());
    assertEquals(checker.getIdApiary(), this.eventService.findById(INDEX).getIdApiary());
  }

  @Test
  @Order(4)
  public void testDeleteById() {
    // need to delete id without any foreign keys restrictions
    assertTrue(this.eventService.deleteById(2));
    assertFalse(this.eventService.deleteById(999));
  }
}
