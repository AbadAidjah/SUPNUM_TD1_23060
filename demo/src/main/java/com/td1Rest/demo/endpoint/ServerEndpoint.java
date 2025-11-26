package com.td1Rest.demo.endpoint;
import com.td1rest.demo.soap.servers.CreateServerRequest;
import com.td1rest.demo.soap.servers.CreateServerResponse;
import com.td1rest.demo.soap.servers.DeleteServerRequest;
import com.td1rest.demo.soap.servers.DeleteServerResponse;
import com.td1rest.demo.soap.servers.ListServersRequest;
import com.td1rest.demo.soap.servers.ListServersResponse;
import com.td1rest.demo.soap.servers.RenameServerRequest;
import com.td1rest.demo.soap.servers.RenameServerResponse;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.td1Rest.demo.model.ServerModel;
import com.td1Rest.demo.service.ServerService;
// import com.td1Rest.demo.model.ServerModel;
// import com.td1Rest.demo.model.ServerModel.Status;
import com.td1rest.demo.soap.servers.Server;
import com.td1rest.demo.soap.servers.StartServerRequest;
import com.td1rest.demo.soap.servers.StartServerResponse;
import com.td1rest.demo.soap.servers.Status;
import com.td1rest.demo.soap.servers.StopServerRequest;
import com.td1rest.demo.soap.servers.StopServerResponse;

import java.util.List;

// Import your generated request/response classes from XSD here

@Endpoint
public class ServerEndpoint {
    private static final String NAMESPACE_URI = "http://soap.demo.td1Rest.com/servers";
    private final ServerService serverService;

    public ServerEndpoint(ServerService serverService) {
        this.serverService = serverService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listServersRequest")
    @ResponsePayload
    public ListServersResponse listServers(@RequestPayload ListServersRequest request) {
        List<ServerModel> servers = serverService.ListServers();
        ListServersResponse response = new ListServersResponse();
        for (ServerModel model : servers) {
        Server soapServer = new Server();
        soapServer.setId(model.getId());
        soapServer.setName(model.getName());
        soapServer.setIpAddress(model.getIpAddress());
        if (model.getServerStatus() != null) {
            soapServer.setServerStatus(Status.valueOf(model.getServerStatus().name()));
        }
        response.getServer().add(soapServer);
    }
        return response;
    }


//     @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createServerRequest")
// @ResponsePayload
// public CreateServerResponse createServer(@RequestPayload CreateServerRequest request) {
//     CreateServerResponse response = new CreateServerResponse();
//     // Map request fields to your ServerModel
//     ServerModel model = new ServerModel();
//     model.setName(request.getName());
//     model.setIpAddress(request.getIpAddress());
//     model.setServerStatus(ServerModel.Status.valueOf(request.getServerStatus().name()));
//     // Save using your service
//     ServerModel saved = serverService.CreatServer(model);
//     // Set response fields
//     response.setServerId(saved.getId());
//     response.setSuccess(true);
//     return response;
// }
@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createServerRequest")
@ResponsePayload
public CreateServerResponse createServer(@RequestPayload CreateServerRequest request) {
    CreateServerResponse response = new CreateServerResponse();

    // Map request fields to your ServerModel
    ServerModel model = new ServerModel();
    model.setName(request.getServer().getName());
    model.setIpAddress(request.getServer().getIpAddress());
    // model.setServerStatus(ServerModel.Status.valueOf(request.getServer().getServerStatus().name()));
    if (request.getServer().getServerStatus() != null) {
    model.setServerStatus(ServerModel.Status.valueOf(request.getServer().getServerStatus().name()));
}

    // Save using your service
    ServerModel saved = serverService.CreatServer(model);

    // Map to generated SOAP Server object
    Server soapServer = new Server();
    soapServer.setId(saved.getId());
    soapServer.setName(saved.getName());
    soapServer.setIpAddress(saved.getIpAddress());
    
    // soapServer.setServerStatus(Status.valueOf(saved.getServerStatus().name()));
    if (saved.getServerStatus() != null) {
    soapServer.setServerStatus(Status.valueOf(saved.getServerStatus().name()));
}
    // Set all fields in the response
    response.setServer(soapServer);
    response.setServerId(saved.getId());
    response.setSuccess(true);
    response.setName(saved.getName());
    response.setIpAddress(saved.getIpAddress());
    if (saved.getServerStatus() != null) {
    response.setServerStatus(Status.valueOf(saved.getServerStatus().name()));
    }

    return response;
}

@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteServerRequest")
@ResponsePayload
public DeleteServerResponse DeleteServer(@RequestPayload DeleteServerRequest request){
    DeleteServerResponse response = new DeleteServerResponse();
    serverService.DeleteServer(request.getId());
     response.setSuccess(true);
     return response;
}
@PayloadRoot(namespace = NAMESPACE_URI, localPart = "renameServerRequest")
@ResponsePayload
public RenameServerResponse renameServer(@RequestPayload RenameServerRequest request){
    RenameServerResponse response = new RenameServerResponse();
    ServerModel server = serverService.RenameServer(request.getId(), request.getName());
    Server soapServer = new Server();
    soapServer.setId(server.getId());
    soapServer.setName(server.getName());
    soapServer.setIpAddress(server.getIpAddress());
    soapServer.setServerStatus(Status.valueOf(server.getServerStatus().name()));
    response.setServer(soapServer);
    return response;    
}

@PayloadRoot(namespace = NAMESPACE_URI, localPart = "startServerRequest")
@ResponsePayload
public StartServerResponse startServer(@RequestPayload StartServerRequest request){
        StartServerResponse response = new StartServerResponse();
        ServerModel server = serverService.StartServer(request.getId());
        Server soapServer = new Server();
        soapServer.setId(server.getId());
        soapServer.setName(server.getName());
        soapServer.setName(server.getName());
        soapServer.setIpAddress(server.getIpAddress());
        soapServer.setServerStatus(Status.valueOf(server.getServerStatus().name()));
        response.setServer(soapServer);
        return response;
}
@PayloadRoot(namespace = NAMESPACE_URI, localPart = "stopServerRequest")
@ResponsePayload
public StopServerResponse stopServer(@RequestPayload StopServerRequest request){
    StopServerResponse response = new StopServerResponse();
    ServerModel server = serverService.StopServer(request.getId());
    Server soapServer = new Server();
    soapServer.setId(server.getId());
        soapServer.setName(server.getName());
        soapServer.setName(server.getName());
        soapServer.setIpAddress(server.getIpAddress());
        soapServer.setServerStatus(Status.valueOf(server.getServerStatus().name()));
        response.setServer(soapServer);
        return response;
}

    
}