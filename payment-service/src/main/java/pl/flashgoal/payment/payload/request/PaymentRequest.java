package pl.flashgoal.payment.payload.request;
public class PaymentRequest {

    private String payerName;
    private String creditCardNumber;
    private double amount;

    public PaymentRequest() {}

    public PaymentRequest(String payerName, String creditCardNumber, double amount) {
        this.payerName = payerName;
        this.creditCardNumber = creditCardNumber;
        this.amount = amount;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
