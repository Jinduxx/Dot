package com.dot.dotfilereaader.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class BlockedIpTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ip")
    private String ip;

    @NotNull
    @Column(name = "request_number")
    private int requestNumber;

    @NotNull
    @Column(name = "comment")
    private String comment;
}
