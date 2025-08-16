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
import org.utl.dsm.huellas.control.animalesControl;

@Path("mascotas")
public class AnimalesApi extends Application {

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        String out = null;
        List<Animales> lista;
        animalesControl a = new animalesControl();
        try {
            lista = a.getAll(out);
            out = new Gson().toJson(lista);
        } catch (Exception e) {
            out = "{\"error\": \"Error al obtener los animales.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
      @Path("getGatos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getG (){
        String out = null;
        List<Animales> lista;
        animalesControl a = new animalesControl();
        try {
            lista = a.getGerros(out);
            out = new Gson().toJson(lista);
        } catch (Exception e) {
            out = "{\"error\": \"Error al obtener los animales.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
      @Path("getPerros")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getP() {
        String out = null;
        List<Animales> lista;
        animalesControl a = new animalesControl();
        try {
            lista = a.getPerros(out);
            out = new Gson().toJson(lista);
        } catch (Exception e) {
            out = "{\"error\": \"Error al obtener los animales.\"}";
            e.printStackTrace();
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
            animalesControl control = new animalesControl();
            List<Animales> lista = control.filtrarAnimales(filtro);
            out = gson.toJson(lista);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\": \"" + e.getMessage() + "\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("saveAnimal")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveA(String datosAnimal) {
        Animales c = null;
        animalesControl cc = new animalesControl();
        String out;
        Gson gson = new Gson();
        try {
            c = gson.fromJson(datosAnimal, Animales.class);
            if (c.getIdAnimal() < 1) {
                cc.insertarAnimal(c);
            } else {
                System.out.println("No se insertó porque ya tiene ID");
            }
            out = gson.toJson(c);
        } catch (Exception e) {
            out = "{\"error\": \"Error al insertar animal.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("updateAnimal")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modA(String datosAnimal) {
        Animales c = null;
        animalesControl cc = new animalesControl();
        String out;
        Gson gson = new Gson();
        try {
            c = gson.fromJson(datosAnimal, Animales.class);
            if (c.getIdAnimal() > 0) {
                cc.modificarAanimal(c);
            } else {
                System.out.println("No se modifico");
            }
            out = gson.toJson(c);
        } catch (Exception e) {
            out = "{\"error\": \"Error al insertar animal.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("deleteAnimal")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarA(String datosAnimal) {
        Animales c = null;
        animalesControl cc = new animalesControl();
        String out;
        Gson gson = new Gson();
        try {
            c = gson.fromJson(datosAnimal, Animales.class);
            if (c.getIdAnimal() > 0) {
                cc.eliminarAanimal(c);
            } else {
                System.out.println("No se elimino");
            }
            out = gson.toJson(c);
        } catch (Exception e) {
            out = "{\"error\": \"Error al insertar animal.\"}";
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
            animalesControl cc = new animalesControl();

            List<Animales> resultados = cc.buscarAnimal(a.getNombreAnimal());
            out = gson.toJson(resultados);

        } catch (Exception e) {
            out = "{\"error\": \"Error al buscar animal.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }


    @Path("contarDisponibles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisponibles() {
        String out;
        try {
            animalesControl ctrl = new animalesControl();
            int total = ctrl.contarDisponibles();
            out = String.valueOf(total);  
        } catch (Exception e) {
            out = "-1";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("contarAdoptados")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdoptados() {
        String out;
        try {
            animalesControl ctrl = new animalesControl();
            int total = ctrl.contarAdoptados();
            out = String.valueOf(total);
        } catch (Exception e) {
            out = "-1";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}


