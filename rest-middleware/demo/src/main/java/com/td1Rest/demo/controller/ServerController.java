package com.td1Rest.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.td1Rest.demo.service.ServerConsumerService;
import com.td1Rest.demo.service.ServerConsumerServiceImpl;
import com.td1rest.demo.soap.servers.Server;

@RestController
public class ServerController {
    private final ServerConsumerServiceImpl serverConsumerServiceImpl;
    public ServerController(ServerConsumerServiceImpl serverConsumerServiceImpl){
        this.serverConsumerServiceImpl = serverConsumerServiceImpl;
    }

    @GetMapping("/api/servers")
    public ResponseEntity<List<Server>> getAllServers(){
        List<Server> servers =  serverConsumerServiceImpl.getAllServers();
        return ResponseEntity.ok(servers);
    }

}