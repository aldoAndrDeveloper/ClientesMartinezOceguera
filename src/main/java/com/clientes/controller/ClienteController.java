package com.clientes.controller;

import com.clientes.dto.ClienteDto;
import com.clientes.service.ClienteService;
import com.clientes.service.FabricaClienteService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @PostMapping("/guardar")
    public ResponseEntity <ClienteDto> save(@RequestBody ClienteDto clienteDto){
        return new ResponseEntity<>( clienteService.save(clienteDto), HttpStatus.OK);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ClienteDto>> findAll(){

        List<ClienteDto> clienteDtoList =  clienteService.findAll();
        FabricaClienteService fabricaClienteService = new FabricaClienteService();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        try{
            System.out.println("ClienteDTOList.size () = "+clienteDtoList.size());

            for(int i=0;i<clienteDtoList.size();i++){
               // fabricaClienteService = new FabricaClienteService();
               // System.out.println("valor = "+Integer.toString());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date birthDate = simpleDateFormat.parse(clienteDtoList.get(i).getEdad());
                Calendar birthdayCalendar = Calendar.getInstance();
                birthdayCalendar.setTime(birthDate);
                Calendar todayCalendar = Calendar.getInstance();
                int age = todayCalendar.get(Calendar.YEAR) - birthdayCalendar.get(Calendar.YEAR);

                // fabricaClienteService.calculateAge(LocalDate.parse(clienteDtoList.get(i).getEdad(),formatter), LocalDate.parse(formatter.format(now)));
                clienteDtoList.get(i).setEdad(Integer.toString(age)+" años");
            }
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(clienteDtoList,HttpStatus.OK);
    }


    @GetMapping("/buscar/{id}")
    public ResponseEntity<ClienteDto> findById(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(clienteService.findById(id), HttpStatus.OK);
        }catch(Exception ex){
            //Si no encuentra datos en lugar de mostrar un error mandaremos un Status de NOT_FOUND
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity< HttpStatus> deleteById(@PathVariable Integer id){
        try{
            //Se elimina usando el metodo de la clase de servicio usando el Id
            clienteService.deleteById(id);
            //Si se elim inó mandaremos un status 200
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception ex){
            //Si no se encuentra enviaremos un status 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
