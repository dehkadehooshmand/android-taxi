package ir.idpz.taxi.user.Models;

public class PaymentTrackModel {
    String message;

    public PaymentTrackModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
