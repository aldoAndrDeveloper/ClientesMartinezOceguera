package com.clientes.controller;

import com.clientes.dto.ClienteDto;
import com.clientes.exceptions.DateFormatException;
import com.clientes.service.ClienteService;
import com.clientes.service.DateValidatorUsingDateFormat;
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
    public ResponseEntity <ClienteDto> save(@RequestBody ClienteDto clienteDto) throws DateFormatException {
        FabricaClienteService fabricaClienteService = new FabricaClienteService();
        /**Usamos este método para validar que el formato de fecha sea correcto
         * Si no lo es se arrojará la excepción que creamos con el mensaje:
         * "El formato de fecha no es válido, asegurate de ser dd/mm/aaaa"**/

        fabricaClienteService.validarFecha(clienteDto.getEdad());
        //Si no falla entonces se agregará exitosamente el dato en la tabla
        return new ResponseEntity<>( clienteService.save(clienteDto), HttpStatus.OK);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ClienteDto>> findAll(){
        //Para convertir la fecha de nacimiento a la edad actual haremos lo siguiente

        //1.-Vamos a llamar la lista de clientes
        List<ClienteDto> clienteDtoList =  clienteService.findAll();
        FabricaClienteService fabricaClienteService = new FabricaClienteService();
        //2.-Se da el formato de fecha en este caso será dd/mm/yyyy
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //3.-Sacamos la fecha actual
        Calendar todayCalendar = Calendar.getInstance();
        //Esta variable la usaremos para darle el valor de la fecha de nacimiento
        Calendar birthdayCalendar = Calendar.getInstance();

        try{
            System.out.println("ClienteDTOList.size () = "+clienteDtoList.size());

            for(int i=0;i<clienteDtoList.size();i++){
                //4.- iteramos la lista para 1 por 1 sacar la fecha de nacimiento y validar el formato
                Date birthDate = simpleDateFormat.parse(clienteDtoList.get(i).getEdad());
                //le agregamos la fecha de nacimiento
                birthdayCalendar.setTime(birthDate);
                //5.-Restmos el año actual - el año guardado
                int age = todayCalendar.get(Calendar.YEAR) - birthdayCalendar.get(Calendar.YEAR);
                //agregamos la edad al JSON
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
