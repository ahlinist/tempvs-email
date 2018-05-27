package club.tempvs.email.api;

import club.tempvs.email.auth.AuthenticationException;
import club.tempvs.email.EmailParameterHelper;
import club.tempvs.email.EmailService;
import club.tempvs.email.json.Payload;
import club.tempvs.email.json.PayloadMalformedException;

import javax.mail.Session;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api")
public class Api {

    private EmailParameterHelper emailParameterHelper = new EmailParameterHelper();

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
        String tokenHash = emailParameterHelper.getTokenHash();
        String smtpUsername = emailParameterHelper.getSmtpUserName();
        Session emailSession = emailParameterHelper.getEmailSession();
        EmailService emailService = new EmailService(tokenHash, smtpUsername, emailSession);

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