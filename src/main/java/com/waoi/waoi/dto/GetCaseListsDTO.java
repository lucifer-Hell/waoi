package com.waoi.waoi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class GetCaseListsDTO {
    String caseType;
    String caseNo;
    String regYear;
}
