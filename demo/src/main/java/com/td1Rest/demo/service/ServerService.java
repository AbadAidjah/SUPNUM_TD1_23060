package com.td1Rest.demo.service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeMBeanException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.td1Rest.demo.model.ServerModel;
// import com.td1Rest.demo.model.ServerModel.Status;
import com.td1Rest.demo.model.ServerModel.Status;
import com.td1Rest.demo.repository.ServerRepository;

import io.swagger.v3.oas.models.servers.Server;

@Service
public class ServerService {

    
    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository){
        this.serverRepository = serverRepository;
    }

    public ServerModel CreatServer(ServerModel server){
         return serverRepository.save(server);
    }
    public List<ServerModel> ListServers(){
        return serverRepository.findAll();
    }


    public ServerModel RenameServer(Long id,String serverName){
        ServerModel server = serverRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Server Not Found")); 
       server.setName(serverName);
       return serverRepository.save(server);
    }
    public ServerModel StartServer(Long id){
        ServerModel server = serverRepository.findById(id).orElseThrow(() -> new RuntimeException("server not found"));
        server.setServerStatus(Status.ACTIVE); 
        
        return serverRepository.save(server);
    }
   public ServerModel StopServer(Long id){
    ServerModel server = serverRepository.findById(id).orElseThrow(() -> new RuntimeException("server is not found "));
    server.setServerStatus(Status.INACTIVE);
    return serverRepository.save(server);
   }
   public Status getServerStatus(Long id){
    ServerModel server = serverRepository.findById(id).orElseThrow(() -> new RuntimeException("server not found"));
    return server.getServerStatus();

    // 
   }

    public void DeleteServer(Long id){
        ServerModel server = serverRepository.findById(id).orElseThrow(() -> new RuntimeException("server not found"));
        serverRepository.delete(server);
        
    } 


}
