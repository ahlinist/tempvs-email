package club.tempvs.email;

import club.tempvs.email.model.EmailPayload;
import club.tempvs.rest.auth.AuthenticationException;
import club.tempvs.rest.model.PayloadMalformedException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api")
public class EmailApi {

    private EmailService emailService = EmailServiceFactory.getInstance();

    @HeaderParam("Authorization")
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
    public Response sendEmail(EmailPayload payload) {
        try {
            emailService.doSend(payload, token);
            return Response.ok().build();
        } catch (AuthenticationException e) {
            return Response.status(401).build();
        } catch (PayloadMalformedException e) {
            return Response.status(400, e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
