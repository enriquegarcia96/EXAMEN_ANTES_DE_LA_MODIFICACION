package com.GARCIA;

import com.GARCIA.libs.Conexion;
import com.GARCIA.modelos.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Scanner lector = new Scanner(System.in);
        /**
        Usuario nuevoUsuario = new Usuario("0209199700123", "Sebastian", "Morales", "smorales@ejemplo.com","98543422");
        System.out.println("El código del usuario nuevo es: " + nuevoUsuario.getCodigo());
        nuevoUsuario.getNombreUsuario();
        try {
            nuevoUsuario.guardar();
            nuevoUsuario.guardar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }**/

        boolean continuar = true;
        while (continuar) {
            System.out.println("~~  Menu  ~~  ");
            System.out.println("1.crear un usuario  ");
            System.out.println("2. Mostrar todos los usuarios");
            System.out.println("3. busqueda por numero de identidad");
            System.out.println("4. busqueda por cualquier campo");
            System.out.println("5. eliminar");
            System.out.println("ingrese su opcion:  ");
            int opcion = lector.nextInt();


            switch (opcion){

                case 1:
                    System.out.println("ingrese su identidad");
                    String identidad = lector.next();
                    System.out.println("ingrese su nombre ");
                    String nombre = lector.next();
                    System.out.println("ingrese apellido ");
                    String apellido = lector.next();
                    System.out.println("ingrese su correo electronico");
                    String correo = lector.next();
                    System.out.println("ingrese su telefono ");
                    String telefono = lector.next();


                    Usuario usuario2 = new Usuario(identidad,nombre,apellido,correo,telefono);

                 break;

                case 2:
                    try {
                        PreparedStatement setencia = Conexion.abrirConexion().prepareStatement(
                                "select nombre_usuario from usuario"
                        );
                        ResultSet resultSet = setencia.executeQuery();
                        while (resultSet.next()){
                            System.out.println("nombre de usuario --->"+resultSet.getString(1));
                        }


                    }catch (SQLException e){
                        System.out.println("Error " + e.getMessage());

                    }
                    break;

                case 3:
                    System.out.println("ingrese el numero de identidad ");
                    String identidad2 = lector.next();

                    try {
                        PreparedStatement setencia = Conexion.abrirConexion().prepareStatement(
                                "select * from usuario\n" +
                                        "where identidad = ?"
                        );
                        setencia.setString(1,identidad2);
                        ResultSet resultSet = setencia.executeQuery();
                        while (resultSet.next()){
                            System.out.println("identidad -->"+resultSet.getString(2));
                            System.out.println("nombre de usuario"+resultSet.getString(3));
                            System.out.println("primer nombre"+resultSet.getString(4));
                            System.out.println("apellido"+resultSet.getString(5));
                            System.out.println("correo electronico"+resultSet.getString(6));
                            System.out.println("telefono"+resultSet.getString(7));
                            System.out.println("creacion"+resultSet.getString(8));
                        }

                    }catch (SQLException e){
                        System.out.println("Error " + e.getMessage());

                    }

                    break;

                case 4:
                    System.out.println("ingrese el campo a buscar ");
                    String campo = lector.next();
                    System.out.println("ingrese la identidad a indentidad ");
                    String id = lector.next();
                    System.out.println("ingrese un limite de usuarios a mostrar");
                    String limite = lector.next();

                 Usuario user = Usuario.buscar(campo,id,limite);



                default:
            }

        }



    }
}
