package com.waoi.waoi.controller;

import com.waoi.waoi.dto.GetCaseListsDTO;
import com.waoi.waoi.dto.TestPayLoadDTO;
import com.waoi.waoi.service.LawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/")
public class TestController {
    @Autowired
    LawService lawService;

    @GetMapping("/ping")
    public String getStatus(){
        return "welcome";
    }

    @PostMapping(value = "/getCaseList",produces={MediaType.TEXT_HTML_VALUE})
    public String handleGlitchRequests(@RequestBody GetCaseListsDTO testPayLoadDTO) throws Exception {
        return lawService.getMatchedCasesList(testPayLoadDTO);
    }
}
