package com.cobraTeam.intelligentFormsApp.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Data
@Container(containerName = "User", ru = "400")
public class User {

    @Id
    @GeneratedValue
    private String id;

    private String fullName;

    private String role;

    private String fiscalCode;

    private String address;

    private String emailAddress;

    private String password;


    public User() {
        this.id = UUID.randomUUID().toString();
        this.fullName = fullName;
        this.role = role;
        this.fiscalCode = fiscalCode;
        this.address = address;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
