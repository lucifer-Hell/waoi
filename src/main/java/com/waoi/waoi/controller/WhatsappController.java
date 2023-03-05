package com.waoi.waoi.controller;

import com.waoi.waoi.model.UserState;
import com.waoi.waoi.service.EventHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WhatsappController {
    @Autowired
    EventHandleService eventHandleService;

    @PostMapping("/glitchRequest")
    public String handleGlitchRequests(RequestEntity requestEntity) throws Exception {
        return eventHandleService.handleEvent(
                "7089212082",
                "welcome to hell"
        );
    }
    @PostMapping("/webhook")
    public ResponseEntity handleIncomingRequests(@RequestBody String message){
            return ResponseEntity.ok().body("Weclome");
    }
    @GetMapping("/webhook")
    public ResponseEntity verifyHook(@RequestParam (name="hub.challenge") String challenge,
                                     @RequestParam(name="hub.mode") String mode,
                                     @RequestParam(name="hub.verify_token") String token){
        if(mode!=null && token!=null){
            if (mode.equals("subscribe") && token.equals("whatsapp")) {
                // Respond with 200 OK and challenge token from the request
                return ResponseEntity.ok().body(challenge);
            } else {
                // Responds with '403 Forbidden' if verify tokens do not match
                return ResponseEntity.status(403).body("unauthorized");
            }
        }
//        System.out.println(challenge);
        return ResponseEntity.status(403).body("unauthorized");
    }
}
