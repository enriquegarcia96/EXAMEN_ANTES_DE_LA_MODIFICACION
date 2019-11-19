package com.GARCIA;

import com.GARCIA.libs.Conexion;
import com.GARCIA.modelos.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            System.out.println("6. Salir");
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


                    Usuario usuarioNuevo = new Usuario(identidad,nombre,apellido,correo,telefono);
                    usuarioNuevo.getNombreUsuario();
                    try{
                        usuarioNuevo.guardar();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    if(usuarioNuevo != null) {
                        System.out.println("registro exitoso!!");
                    }

                 break;

                case 2:
                    System.out.println("cuantos usuarios desea mostrar: ");
                    int limite2 = lector.nextInt();
                    ArrayList<Usuario> lista = Usuario.getUsuarios(limite2);
                    for (Usuario c: lista){
                        System.out.println(c.getNombreUsuario());
                    }
                    break;

                case 3:

                    System.out.println("ingrese el numero de identidad a buscar: ");
                    String busquedaIdentidad = lector.next();
                    Usuario busqueda2 = Usuario.getUsuario(busquedaIdentidad);
                    System.out.println(""+busqueda2);
                    break;

                case 4:
                    System.out.println("1.Identidad "+
                            "2.Nombre de usuario "+
                            "3.Primer nombre "+
                            "4.Apellido "+
                            "5.Correo electronico "+
                            "6.Telefono");
                    System.out.println("ingrese la opcion al campo a modificar: ");
                    int campo2 = lector.nextInt();
                    System.out.println("ingrese la identidad a indentidad ");
                    String id = lector.next();
                    System.out.println("ingrese un limite de usuarios a mostrar");
                    String limite = lector.next();

                 //Usuario user = Usuario.buscar(campo2,id,limite);

                    System.out.println("assam"+ Usuario.buscar(campo2,id,limite));

                    break;
                case 5:
                    System.out.println("Ingrese la identidad a eliminar: ");
                    String identida = lector.next();
                    Boolean eliminarUsuario = Usuario.eliminar(identida);

                    if (eliminarUsuario.booleanValue()){
                        System.out.println("El registro fue eliminado con exitosamente!"+eliminarUsuario);
                    }else {
                        System.out.println("el usuario no existe con ese numero de identidad! "+eliminarUsuario);
                    }
                    break;
                case 6:
                    continuar = false;
                    System.out.println("Adios!");
                    break;

                default:
            }

        }
    }
}
