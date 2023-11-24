package com.clientes.service;

import com.clientes.dto.ClienteDto;
import com.clientes.exceptions.DateFormatException;
import com.clientes.model.Cliente;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class FabricaClienteService {

    public Cliente crearCliente(ClienteDto clienteDto){
        return new Cliente(clienteDto);
    }

    public  ClienteDto crearClienteDto(Cliente  cliente){
        return new ClienteDto(cliente);
    }


    public List<ClienteDto> crearClientesDto(List<Cliente> clienteList){
        List<ClienteDto> clienteDtoList = new ArrayList<>();
        clienteList.stream().forEach(
                clienteDto -> clienteDtoList.add(crearClienteDto(clienteDto))
        );
        for (int i = 0; i < clienteDtoList.size();i++){
//            clienteDtoList.get(i).setEdad() = "";
        }

        return clienteDtoList;
    }

    public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

    private static String dateFormat;
    public void validarFecha(String fechaDeNacimiento) throws DateFormatException {


        DateValidatorUsingDateFormat validator = new DateValidatorUsingDateFormat("dd/mm/yyyy");

        if(!validator.isValid(fechaDeNacimiento)){
            throw new DateFormatException("El formato de fecha no es válido, asegurate de ser dd/mm/aaaa");
        }
    }


}
