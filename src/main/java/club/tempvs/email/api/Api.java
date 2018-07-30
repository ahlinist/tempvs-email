package club.tempvs.email.api;

import club.tempvs.email.EmailServiceFactory;
import club.tempvs.email.auth.AuthenticationException;
import club.tempvs.email.EmailService;
import club.tempvs.email.json.Payload;
import club.tempvs.email.json.PayloadMalformedException;
import com.sendgrid.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

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

            Email from = new Email("test@tempvs.club");
            String subject = "Hello World from the SendGrid Java Library!";
            Email to = new Email("anton.hlinisty@gmail.com");
            Content content = new Content("text/plain", "Hello, Email!");
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
            Request request = new Request();
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                com.sendgrid.Response response = sg.api(request);
                System.out.println(response.getStatusCode());
                System.out.println(response.getBody());
                System.out.println(response.getHeaders());
            } catch (IOException ex) {
                throw ex;
            }

            //emailService.doSend(payload, token);
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
