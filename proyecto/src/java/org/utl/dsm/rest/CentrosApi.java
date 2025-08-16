package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.utl.dsm.huellas.control.centroControl;
import org.utl.dsm.huellas.modelo.Centros;

@Path("centros")
public class CentrosApi extends Application {

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        String out;
        List<Centros> lista;
        centroControl control = new centroControl();
        try {
            lista = control.getAll(null);
            out = new Gson().toJson(lista);
        } catch (Exception e) {
            out = "{\"error\": \"Error al obtener los centros.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("insert")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCentro(String json) {
        String out;
        Gson gson = new Gson();
        Centros centro = gson.fromJson(json, Centros.class);
        centroControl control = new centroControl();

        try {
            control.insertarCentro(centro);
            out = "{\"result\":\"Centro insertado correctamente.\"}";
        } catch (Exception e) {
            out = "{\"error\":\"Error al insertar centro: " + e.getMessage() + "\"}";
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("update")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCentro(String json) {
        String out;
        Gson gson = new Gson();
        Centros centro = gson.fromJson(json, Centros.class);
        centroControl control = new centroControl();

        try {
            control.actualizarCentro(centro);
            out = "{\"result\":\"Centro actualizado correctamente.\"}";
        } catch (Exception e) {
            out = "{\"error\":\"Error al actualizar centro: " + e.getMessage() + "\"}";
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
    @Path("delete")
@DELETE
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response deleteCentro(String json) {
    String out;
    Gson gson = new Gson();
    Centros centro = gson.fromJson(json, Centros.class);
    centroControl control = new centroControl();

    try {
        control.eliminarCentro(centro.getIdCentro());
        out = "{\"result\":\"Centro eliminado correctamente.\"}";
    } catch (Exception e) {
        out = "{\"error\":\"Error al eliminar centro: " + e.getMessage() + "\"}";
        e.printStackTrace();
    }

    return Response.status(Response.Status.OK).entity(out).build();
}

    @Path("countActivos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response contarActivos() {
        String out;
        centroControl control = new centroControl();
        try {
            int total = control.contarCentrosActivos();
            out = "{\"total_activos\": " + total + "}";
        } catch (Exception e) {
            out = "{\"error\":\"Error al contar centros activos: " + e.getMessage() + "\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    // 🔹 Nuevo endpoint: contar inactivos
    @Path("countInactivos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response contarInactivos() {
        String out;
        centroControl control = new centroControl();
        try {
            int total = control.contarCentrosInactivos();
            out = "{\"total_inactivos\": " + total + "}";
        } catch (Exception e) {
            out = "{\"error\":\"Error al contar centros inactivos: " + e.getMessage() + "\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
   
     @Path("getNombres")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNombres() {
        String out = null;
        List<Centros> lista;
        centroControl a = new centroControl();
        try {
            lista = a.getNombresCentros();
            out = new Gson().toJson(lista);
        } catch (Exception e) {
            out = "{\"error\": \"Error al obtener los centros.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}