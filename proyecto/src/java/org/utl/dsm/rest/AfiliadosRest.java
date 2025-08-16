
package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.utl.dsm.huellas.control.AfiliacionControl;
import org.utl.dsm.huellas.modelo.Afiliacion;

@Path("Afiliacion")
public class AfiliadosRest {

    // Obtener todos
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAfiliados() {
        Gson gson = new Gson();
        String out;
        try {
            List<Afiliacion> lista = new AfiliacionControl().getAllAfiliados();
            out = gson.toJson(lista);
        } catch (Exception e) {
            out = "{\"error\":\"" + e.getMessage() + "\"}";
        }
        return Response.ok(out).build();
    }

    // Eliminar
    @POST
    @Path("eliminar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarAfiliado(@FormParam("idAfiliado") int idAfiliado) {
        String out;
        try {
            new AfiliacionControl().eliminarAfiliado(idAfiliado);
            out = "{\"result\":\"Afiliado eliminado correctamente\"}";
        } catch (Exception e) {
            out = "{\"error\":\"" + e.getMessage() + "\"}";
        }
        return Response.ok(out).build();
    }

    // Modificar
    @POST
    @Path("modificar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificar(@FormParam("datos") String datos) {
        try {
            Afiliacion ap = new Gson().fromJson(datos, Afiliacion.class);
            new AfiliacionControl().modificarAfiliadoPersona(ap);
            return Response.ok("{\"status\":\"Modificado Exitosamente\"}").build();
        } catch(Exception e) {
            return Response.ok("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
    
     @POST
    @Path("agregar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregar(@FormParam("datos") String datos) {
        try {
            Afiliacion afiliado = new Gson().fromJson(datos, Afiliacion.class);
            new AfiliacionControl().agregarAfiliado(afiliado);
            return Response.ok("{\"status\":\"ok\"}").build();
        } catch (Exception ex) {
            return Response.ok("{\"error\":\"" + ex.getMessage() + "\"}").build();
        }
    }
    
  @POST
@Path("buscar")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public Response buscarAfiliados(@FormParam("texto") String texto) {
    Gson gson = new Gson();
    String out;
    try {
        List<Afiliacion> lista = new AfiliacionControl().buscarAfiliadosPorNombreOCorreo(texto);
        out = gson.toJson(lista);
    } catch (Exception e) {
        out = "{\"error\":\"" + e.getMessage() + "\"}";
    }
    return Response.ok(out).build();
}


}
