package com.gestion.unidadesoperativas.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "operative_unit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationalUnit {

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


    public OperationalUnit(String name, String director, String phonenumber,  String address, String codubi,String status) {
        this.name = name;
        this.director = director;
        this.phonenumber = phonenumber;
        this.address = address;
        this.codubi = codubi;
        this.status = status;
    }

}
