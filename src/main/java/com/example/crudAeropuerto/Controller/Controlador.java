package com.example.crudAeropuerto.Controller;

import com.example.crudAeropuerto.Entity.Vuelo;
import com.example.crudAeropuerto.Repository.VueloRepo;
import com.example.crudAeropuerto.Service.VueloServiceIMPL.VueloServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("CrudRepo")
public class Controlador {

    @Autowired
    private VueloServiceIMPL impl;

    @Autowired
    private VueloRepo vueloRepo;

    @RequestMapping(value = "/ConsultarVuelos")
    public String ConsultarVuelos(Model model){
        List<Vuelo> listaVuelos= vueloRepo.findAll();
        model.addAttribute("listaVuelos", listaVuelos);
        return "vuelos";
    }

    @RequestMapping(value = "/CrearVuelos")
    public String CreaVuelos(Model model){
        return "creaVuelos";
    }

    @PostMapping(value = "/CrearVuelos")
    public ResponseEntity<?> CrearVuelos(Vuelo vuelo){
        Vuelo VueloCreado = vueloRepo.save(vuelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(VueloCreado);
    }

    @GetMapping(value = "/ModificarVuelos")
    public String ModiVuelos(@RequestParam int numVuelo, Model model){
        Vuelo vuelo = impl.BuscaVuelo(numVuelo);
        model.addAttribute("vuelo", vuelo);
        return "modificarVuelo";
    }
    @PostMapping(value = "/ModificarVuelos")
    public ResponseEntity<?> ModificarVuelos (@PathVariable int numVuelo,
                                              @RequestParam int numPasajeros,
                                              @RequestParam String origen,
                                              @RequestParam String destino,
                                              @RequestParam String fecha,
                                              @RequestParam String horaSalida){
        Vuelo vuelo = impl.BuscaVuelo(numVuelo);
        vuelo.setNumPasajeros(numPasajeros);
        vuelo.setOrigen(origen);
        vuelo.setDestino(destino);
        vuelo.setFecha(fecha);
        vuelo.setHora(horaSalida);
        Vuelo vueloModificado = vueloRepo.save(vuelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(vueloModificado);
    }


    @GetMapping(value = "/BuscaVuelo/{numVuelo}")
    public ResponseEntity<?> BuscaVuelo (@PathVariable int numVuelo){
        Vuelo vuelo = impl.BuscaVuelo(numVuelo);
        return ResponseEntity.ok(vuelo);
    }

    @DeleteMapping(value = "/ElimnVuelo/{numVuelo}")
    public ResponseEntity<?> ElimnVuelo (@PathVariable int numVuelo){
        impl.EliminaVuelo(numVuelo);
        return ResponseEntity.ok().build();
    }
}
