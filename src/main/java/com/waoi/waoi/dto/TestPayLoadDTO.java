package com.waoi.waoi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TestPayLoadDTO {
    @JsonProperty("mobileNumber")
    String mobileNumber;
    @JsonProperty("input")
    String input;
}
