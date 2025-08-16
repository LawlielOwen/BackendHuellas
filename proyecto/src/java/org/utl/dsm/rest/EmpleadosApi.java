package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.utl.dsm.huellas.modelo.Empleado;
import org.utl.dsm.huellas.control.ControllerEmpleados;

@Path("empleados")
public class EmpleadosApi extends Application {

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("estatus") @DefaultValue("-1") int estatus) {
        String out = null;
        List<Empleado> lista;
        ControllerEmpleados ce = new ControllerEmpleados();
        try {
            lista = ce.listarEmpleados(estatus);
            out = new Gson().toJson(lista);
        } catch (Exception e) {
            out = "{\"error\": \"Error al obtener los empleados.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("insertar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertar(String datosEmpleado) {
        Empleado e = null;
        ControllerEmpleados ce = new ControllerEmpleados();
        String out;
        Gson gson = new Gson();
        try {
            e = gson.fromJson(datosEmpleado, Empleado.class);
            ce.insertarEmpleado(e);
            out = gson.toJson(e);
        } catch (Exception ex) {
            out = "{\"error\": \"Error al insertar empleado.\"}";
            ex.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("modificar")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificar(String datosEmpleado) {
        Empleado e = null;
        ControllerEmpleados ce = new ControllerEmpleados();
        String out;
        Gson gson = new Gson();
        try {
            e = gson.fromJson(datosEmpleado, Empleado.class);
            ce.actualizarEmpleado(e);
            out = gson.toJson(e);
        } catch (Exception ex) {
            out = "{\"error\": \"Error al modificar empleado.\"}";
            ex.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("eliminar/{idPersona}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminar(@PathParam("idPersona") int idPersona) {
        String out;
        ControllerEmpleados ce = new ControllerEmpleados();
        try {
            ce.eliminarEmpleadoLogico(idPersona);
            out = "{\"mensaje\":\"Empleado eliminado correctamente\"}";
        } catch (Exception e) {
            out = "{\"error\": \"Error al eliminar empleado.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("buscar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(String filtro) {
        String out;
        try {
            Gson gson = new Gson();
            ControllerEmpleados ce = new ControllerEmpleados();
            List<Empleado> resultados = ce.buscarEmpleados(filtro);
            out = gson.toJson(resultados);
        } catch (Exception e) {
            out = "{\"error\": \"Error al buscar empleados.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}