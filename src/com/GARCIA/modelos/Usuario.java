package com.GARCIA.modelos;


import com.GARCIA.libs.Conexion;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Usuario {

    private int codigo;
    private String identidad;
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private String correoElectronico;
    private String telefono;
    private Date creacion;


    public Usuario(String identidad, String nombre, String apellido,
                   String correoElectronico) {
        this.setIdentidad(identidad);
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setCorreoElectronico(correoElectronico);

    }

    private Usuario(String identidad, String nombre, String apellido,
                    String correoElectronico, String telefono){
        this.setIdentidad(identidad);
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setCorreoElectronico(correoElectronico);
        this.setTelefono(telefono);


    }

    public int getCodigo() {
        return codigo;
    }

    public String getIdentidad() {
        return identidad;
    }

    public void setIdentidad(String identidad) {
        this.identidad = identidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreUsuario() {
        if (this.nombreUsuario == null) {
            this.nombreUsuario = this.generarNombreUsuario(0);
        }
        return this.nombreUsuario;
    }

    private String generarNombreUsuario(int contador) {
        String tempNombreUsuario =
                this.nombre.toLowerCase() + "." + this.apellido.toLowerCase();
        if (contador > 0) {
            tempNombreUsuario += "." + String.valueOf(contador);
        }
        try {
            PreparedStatement sentencia = Conexion.abrirConexion().prepareStatement(
                    "select nombre_usuario from usuario where nombre_usuario = ?"
            );
            sentencia.setString(1, tempNombreUsuario);
            ResultSet resultado = sentencia.executeQuery();
            boolean encontrado = false;
            while (resultado.next()) {
                encontrado = true;
            }
            if (encontrado) {
                generarNombreUsuario(contador + 1);
            }
        } catch (SQLException e) {
            System.err.println("Error al generar el nombre de usuario " + e.getMessage());
        }
        return tempNombreUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getCreacion() {
        return creacion;
    }

    public boolean guardar() throws Exception {
        try {
            PreparedStatement sentencia = null;
            if (this.codigo == 0) {

                Usuario usuarioEncontrado = Usuario.getUsuario(this.getIdentidad());
                if (usuarioEncontrado != null) {
                    throw new Exception("El número de identidad ya existe");
                }

                sentencia = Conexion.abrirConexion().prepareStatement(
                        "insert into usuario (identidad, nombre_usuario, primer_nombre, apellido, correo_electronico, telefono)" +
                                " values (?, ?, ?, ?, ?, ?)"
                );
            } else {
                sentencia = Conexion.abrirConexion().prepareStatement(
                        "update usuario set" +
                                " identidad = ?," +
                                " nombre_usuario = ?," +
                                " primer_nombre = ?," +
                                " apellido = ?," +
                                " correo_electronico = ?," +
                                " telefono = ?" +
                                " where codigo = ?"
                );
                sentencia.setInt(7, this.getCodigo());
            }
            sentencia.setString(1, this.getIdentidad());
            sentencia.setString(2, this.getNombre());
            sentencia.setString(3, this.getApellido());
            sentencia.setString(4, this.getNombreUsuario());
            sentencia.setString(5, this.getCorreoElectronico());
            sentencia.setString(6, this.getTelefono());
            boolean resultado = sentencia.execute();
            if (resultado && this.codigo == 0) {
                Usuario usuarioCreado = Usuario.getUsuario(this.getIdentidad());
                this.codigo = usuarioCreado.getCodigo();
                this.creacion = usuarioCreado.getCreacion();
            }
            return resultado;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public static ArrayList<Usuario> getUsuarios(int limite) {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        PreparedStatement sentencia = null;

        try {
            sentencia = Conexion.abrirConexion().prepareStatement(
                    "select * from usuario limit ?"
            );
            sentencia.setInt(1, limite);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                listaUsuarios.add(Usuario.crearInstancia(resultado));
            }
        } catch (SQLException e) {
            System.err.println("Algo salio mal " + e.getMessage());
        }
        return listaUsuarios;

    }

    public static Usuario getUsuario(String identidad) {
        try {
            PreparedStatement sentencia = Conexion.abrirConexion().prepareStatement(
                    "SELECT * FROM usuario WHERE identidad = ?"
            );
            sentencia.setString(1, identidad);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {

                return Usuario.crearInstancia(resultado);
            }
        } catch (SQLException e) {
            System.err.println("Algo salio mal " + e.getMessage());
        }
        return null;
    }

    private static Usuario crearInstancia(ResultSet resultado) {
        Usuario usuario = null;
        try {
            usuario = new Usuario(
                    resultado.getString("identidad"),
                    resultado.getString("nombre_usuario"),
                    resultado.getString("apellido"),
                    resultado.getString("correo_electronico"),
                    resultado.getString("telefono"));
            usuario.codigo = resultado.getInt("codigo");
            usuario.nombreUsuario = resultado.getString("nombre_usuario");
            usuario.creacion = resultado.getDate("creacion");
        } catch (SQLException e) {
            System.err.println("Algo salio mal " + e.getMessage());
        }
        return usuario;
    }


    public String toString() {
        return this.identidad + " - " + this.nombre;
    }

    public static Usuario buscar(String campo, String valor, String limite){
        Usuario usuario = null;
        try {
            PreparedStatement setencia = Conexion.abrirConexion().prepareStatement(
                    "select * from usuario  where "+campo+" = ? and "+valor+" = ? and  limit ? "
            );
            setencia.setString(1,campo);
            setencia.setString(2,valor);
            setencia.setString(3,limite);
            ResultSet resultado = setencia.executeQuery();

            while (resultado.next()){
                System.out.println("identidad" + resultado.getString(2));
                System.out.println("nombre de usuario" + resultado.getString(3));
                System.out.println("pirmer nombre" + resultado.getString(4));
                System.out.println("apellido" + resultado.getString(5));
                System.out.println("correo electronico" + resultado.getString(6));
                System.out.println("telefono" + resultado.getString(7));
                System.out.println("creacion" + resultado.getDate(8));
            }

        }catch (SQLException e){
            System.err.println("Algo salio mal tu " + e.getMessage());
        }
        return null;
    }

    public static Usuario eliminar(String identi){
        PreparedStatement setencia = Conexion.abrirConexion().prepareStatement(
                "delete from usuario where  identidad = ?"
        );
            return null;
    }

}




