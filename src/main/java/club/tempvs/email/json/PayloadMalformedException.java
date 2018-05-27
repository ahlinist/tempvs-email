package club.tempvs.email.json;

public class PayloadMalformedException extends RuntimeException {

    public PayloadMalformedException(String message) {
        super(message);
    }
}
