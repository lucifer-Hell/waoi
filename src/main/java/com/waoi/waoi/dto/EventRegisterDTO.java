package com.waoi.waoi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waoi.waoi.enums.EventType;
import com.waoi.waoi.enums.InputType;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;
@Data
public class EventRegisterDTO {
//    @NonNull
    String evtId;
    String message;
    String nextEvt;
    EventType evtType;
    InputType inputType;
    @JsonProperty("options")
    List<OptionDTO> options;
}
