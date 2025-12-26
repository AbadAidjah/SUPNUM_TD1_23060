package mr.supnum.feignclient.controller;

import mr.supnum.feignclient.client.ConsumateurClient;
import mr.supnum.feignclient.dto.GetServerStatusResponse;
import mr.supnum.feignclient.dto.Server;
import mr.supnum.feignclient.dto.StartServerResponse;
import mr.supnum.feignclient.dto.StopServerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/client/servers")
public class ServerFeignController {

    private final ConsumateurClient consumateurClient;

    public ServerFeignController(ConsumateurClient consumateurClient) {
        this.consumateurClient = consumateurClient;
    }

    @GetMapping
    public ResponseEntity<List<Server>> getAllServers() {
        List<Server> servers = consumateurClient.getAllServers();
        return ResponseEntity.ok(servers);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<GetServerStatusResponse> getServerStatus(@PathVariable Long id) {
        GetServerStatusResponse response = consumateurClient.getServerStatus(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<StartServerResponse> startServer(@PathVariable Long id) {
        StartServerResponse response = consumateurClient.startServer(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<StopServerResponse> stopServer(@PathVariable Long id) {
        StopServerResponse response = consumateurClient.stopServer(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Server> createServer(@RequestBody Server server) {
        Server createdServer = consumateurClient.createServer(server);
        return ResponseEntity.ok(createdServer);
    }

    @PutMapping("/{id}/rename")
    public ResponseEntity<Server> renameServer(@PathVariable Long id, @RequestBody Server server) {
        Server renamedServer = consumateurClient.renameServer(id, server);
        return ResponseEntity.ok(renamedServer);
    }
}

