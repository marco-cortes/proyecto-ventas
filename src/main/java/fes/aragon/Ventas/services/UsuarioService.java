package fes.aragon.Ventas.services;


import java.util.ArrayList;

import fes.aragon.Ventas.domain.Usuario;
import fes.aragon.Ventas.repo.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userDetailsService")
public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioRepo usuarioDao;

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        if(usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        ArrayList<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }

    public void guardar(Usuario usuario){
        usuarioDao.save(usuario);
    }

    public Usuario getUsuario(String username){
        return usuarioDao.findByUsername(username);
    }

    public void eliminar(String username){
        usuarioDao.delete(getUsuario(username));
    }
}