package com.gestion.unidadesoperativas.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class OperationalUnitResponseDto implements Serializable{


    @Serial
    private static final long serialVersionUID = 8222253670338491507L;

    @Id
    private Integer id_operativeunit;
    @Column
    private String name;
    @Column
    private String director;
    @Column
    private String phonenumber;
    @Column
    private String address;
    @Column
    private String codubi;
    @Column
    private String status;
}
