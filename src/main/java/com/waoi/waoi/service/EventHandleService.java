package com.waoi.waoi.service;

import com.waoi.waoi.dto.OptionDTO;
import com.waoi.waoi.enums.EventType;
import com.waoi.waoi.enums.InputType;
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

    public String handleEvent(String mobileNumber ,String input) throws Exception{
        UserState userState=userStateRepository.findByMobileNumber(mobileNumber);
        String response=null;
        if(userState==null){
            // user recently came
            userState=new UserState(mobileNumber);
            userState.setCurrEvtId("0");
            userState.setData(new HashMap<>());
            userStateRepository.save(userState);
            response=getEvtMessage(userState.getCurrEvtId());
        }
        else{
            // set user state
            Event event= eventRepository.findByEvtId(userState.getCurrEvtId());
            // if event type is question
            if(event.getEvtType().equals(EventType.QUESTION)){
                // if input type is options
                if(event.getInputType().equals(InputType.OPTIONS)){
                    // compare with option and chose next event accordingly
                    boolean noOptionFound=true;
                    for(OptionDTO option :event.getOptions()) {
                        if (option.getOptionId().equals(input)) {
                            userState.setPrevEvtId(userState.getCurrEvtId());
                            userState.setCurrEvtId(option.getEventId());
                            userStateRepository.save(userState);
                            noOptionFound=false;
                            break;
                        }
                    }
                    if(noOptionFound)
                        throw new Exception("Unknown option");
                    else
                        response= getEvtMessage(userState.getCurrEvtId());
                }
                else if(event.getInputType().equals(InputType.DATA)){
                    // set the answer
                    userState.getData().put(event.getEvtId(),input);
                    userState.setPrevEvtId(userState.getCurrEvtId());
                    userState.setCurrEvtId(event.getNextEvt());
                    userStateRepository.save(userState);
                    return handleEvent(mobileNumber,input);
                }
                else{
                    throw new Exception("Invalid Input type ");
                }
            }
            // if event type is display
            else if (event.getEvtType().equals(EventType.DISPLAY)){
                // return message
                response=userState.getData().toString();
                userState.setPrevEvtId(userState.getCurrEvtId());
                userState.setCurrEvtId(event.getNextEvt());
            }
            else if(event.getInputType().equals(EventType.APICALL)){
                 // TODO IMPLEMENT
                response="call api !";
            }
            else{
                throw new Exception("Unknown Event Type");
            }
        }
        return response;
    }

    private String getEvtMessage(String evtId) {
        return eventRepository.findByEvtId(evtId).getMessage();
    }
}
