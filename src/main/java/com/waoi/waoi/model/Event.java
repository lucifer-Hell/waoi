package com.waoi.waoi.model;

import com.waoi.waoi.enums.EventType;
import com.waoi.waoi.enums.InputType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;

@Data
@Document(collection = "Event")
public class Event {
    @Id
    String id;
    @Indexed
    String evtId;
    String message;
    List<String> nextEvt;
    EventType evtType;
    InputType inputType;
    List<HashMap<String,String>> options;
}
