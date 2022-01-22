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

public class Database {
  private DataSource dataSource;
  private Repository eventRepository;

  @BeforeEach
  public void initialization() {
    this.dataSource = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScripts("schema.sql", "data.sql")
        .build();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    this.eventRepository = new Persistent(jdbcTemplate);
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
    this.eventRepository.getAll().stream().forEach((e) -> {
      System.out.println(e.getId());
    });
    assertNotEquals(template, this.eventRepository.getAll());
  }

  @Test
  @Order(1)
  public void testFindById() {
    assertNotNull(this.eventRepository.findById(1));
    assertNotNull(this.eventRepository.findById(2));
    assertNull(this.eventRepository.findById(999));
  }

  @Test
  @Order(2)
  public void testSave() {
    assertEquals(this.eventRepository.getAll().size() + 1, this.eventRepository
        .save(new Create(1, Timestamp.valueOf(LocalDateTime.now()), null, "Some note")));
  }

  @Test
  @Order(3)
  public void testUpdate() {
    assertTrue(this.eventRepository.update(new Update(1, 2, null, null, null)));
    assertEquals(2, this.eventRepository.findById(1).getIdApiary());
  }

  @Test
  @Order(4)
  public void testDeleteById() {
    // need to delete id without any foreign keys restrictions
    assertTrue(this.eventRepository.deleteById(2));
    assertFalse(this.eventRepository.deleteById(999));
  }
}
