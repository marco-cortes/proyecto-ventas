package fes.aragon.Ventas.controller;

import fes.aragon.Ventas.domain.Usuario;
import fes.aragon.Ventas.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService cs;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model modelo, Usuario usuario){
        modelo.addAttribute("usuario", usuario);
        return "register";
    }

    @PostMapping("/register/guardar")
    public String guardarUsuario(@Valid Usuario usuario, String apellido, String cpassword, Model modelo, Errors errors){
        if(errors.hasErrors()){
            modelo.addAttribute("errores", "Error");
            return register(modelo, usuario);
        }
        if(usuario.getPassword().equals(cpassword)){
            usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
            usuario.setNombre(usuario.getNombre() + " " + apellido);
            cs.guardar(usuario);
        } else {
            modelo.addAttribute("errores", "Error");
            return register(modelo, usuario);
        }
        modelo.addAttribute("errores", null);
        return "redirect:/login?registrado";
    }

    @GetMapping("/datos")
    public String getUsuario(Model modelo){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User aux = (User) auth.getPrincipal();
        modelo.addAttribute("usuario", cs.getUsuario(aux.getUsername()));
        return "usuario";
    }

    @PostMapping("/datos/guardar")
    public String actualizar(Model modelo, @Valid Usuario usuario, String antiguo, Errors errores){
        if(errores.hasErrors()){
            modelo.addAttribute("errores", "Error");
            return getUsuario(modelo);
        }
        Usuario aux = cs.getUsuario(antiguo);
        aux.setNombre(usuario.getNombre());
        String redirect = "redirect:/datos";
        boolean bandera = false;
        if(!aux.getUsername().equals(usuario.getUsername())){
            redirect = "redirect:/login?aviso";
            bandera = true;
            aux.setUsername(usuario.getUsername());
        }
        aux.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        cs.guardar(aux);

        modelo.addAttribute("errores", null);
        if(bandera){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            auth.setAuthenticated(false);
        }
        return redirect;
    }

    @PostMapping("/datos/eliminar")
    public String eliminar(Usuario username){
        cs.eliminar(username.getUsername());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.setAuthenticated(false);
        return "redirect:/login?eliminado";
    }

}
