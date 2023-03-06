package com.waoi.waoi.model;

import com.waoi.waoi.dto.EventRegisterDTO;
import com.waoi.waoi.dto.OptionDTO;
import com.waoi.waoi.enums.EventType;
import com.waoi.waoi.enums.InputType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "Event")
public class Event {
    @Id
    String id;
    @Indexed
    @NonNull
    String evtId;
    String message;
    String nextEvt;
    EventType evtType;
    InputType inputType;
    List<OptionDTO> options;

    Event(String evtId,
          String message,
          String nextEvt,
          EventType evtType,
          InputType inputType,
          List<OptionDTO> options
    ) {
        this.evtId = evtId;
        this.message = message;
        this.nextEvt = nextEvt;
        this.evtType = evtType;
        this.inputType = inputType;
        this.options = options;
    }

    public Event(EventRegisterDTO eventRegisterDTO
    ) {
        this.evtId =eventRegisterDTO.getEvtId();
        this.message = eventRegisterDTO.getMessage();
        this.nextEvt = eventRegisterDTO.getNextEvt();
        this.evtType = eventRegisterDTO.getEvtType();
        this.inputType = eventRegisterDTO.getInputType();
        this.options = eventRegisterDTO.getOptions();
    }
}
