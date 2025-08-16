
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
import org.utl.dsm.huellas.modelo.Solicitudes;
import org.utl.dsm.huellas.control.solicitudesControl;
@Path("solicitudes")
public class solicitudesApi extends Application {
     @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        String out = null;
        List<Solicitudes> lista;
        solicitudesControl a = new solicitudesControl();
        try {
            lista = a.getAll(out);
            out = new Gson().toJson(lista);
        } catch (Exception e) {
            out = "{\"error\": \"Error al obtener las solicitudes.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    @Path("getTodas")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodas() {
        String out = null;
        List<Solicitudes> lista;
        solicitudesControl a = new solicitudesControl();
        try {
            lista = a.getSolicitudes(out);
            out = new Gson().toJson(lista);
        } catch (Exception e) {
            out = "{\"error\": \"Error al obtener las solicitudes.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
     @Path("saveSolicitud")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveA(String datosSoli) {
        Solicitudes c = null;
       solicitudesControl cc = new solicitudesControl();
        String out;
        Gson gson = new Gson();
        try {
            c = gson.fromJson(datosSoli, Solicitudes.class);
            if (c.getIdSolicitud()< 1) {
                cc.insertarSoli(c);
            } else {
                System.out.println("No se insertó porque ya tiene ID");
            }
            out = gson.toJson(c);
        } catch (Exception e) {
            out = "{\"error\": \"Error al insertar la soli.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    @Path("rechazarSoli")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response rechazarS(String datosSoli) {
        Solicitudes c = null;
        solicitudesControl cc = new solicitudesControl();
        String out;
        Gson gson = new Gson();
        try {
            c = gson.fromJson(datosSoli, Solicitudes.class);
            if (c.getIdSolicitud()> 0) {
                cc.rechazarSolicitud(c);
            } else {
                System.out.println("No se rechazo");
            }
            out = gson.toJson(c);
        } catch (Exception e) {
            out = "{\"error\": \"Error mano.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
     @Path("aceptarSoli")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response aceptarS(String datosSoli) {
        Solicitudes c = null;
        solicitudesControl cc = new solicitudesControl();
        String out;
        Gson gson = new Gson();
        try {
            c = gson.fromJson(datosSoli, Solicitudes.class);
            if (c.getIdSolicitud() > 0) {
                cc.aceptarSolicitud(c);
            } else {
                System.out.println("No se rechazo");
            }
            out = gson.toJson(c);
        } catch (Exception e) {
            out = "{\"error\": \"Error mano.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
     @Path("eliminarSoli")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarS(String datosSoli) {
        Solicitudes c = null;
        solicitudesControl cc = new solicitudesControl();
        String out;
        Gson gson = new Gson();
        try {
            c = gson.fromJson(datosSoli, Solicitudes.class);
            if (c.getIdSolicitud() > 0) {
                cc.eliminarSoli(c);
            } else {
                System.out.println("No se rechazo");
            }
            out = gson.toJson(c);
        } catch (Exception e) {
            out = "{\"error\": \"Error mano.\"}";
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
           solicitudesControl ctrl = new solicitudesControl();
            int total = ctrl.contarDisponibles();
            out = String.valueOf(total);  
        } catch (Exception e) {
            out = "-1";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
        @Path("contarAceptadas")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAceptadas() {
        String out;
        try {
           solicitudesControl ctrl = new solicitudesControl();
            int total = ctrl.contarAceptar();
            out = String.valueOf(total);  
        } catch (Exception e) {
            out = "-1";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
        @Path("contarRechazadas")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRechazadas() {
        String out;
        try {
           solicitudesControl ctrl = new solicitudesControl();
            int total = ctrl.contarRechazar();
            out = String.valueOf(total);  
        } catch (Exception e) {
            out = "-1";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
  @Path("filtroEstatus")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtroEstatus(String filtracion) {
        Gson gson = new Gson();
        String out;

        try {
            Solicitudes filtro = gson.fromJson(filtracion, Solicitudes.class);
            int Estatus = filtro.getEstatus();
            solicitudesControl control = new solicitudesControl();
            List<Solicitudes> lista = control.filtrarPorEstatus(Estatus);
            out = gson.toJson(lista);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\": \"" + e.getMessage() + "\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
    @Path("buscarSoli")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(String datosSoli) {
        String out;
        try {
            Gson gson = new Gson();
             Solicitudes a = gson.fromJson(datosSoli, Solicitudes.class);
             String textoBusqueda = null;

    if (a.getNombreAnimal() != null && !a.getNombreAnimal().isEmpty()) {
    textoBusqueda = a.getNombreAnimal();
} else if (a.getNombreAdoptante() != null && !a.getNombreAdoptante().isEmpty()) {
    textoBusqueda = a.getNombreAdoptante();
}
            solicitudesControl cc = new solicitudesControl();

            List<Solicitudes> resultados = cc.buscarSoli(textoBusqueda);
            out = gson.toJson(resultados);

        } catch (Exception e) {
            out = "{\"error\": \"Error al buscar animal.\"}";
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
