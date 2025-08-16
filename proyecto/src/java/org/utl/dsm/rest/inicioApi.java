
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
import org.utl.dsm.huellas.modelo.Animales;
import org.utl.dsm.huellas.control.inicioControl;
@Path("inicio")
public class inicioApi extends Application{
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        String out = null;
        List<Animales> lista;
        inicioControl a = new inicioControl();
        try {
            lista = a.getAll(out);
            out = new Gson().toJson(lista);
        } catch (Exception e) {
            out = "{\"error\": \"Error al obtener los animales.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
     @Path("buscarAnimal")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(String datosAnimal) {
        String out;
        try {
            Gson gson = new Gson();
            Animales a = gson.fromJson(datosAnimal, Animales.class);
            inicioControl cc = new inicioControl();

            List<Animales> resultados = cc.busquedaAnimal(a.getNombreAnimal());
            out = gson.toJson(resultados);

        } catch (Exception e) {
            out = "{\"error\": \"Error al buscar animal.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    @Path("buscarPerro")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPerro(String datosAnimal) {
        String out;
        try {
            Gson gson = new Gson();
            Animales a = gson.fromJson(datosAnimal, Animales.class);
            inicioControl cc = new inicioControl();

            List<Animales> resultados = cc.busquedaPerros(a.getNombreAnimal());
            out = gson.toJson(resultados);

        } catch (Exception e) {
            out = "{\"error\": \"Error al buscar animal.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    @Path("buscarGatos")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarGatos(String datosAnimal) {
        String out;
        try {
            Gson gson = new Gson();
            Animales a = gson.fromJson(datosAnimal, Animales.class);
            inicioControl cc = new inicioControl();

            List<Animales> resultados = cc.busquedaGatos(a.getNombreAnimal());
            out = gson.toJson(resultados);

        } catch (Exception e) {
            out = "{\"error\": \"Error al buscar animal.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("filtroPerros")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtroEspecie(String filtracion) {
        Gson gson = new Gson();
        String out;

        try {
            Animales filtro = gson.fromJson(filtracion, Animales.class);
            String especie = filtro.getEspecie();
            inicioControl control = new inicioControl();
            List<Animales> lista = control.filtrarPerros(filtro);
            out = gson.toJson(lista);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\": \"" + e.getMessage() + "\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
        @Path("filtroTodos")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtroTodos(String filtracion) {
        Gson gson = new Gson();
        String out;

        try {
            Animales filtro = gson.fromJson(filtracion, Animales.class);
            String especie = filtro.getEspecie();
            inicioControl control = new inicioControl();
            List<Animales> lista = control.filtrarTodos(filtro);
            out = gson.toJson(lista);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\": \"" + e.getMessage() + "\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
        @Path("filtroGatos")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtroGato(String filtracion) {
        Gson gson = new Gson();
        String out;

        try {
            Animales filtro = gson.fromJson(filtracion, Animales.class);
            String especie = filtro.getEspecie();
            inicioControl control = new inicioControl();
            List<Animales> lista = control.filtrarGatos(filtro);
            out = gson.toJson(lista);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\": \"" + e.getMessage() + "\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
}
