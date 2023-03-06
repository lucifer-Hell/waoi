package com.waoi.waoi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OptionDTO {
    @JsonProperty("OptionId")
    String OptionId;
    @JsonProperty("EventId")
    String EventId;
}
