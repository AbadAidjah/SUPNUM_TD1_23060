package com.td1Rest.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.td1rest.demo.soap.servers.GetServerStatusResponse;
import com.td1rest.demo.soap.servers.Server;
import com.td1rest.demo.soap.servers.StartServerResponse;
import com.td1rest.demo.soap.servers.StopServerResponse;

@Service
public interface ServerConsumerService {
List<Server> getAllServers();
GetServerStatusResponse GetServerStatus(Long ServerId);
StartServerResponse startServer(Long serverId);
StopServerResponse stopServer(Long serverId);
}