package com.td1Rest.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.td1Rest.demo.model.ServerModel;
import com.td1Rest.demo.service.ServerService;

@RestController
@RequestMapping("/api/rest/")
public class ServerController {
    private final ServerService serverService;

    public ServerController(ServerService serverService){
        this.serverService = serverService;
    }

    @PostMapping("create")
    public ResponseEntity CreateServer(ServerModel server){
        ServerModel createServer = serverService.CreatServer(server);
        return ResponseEntity.status(HttpStatus.CREATED).body(createServer);
    }

}
