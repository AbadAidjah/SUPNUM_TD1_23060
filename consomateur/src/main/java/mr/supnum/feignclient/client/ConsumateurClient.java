package mr.supnum.feignclient.client;

import mr.supnum.feignclient.dto.GetServerStatusResponse;
import mr.supnum.feignclient.dto.Server;
import mr.supnum.feignclient.dto.StartServerResponse;
import mr.supnum.feignclient.dto.StopServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "consumateur", url = "${consumateur.service.url}")
public interface ConsumateurClient {

    @GetMapping("/api/servers")
    List<Server> getAllServers();

    @GetMapping("/api/serverstatus/{id}")
    GetServerStatusResponse getServerStatus(@PathVariable("id") Long id);

    @PostMapping("/api/startserver/{id}")
    StartServerResponse startServer(@PathVariable("id") Long id);

    @PostMapping("/api/stopserver/{id}")
    StopServerResponse stopServer(@PathVariable("id") Long id);

    @PostMapping("/api/create/server/")
    Server createServer(@RequestBody Server server);

    @PutMapping("/api/rename/server/{id}")
    Server renameServer(@PathVariable("id") Long id, @RequestBody Server server);
}

