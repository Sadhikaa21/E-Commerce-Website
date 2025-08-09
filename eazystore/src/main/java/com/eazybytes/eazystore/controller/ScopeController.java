package com.eazybytes.eazystore.controller;

import com.eazybytes.eazystore.scopes.ApplicationScopedBean;
import com.eazybytes.eazystore.scopes.RequestScopedBean;
import com.eazybytes.eazystore.scopes.SessionScopedBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/scope")
@RequiredArgsConstructor
public class ScopeController {

    private final RequestScopedBean requestScopedBean;
    private final SessionScopedBean sessionScopedBean;
    private final ApplicationScopedBean applicationScopedBean;

    @GetMapping("/request")
    public ResponseEntity<String> testRequestScope() {
        requestScopedBean.setUserName("John Doe");
        return ResponseEntity.ok().body(requestScopedBean.getUserName());
    }
//    new bean for each api request

    @GetMapping("/session")
    public ResponseEntity<String> testSessionScope() {
        sessionScopedBean.setUserName("John Doe");
        return ResponseEntity.ok().body(sessionScopedBean.getUserName());
    }
//same bean until user closes the application in the browser since he opened it first
    @GetMapping("/application")
    public ResponseEntity<Integer> testApplicationScope() {
        applicationScopedBean.incrementVisitorCount();
        return ResponseEntity.ok().body(applicationScopedBean.getVisitorCount());
    }
//same bean used until aPPLICATION RESTARTS,increments on reloading application api call page,count remains same until u restart application(it is retained)
    @GetMapping("/test")
    public ResponseEntity<Integer> testScope() {
        return ResponseEntity.ok().body(applicationScopedBean.getVisitorCount());
    }
}