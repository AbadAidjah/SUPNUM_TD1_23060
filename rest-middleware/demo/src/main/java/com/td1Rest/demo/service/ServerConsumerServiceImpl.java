package com.td1Rest.demo.service;

import com.td1rest.demo.soap.servers.GetServerStatusRequest;
import com.td1rest.demo.soap.servers.GetServerStatusResponse;
import com.td1rest.demo.soap.servers.ListServersResponse;
import com.td1rest.demo.soap.servers.ObjectFactory;
import com.td1rest.demo.soap.servers.Server;
import com.td1rest.demo.soap.servers.StartServerRequest;
import com.td1rest.demo.soap.servers.StartServerResponse;
import com.td1rest.demo.soap.servers.StopServerRequest;
import com.td1rest.demo.soap.servers.StopServerResponse;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;
import com.td1rest.demo.soap.servers.ListServersResponse;

@Service
public class ServerConsumerServiceImpl {

  private static final String NAMESPACE_URI = "http://soap.demo.td1Rest.com/servers";
  private final WebServiceTemplate webServiceTemplate;
  private final ObjectFactory objectFactory;

  @Value("${soap.server.url}")
  // @Value("${SOAP_SERVER_URL}")
  // @Value("${soap.server.url}")
  private String soapServerUrl;

  public ServerConsumerServiceImpl(WebServiceTemplate webServiceTemplate) {
    this.webServiceTemplate = webServiceTemplate;
    this.objectFactory = new ObjectFactory();
  }

  public List<Server> getAllServers() {
    try {
      Object request = objectFactory.createListServersRequest();
      WebServiceMessageCallback messageCallback = message -> {
        if (message instanceof SoapMessage soapMessage) {
          soapMessage.setSoapAction("");
        }
      };
      ListServersResponse response = (ListServersResponse) webServiceTemplate
          .marshalSendAndReceive(soapServerUrl, request, messageCallback);
      return response != null ? response.getServer() : List.of();
    } catch (Exception e) {
      throw new RuntimeException("Erreur lors de l'appel au service SOAP: " + e.getMessage(), e);
    }

  }

  public GetServerStatusResponse getServerStatus(Long serverId) {
    try {
      GetServerStatusRequest request = objectFactory.createGetServerStatusRequest();
      request.setId(serverId);

      WebServiceMessageCallback messageCallback = message -> {
        if (message instanceof SoapMessage soapMessage) {
          soapMessage.setSoapAction("");
        }

      };
      GetServerStatusResponse response = (GetServerStatusResponse) webServiceTemplate
          .marshalSendAndReceive(soapServerUrl, request, messageCallback);
      // return response != null ? response.getStatus() : List.of();
      return response;
    } catch (Exception e) {
      throw new RuntimeException("Erreur lors de l'appel au service SOAP: " + e.getMessage(), e);

      // System.out.print("test");
    }

  }
  public StartServerResponse startServer(Long serverId){
    try {
     StartServerRequest request = objectFactory.createStartServerRequest();
     request.setId(serverId);

     WebServiceMessageCallback messageCallback = message ->{
      if(message instanceof SoapMessage soapMessage){
        soapMessage.setSoapAction("");
      }
     };
     StartServerResponse response = (StartServerResponse) webServiceTemplate
        .marshalSendAndReceive(soapServerUrl, request, messageCallback);
        return response;
    }
    catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'appel au service SOAP: " + e.getMessage(), e);
        }
  }
  public StopServerResponse StopServer(Long serverId){
    try {
      StopServerRequest request = objectFactory.createStopServerRequest();
      request.setId(serverId);

      WebServiceMessageCallback messageCallback = message -> {
        if(message instanceof SoapMessage soapMessage){
          soapMessage.setSoapAction("");

        }
      };
      StopServerResponse response = (StopServerResponse) webServiceTemplate
      .marshalSendAndReceive(soapServerUrl,request, messageCallback);
      return response;
    }catch(Exception e){
      throw new RuntimeException("Erreur lors de l'appel au service SOAP: " + e.getMessage(),e);

    }
  }




}
