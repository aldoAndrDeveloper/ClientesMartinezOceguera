package com.clientes.service;


import com.clientes.dto.ClienteDto;
import com.clientes.model.Cliente;
import com.clientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired // es para inyectar esta clase a nuestro servicio
    private FabricaClienteService fabricaClienteService;
    @Autowired
    private ClienteRepository clienteRepository;

    /**ClienteDto es el modelo con las variables (nombres) que se van a mostrar en el JSON
     * al recibir un json con los mismos nombres debemos convertirlos a los nombres originales de las tablas
     * por eso en el constructor de Cliente se igualan con los valores de ClienteDto
     **/
    public ClienteDto save(ClienteDto clienteDto){
        return fabricaClienteService.crearClienteDto(clienteRepository.save(fabricaClienteService.crearCliente(clienteDto)));
    }
    public List<ClienteDto> findAll(){
        return fabricaClienteService.crearClientesDto(clienteRepository.findAll());
    }
    public ClienteDto findById(Integer id){
        return fabricaClienteService.crearClienteDto(clienteRepository.findById(id).get());}
    public void deleteById(Integer id){
        clienteRepository.deleteById(id);
    }

}
