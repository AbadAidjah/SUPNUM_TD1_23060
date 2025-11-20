package com.td1Rest.demo.controller;

import java.util.List;

import org.springframework.stereotype.Component;

// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PatchMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;

import com.td1Rest.demo.model.ServerModel;
import com.td1Rest.demo.model.ServerModel.Status;
import com.td1Rest.demo.service.ServerService;

// @RestController
@Component
@WebService(serviceName = "ServerService")
// @RequestMapping("/api/rest/")
public class ServerController {
    private final ServerService serverService;

    public ServerController(ServerService serverService){
        this.serverService = serverService;
    }

    // @PostMapping("/create")
    @WebMethod
    public ServerModel CreateServer(@WebParam(name = "server") ServerModel server){
        ServerModel createServer = serverService.CreatServer(server);
        // return ResponseEntity.status(HttpStatus.CREATED).body(createServer);
        return createServer;
    }
    // @GetMapping("/list")
    @WebMethod
    public List<ServerModel> ListServers(){
        List<ServerModel> servers = serverService.ListServers();
        // return ResponseEntity.ok(servers);
        return servers;
    }
    // @GetMapping("/server/{id}/status")
    @WebMethod
    public Status getServerStatus(@WebParam(name = "id") Long id ){
        Status servertatus = serverService.getServerStatus(id);
        return servertatus;
    }
    
    // @PutMapping("/server/{id}/start")
    @WebMethod
    public ServerModel startServer(@WebParam(name = "id") Long id ){
        ServerModel server = serverService.StartServer(id);
        return server;
       
    }
    //   @PutMapping("/server/{id}/stop")
    @WebMethod
    public ServerModel stopServer(@WebParam(name = "id") Long id ){
        ServerModel server = serverService.StopServer(id);
        return server;
    }
    // @PutMapping("/server/{id}/rename")
    @WebMethod
    public ServerModel renameServer(
            @WebParam(name = "id") Long id , 
            @WebParam(name = "name") String name){
        ServerModel server = serverService.RenameServer(id, name);
        return server;
    }
    
   
    // @DeleteMapping("/server/{id}/delete")
    @WebMethod
    public void deleteServer(@WebParam(name = "id") Long id ){
        serverService.DeleteServer(id);
    }
}
