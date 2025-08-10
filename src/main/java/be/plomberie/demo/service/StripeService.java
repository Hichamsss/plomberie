package be.plomberie.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    // URLs paramétrées en dur ici pour aller vite — tu peux les externaliser ensuite
    private static final String SUCCESS_URL = "http://localhost:8080/urgence?success=true&session_id={CHECKOUT_SESSION_ID}";
    private static final String CANCEL_URL  = "http://localhost:8080/urgence?canceled=true";

    public Session creerSessionPaiementUrgenceAvecMetadata(Long urgenceId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(SUCCESS_URL)
            .setCancelUrl(CANCEL_URL)
            .putMetadata("urgenceId", String.valueOf(urgenceId))
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("eur")
                            .setUnitAmount(10000L) // 100€ en centimes
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName("Acompte intervention urgente")
                                    .build()
                            ).build()
                    ).build()
            ).build();

        return Session.create(params);
    }
}
