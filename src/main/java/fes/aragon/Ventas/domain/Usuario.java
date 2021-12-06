package fes.aragon.Ventas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name="usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Integer idUsuario;

    @NotEmpty
    @Column(name="nombre")
    private String nombre;

    @NotEmpty
    @Email
    @Column(name="email")
    private String username;

    @NotEmpty
    @Column(name="password")
    private String password;

}
