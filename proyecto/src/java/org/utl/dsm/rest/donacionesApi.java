
package org.utl.dsm.rest;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
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
import org.utl.dsm.huellas.modelo.Donaciones;
import org.utl.dsm.huellas.control.ControllerDonaciones;
import org.utl.dsm.huellas.control.centroControl;
import org.utl.dsm.huellas.modelo.Centros;
@Path("donacion")
public class donacionesApi extends Application{
    @Path("saveDonacion")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response guardarDonacion(@FormParam("datosDonacion") @DefaultValue("") String datosDonacion) {
        String out;
        Donaciones donacion;
        ControllerDonaciones controller;
        Gson gson = new Gson();

        try {
            // Convertimos el JSON recibido en un objeto Donaciones
            donacion = gson.fromJson(datosDonacion, Donaciones.class);

            controller = new ControllerDonaciones();

            // Insertar la donación (en el método generar se asigna fecha automáticamente y id_centro fijo)
            if (donacion.getIdDonacion() < 1) {
                controller.generarD(donacion);
            } else {
                // Aquí puedes manejar actualizaciones si decides implementarlas
                System.out.println("Actualización no implementada aún");
            }

            // Convertimos el objeto donación a JSON para la respuesta
            out = gson.toJson(donacion);

        } catch (JsonParseException jpe) {
            out = "{\"error\":\"formato de datos no válido\"}";
            jpe.printStackTrace();
            System.out.println(jpe);

        } catch (Exception e) {
            out = "{\"error\":\"error interno del servidor, intente más tarde\"}";
            e.printStackTrace();
            System.out.println(e);
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    
    
    
     @Path("getDonaciones")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDonaciones() {
        String out = null;
        List<Donaciones> lista;
        ControllerDonaciones a = new ControllerDonaciones();
        try {
            lista = a.getDonaciones();
            out = new Gson().toJson(lista);
        } catch (Exception e) {
            out = "{\"error\": \"Error al obtener las donaciones.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
     
     @Path("getContDonaciones")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContDonaciones() {
        String out = null;
        List<Donaciones> lista;
        ControllerDonaciones a = new ControllerDonaciones();
        try {
            lista = a.getCount();
            out = new Gson().toJson(lista);
        } catch (Exception e) {
            out = "{\"error\": \"Error al obtener la counteneishon .\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
       @Path("getTotal")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotal() {
        String out = null;
        List<Donaciones> lista;
        ControllerDonaciones a = new ControllerDonaciones();
        try {
            lista = a.getTotal();
            out = new Gson().toJson(lista);
        } catch (Exception e) {
            out = "{\"error\": \"Error al obtener la counteneishon .\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
      @Path("contarDonaciones")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisponibles() {
        String out;
        try {
            ControllerDonaciones ctrl = new ControllerDonaciones();
            int total = ctrl.contarDisponibles();
            out = String.valueOf(total);  
        } catch (Exception e) {
            out = "-1";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
       @Path("sumaDonaciones")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response SumaDonacion() {
        String out;
        try {
            ControllerDonaciones ctrl = new ControllerDonaciones();
            int total = ctrl.sumaDonaciones();
            out = String.valueOf(total);  
        } catch (Exception e) {
            out = "-1";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
     @Path("todasDonaciones")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response todasDonaciones() {
        String out = null;
        List<Donaciones> lista;
        ControllerDonaciones a = new ControllerDonaciones();
        try {
            lista = a.todasDonacioneses();
            out = new Gson().toJson(lista);
        } catch (Exception e) {
            out = "{\"error\": \"Error al obtener las donaciones.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
   
                }

