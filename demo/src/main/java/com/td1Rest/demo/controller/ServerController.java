package com.td1Rest.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.td1Rest.demo.model.ServerModel;
import com.td1Rest.demo.model.ServerModel.Status;
import com.td1Rest.demo.service.ServerService;

@RestController
@RequestMapping("/api/rest/")
public class ServerController {
    private final ServerService serverService;

    public ServerController(ServerService serverService){
        this.serverService = serverService;
    }

    @PostMapping("/create")
    public ResponseEntity CreateServer(@RequestBody ServerModel server){
        ServerModel createServer = serverService.CreatServer(server);
        return ResponseEntity.status(HttpStatus.CREATED).body(createServer);
    }
    @GetMapping("/list")
    public ResponseEntity ListServers(){
        List<ServerModel> servers = serverService.ListServers();
        return ResponseEntity.ok(servers);
    }
    @GetMapping("/server/{id}/status")
    public ResponseEntity<Status> getServerStatus(@PathVariable Long id){
        Status servertatus = serverService.getServerStatus(id);
        return ResponseEntity.ok(servertatus);
    }
    
    @PutMapping("/server/{id}/start")
    public ResponseEntity<ServerModel> startServer(@PathVariable Long id){
        ServerModel server = serverService.StartServer(id);
        return ResponseEntity.ok(server);
       
    }
      @PutMapping("/server/{id}/stop")
    public ResponseEntity<ServerModel> stopServer(@PathVariable Long id){
        ServerModel server = serverService.StopServer(id);
        return ResponseEntity.ok(server);
    }
    @PutMapping("/server/{id}/rename")
    public ResponseEntity<ServerModel> renameServer(
            @PathVariable Long id, 
            @RequestParam String name){
        ServerModel server = serverService.RenameServer(id, name);
        return ResponseEntity.ok(server);
    }
    
   
    @DeleteMapping("/server/{id}/delete")
    public ResponseEntity<Void> deleteServer(@PathVariable Long id){
        serverService.DeleteServer(id);
        return ResponseEntity.noContent().build();
    }
}
