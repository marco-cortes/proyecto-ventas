package fes.aragon.Ventas.controller;

import fes.aragon.Ventas.domain.Clientes;
import fes.aragon.Ventas.services.impl.ClientesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClientesController {

    @Autowired
    private ClientesServiceImpl clientesService;

    @GetMapping("/")
    public String inicio(Clientes cliente, Model modelo) {
        var clientes = clientesService.listaClientes();
        modelo.addAttribute("clientes", clientes);
        return "index";
    }

    @GetMapping("/nuevo")
    public String nuevo(Clientes cliente, Model modelo){
        modelo.addAttribute("clientes", cliente);
        return "formularios/formularioclientes";
    }

    @PostMapping("/guardar")
    public String guardar(Clientes cliente){
        clientesService.guardar(cliente);
        return "redirect:/";
    }

    @GetMapping("/editar/{idClientes}")
    public String editar(@PathVariable(value="idClientes") int idClientes, Model model) {
        Clientes cliente = clientesService.getCliente(idClientes);
        model.addAttribute("clientes", cliente);
        return "formularios/formularioClientes";
    }

    @PostMapping("/eliminar/{idClientes}")
    public String eliminar(@PathVariable(value="idClientes") int idClientes) {
        clientesService.eliminar(idClientes);
        return "redirect:/";
    }
}
