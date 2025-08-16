package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.utl.dsm.huellas.control.ControllerEmpleadosLogin;
import org.utl.dsm.huellas.modelo.Empleado;
import org.utl.dsm.huellas.modelo.EmpleadoLogin;

@Path("empleadosLogin")
public class EmpleadosLoginApi {

    @POST
    @Path("validar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validarEmpleado(String datosLogin) {
        Gson gson = new Gson();
        try {
            EmpleadoLogin login = gson.fromJson(datosLogin, EmpleadoLogin.class);

            ControllerEmpleadosLogin controller = new ControllerEmpleadosLogin();
            Empleado empleado = controller.validarLogin(login);

            if (empleado != null) {
                return Response.status(Response.Status.OK).entity(gson.toJson(empleado)).build();
            } else {
                String mensaje = "{\"mensaje\":\"Correo o contraseña incorrectos.\"}";
                return Response.status(Response.Status.UNAUTHORIZED).entity(mensaje).build();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            String error = "{\"error\":\"Error al validar el empleado: " + ex.getMessage() + "\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}