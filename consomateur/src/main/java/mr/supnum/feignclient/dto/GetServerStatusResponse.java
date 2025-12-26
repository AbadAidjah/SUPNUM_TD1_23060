package mr.supnum.feignclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetServerStatusResponse {
    @JsonProperty("status")
    private Status status;

    public enum Status {
        ACTIVE,
        INACTIVE
    }
}

