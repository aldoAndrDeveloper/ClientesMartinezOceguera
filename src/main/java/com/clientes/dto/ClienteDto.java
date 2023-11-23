package com.clientes.dto;

import com.clientes.model.Cliente;
import lombok.Data;
import lombok.NoArgsConstructor;

//Aquí aparecerá lo que queremos mostrar al usuario
@Data //Crea los getter y setter de manera automática sin necesidad de hacerlo nosotros
@NoArgsConstructor
public class ClienteDto {
    private int clienteId;
    private String nombre;
    private String apellidos;
    //Cambiaremos el nombre de esta variable que se mostrará en ell JSON, va a sustuir fecha_nacimiento
    private String edad;

    public ClienteDto(Cliente cliente) {
        this.clienteId = cliente.getClienteId();
        this.nombre = cliente.getNombre();
        this.apellidos = cliente.getApellidos();
        this.edad = cliente.getFecha_nacimiento();
    }
}
