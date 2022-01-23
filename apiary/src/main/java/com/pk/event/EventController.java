package com.pk.event;

import java.util.List;

import com.pk.event.request.EventCreate;
import com.pk.event.request.EventUpdate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class EventController {

  private EventService eventService;

  @GetMapping("/event/{id}")
  public @ResponseBody Event getEvent(@PathVariable Integer id) {
    return this.eventService.findById(id);
  }

  @PostMapping("/eventAdd")
  public void addEvent(EventCreate eventCreate) {
    this.eventService.save(eventCreate);
  }

  @PostMapping("/eventUpdate")
  public void addEvent(EventUpdate eventUpdate) {
    this.eventService.update(eventUpdate);
  }

  @GetMapping("/event")
  public @ResponseBody List<Event> getEvents() {
    return this.eventService.getAll();
  }

  @GetMapping("/events")
  public String getEventHtml(Model model) {
    model.addAttribute("eventsData", this.eventService.getAll());
    return "Events";
  }
}
