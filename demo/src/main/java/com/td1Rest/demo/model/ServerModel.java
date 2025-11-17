package com.td1Rest.demo.model;
import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ServerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ipAddress;
    public enum Status {
        ACTIVE,
        INACTIVE
    }
    @Enumerated(EnumType.STRING)
    private Status serverStatus;


    public ServerModel(Long id, String name, String ipAddress, Status serverStatus ){
        this.id = id;
        this.name = name;
        this.ipAddress = ipAddress;
        this.serverStatus = serverStatus;
    }

    public Long getId(){
       return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Status getServerStatus() {
        return this.serverStatus;
    }

    public void setServerStatus(Status serverStatus) {
        this.serverStatus = serverStatus;
    }

}
