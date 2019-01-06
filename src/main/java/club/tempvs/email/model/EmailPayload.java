package club.tempvs.email.model;

import lombok.Data;

@Data
public class EmailPayload {
    private String email;
    private String subject;
    private String body;
}
