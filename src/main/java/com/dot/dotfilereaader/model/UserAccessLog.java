package com.dot.dotfilereaader.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class UserAccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_time")
    private Timestamp date;

    @NotNull
    @Column(name = "ip_address")
    private String ipAddress;

    @NotNull
    @Column(name = "request")
    private String request;

    @NotNull
    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "user_agent")
    private String userAgent;
}
