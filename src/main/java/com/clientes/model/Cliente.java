package com.clientes.model;

import com.clientes.dto.ClienteDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor

/*
 * Entity es para mapear a BD
 **/
@Entity
@Table(name="Cliente")

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Generated value es para indicarle que al crear la tabla va a generar un numero único
    private int clienteId;
    //@Column()
    private String nombre;
   //@Column()
    private String apellidos;
    //@Column()
    private String fecha_nacimiento;


    public Cliente(ClienteDto clienteDto) {
        this.clienteId = clienteDto.getClienteId();
        this.nombre = clienteDto.getNombre();
        this.apellidos = clienteDto.getApellidos();
        this.fecha_nacimiento = clienteDto.getEdad();
    }
}
