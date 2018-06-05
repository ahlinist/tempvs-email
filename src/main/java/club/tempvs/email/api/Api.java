package club.tempvs.email.api;

import club.tempvs.email.EmailServiceFactory;
import club.tempvs.email.auth.AuthenticationException;
import club.tempvs.email.EmailService;
import club.tempvs.email.json.Payload;
import club.tempvs.email.json.PayloadMalformedException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api")
public class Api {

    private EmailService emailService = EmailServiceFactory.getInstance();

    @HeaderParam("token")
    private String token;

    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPong() {
        return "pong!";
    }

    @POST
    @Path("send")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendEmail(Payload payload) {
        try {
            emailService.doSend(payload, token);
            return Response.ok().build();
        } catch (AuthenticationException e) {
            return Response.status(401).build();
        } catch (PayloadMalformedException e) {
            return Response.status(400, e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
