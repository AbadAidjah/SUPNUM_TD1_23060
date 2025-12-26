package com.td1Rest.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.td1Rest.demo.model.ServerModel;
import com.td1Rest.demo.service.ServerConsumerService;
import com.td1Rest.demo.service.ServerConsumerServiceImpl;
import com.td1rest.demo.soap.servers.Server;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

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

    @GetMapping("/api/serverstatus/{id}")
    public ResponseEntity<?> getServerStatus(@PathVariable Long id){
       
        return ResponseEntity.ok(serverConsumerServiceImpl.getServerStatus(id));

    }
    @PostMapping("/api/startserver/{id}")
    public ResponseEntity<?> startServer(@PathVariable Long id){
        return ResponseEntity.ok(serverConsumerServiceImpl.startServer(id));
    }
    
    @PostMapping("/api/stopserver/{id}")
    public ResponseEntity<?> stopServer(@PathVariable Long id){
        return ResponseEntity.ok(serverConsumerServiceImpl.StopServer(id));
    }

    @PostMapping("/api/create/server/")
    public ResponseEntity<?> createServer(@org.springframework.web.bind.annotation.RequestBody ServerModel server){
        try{
            System.out.println("hhhhhhhhhhhhhhhhhhh loooooooook attttt thiiiiiis "+server.toString() + server.getName() + server.getIpAddress());
        return ResponseEntity.ok(serverConsumerServiceImpl.CreateServer(server));
    }catch(Exception e){
        return ResponseEntity.status(500).body("Error creation server: " + e.getMessage());
    }
}

    
      

    
}