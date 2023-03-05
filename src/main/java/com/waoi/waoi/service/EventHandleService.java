package com.waoi.waoi.service;

import com.waoi.waoi.enums.EventType;
import com.waoi.waoi.model.Event;
import com.waoi.waoi.model.UserState;
import com.waoi.waoi.repository.EventRepository;
import com.waoi.waoi.repository.UserStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class EventHandleService {
    // each request will have a mobile number and current event
    @Autowired
    private UserStateRepository userStateRepository;
    @Autowired
    private EventRepository eventRepository;

    public String handleEvent(String mobileNumber ,String input){
        UserState userState=userStateRepository.findByMobileNumber(mobileNumber);
        String response=null;
        if(userState==null){
            // user recently came
            userState=new UserState(mobileNumber);
            userState.setCurrEvtId("0");
            userState.setData(new HashMap<>());
            userStateRepository.save(userState);
        }
        else{
            // set user state
            Event event= eventRepository.findByEvtId(userState.getCurrEvtId());
            // if input type is option compare with options
            if(event.getEvtType().equals(EventType.QUESTION)){
                // set the answer
                userState.getData().put(event.getEvtId(),input);
            }
            else if (event.getEvtType().equals(EventType.DISPLAY)){
                // return message
            }
        }
        return eventRepository.findByEvtId(userState.getCurrEvtId()).getMessage();
    }
}
