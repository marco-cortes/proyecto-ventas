package fes.aragon.Ventas.controller;

import fes.aragon.Ventas.domain.Clientes;
import fes.aragon.Ventas.services.impl.ClientesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ClientesController {

    @Autowired
    private ClientesServiceImpl clientesService;

    @GetMapping("/")
    public String inicio(Clientes cliente, Model modelo) {
        List<Clientes> clientes = clientesService.listaClientes();
        modelo.addAttribute("clientes", clientes);
        modelo.addAttribute("cliente", cliente);
        return "tabla-clientes";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Clientes cliente, Errors errores, Model modelo){
        if(errores.hasErrors()){
            System.out.println(errores.getFieldError());
            modelo.addAttribute("errores", " Error");
            return inicio(cliente, modelo);
        }
        modelo.addAttribute("errores", null);
        clientesService.guardar(cliente);
        return "redirect:/";
    }

    @RequestMapping("/editar/{idClientes}")
    @ResponseBody
    public Clientes editar(@PathVariable(value="idClientes") int idClientes) {
        return  clientesService.getCliente(idClientes);
    }

    @PostMapping("/eliminar/{idClientes}")
    public String eliminar(@PathVariable(value="idClientes") int idClientes) {
        clientesService.eliminar(idClientes);
        return "redirect:/";
    }
}
