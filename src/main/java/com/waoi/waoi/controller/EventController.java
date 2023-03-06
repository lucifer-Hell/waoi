package com.waoi.waoi.controller;

import com.waoi.waoi.dto.EventRegisterDTO;
import com.waoi.waoi.model.Event;
import com.waoi.waoi.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
    @Autowired
    EventRepository eventRepository;
    @PostMapping("/registerEvent")
    public String registerEvent(@RequestBody EventRegisterDTO eventRegisterDTO){
        System.out.println(eventRegisterDTO);
        if(eventRepository.findByEvtId(eventRegisterDTO.getEvtId())==null){
            eventRepository.save(new Event(eventRegisterDTO));
            return "Event sucesfully registered";
        }
        return "Event with given event id already exists";
    }
}
