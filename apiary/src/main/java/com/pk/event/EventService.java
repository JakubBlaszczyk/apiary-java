package com.pk.event;

import java.util.ArrayList;
import java.util.List;

import com.pk.apiary.ApiaryService;
import com.pk.event.request.EventCreate;
import com.pk.event.request.EventDisplay;
import com.pk.event.request.EventUpdate;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class EventService {
  private EventRepository eventRepository;
  private ApiaryService apiaryService;

  public List<EventDisplay> getAll() {
    List<Event> list = eventRepository.getAll();
    List<EventDisplay> temp = new ArrayList<>();
    for (Event event : list) {
      temp.add(new EventDisplay(event.getId(), event.getIdApiary(),
          event.getStart() != null ? event.getStart().toString() : null,
          event.getEnd() != null ? event.getEnd().toString() : null, event.getNote()));
      EventDisplay tempTemp = temp.get(temp.size() - 1);
      if (tempTemp.getStart() != null && tempTemp.getStart().length() > 10) {
        tempTemp.setStart(tempTemp.getStart().substring(0, 10));
      }
      if (tempTemp.getEnd() != null && tempTemp.getEnd().length() > 10) {
        tempTemp.setEnd(tempTemp.getEnd().substring(0, 10));
      }
    }
    return temp;
  }

  public Event findById(Integer id) {
    return eventRepository.findById(id);
  }

  public Boolean deleteById(Integer id) {
    return eventRepository.deleteById(id);
  }

  public Boolean update(EventUpdate event) {
    Event temp = eventRepository.findById(event.getId());
    if (temp == null) {
      log.warn("Account doesn't exist");
      return false;
    }

    // I have to check if such a apiary exists, if not then return false and log
    // message
    if (event.getIdApiary() == null) {
      event.setIdApiary(temp.getIdApiary());
    }

    if (event.getStart() == null) {
      event.setStart(temp.getStart());
    }

    if (event.getEnd() == null) {
      event.setEnd(temp.getEnd());
    }

    if (event.getNote() == null) {
      event.setNote(temp.getNote());
    }

    return eventRepository.update(event);
  }

  public Integer save(EventCreate event) {
    if (apiaryService.findById(event.getIdApiary()) == null) {
      log.warn("No apiary {}", event.getIdApiary());
      return -1;
    }

    return eventRepository.save(event);
  }
}
