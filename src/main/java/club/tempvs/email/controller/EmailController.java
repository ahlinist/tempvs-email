package club.tempvs.email.controller;

import club.tempvs.email.api.UnauthorizedException;
import club.tempvs.email.model.EmailPayload;
import club.tempvs.email.service.EmailService;
import club.tempvs.email.util.AuthHelper;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailController.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final AuthHelper authHelper;
    private final EmailService emailService;

    @GetMapping("/ping")
    public String ping() {
        return "pong!";
    }

    @PostMapping("/send")
    public ResponseEntity send(
            @RequestHeader(value = AUTHORIZATION_HEADER, required = false) String token,
            @RequestBody EmailPayload emailPayload) throws IOException {
        authHelper.authenticate(token);
        String email = emailPayload.getEmail();
        String subject = emailPayload.getSubject();
        String body = emailPayload.getBody();
        emailService.send(email, subject, body);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String throwUnauthorizedException(UnauthorizedException e) {
        return processException(e);
    }

    @ExceptionHandler(HystrixRuntimeException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public String throwTimeOutException(HystrixRuntimeException e) {
        return processException(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String throwException(Exception e) {
        return processException(e);
    }

    private String processException(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTraceString = sw.toString();
        LOGGER.error(stackTraceString);
        return e.getMessage();
    }
}
