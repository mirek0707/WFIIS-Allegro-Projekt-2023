package pl.flashgoal.payment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.flashgoal.payment.payload.request.PaymentRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = PaymentRequest.class)
public class PaymentRequestTests
{
    @Test
    public void test_createPaymentRequestWithRequiredFields() {
        PaymentRequest req = new PaymentRequest();
        req.setPayerName("Test Payer");
        req.setCreditCardNumber("Test");
        req.setAmount(10.0);

        assertEquals("Test Payer", req.getPayerName());
        assertEquals("Test", req.getCreditCardNumber());
        assertEquals(10.0, req.getAmount());
    }
}
