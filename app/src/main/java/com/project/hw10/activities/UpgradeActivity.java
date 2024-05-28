package com.project.hw10.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.OrderRequest;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PayPalButtonColor;
import com.paypal.checkout.paymentbutton.PayPalButtonLabel;
import com.paypal.checkout.paymentbutton.PayPalButtonUi;
import com.paypal.checkout.paymentbutton.PaymentButtonAttributes;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;
import com.paypal.checkout.paymentbutton.PaymentButtonShape;
import com.paypal.checkout.paymentbutton.PaymentButtonSize;
import com.project.hw10.R;

import java.util.ArrayList;

public class UpgradeActivity extends AppCompatActivity {

    private static final String TAG = "PAYMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());


        PaymentButtonContainer paymentButtonContainer1 = findViewById(R.id.payment_button_container1);
        paymentButtonContainer1.setPayPalButtonUi(
                new PayPalButtonUi(
                        PayPalButtonColor.SILVER,
                        PayPalButtonLabel.CHECKOUT,
                        new PaymentButtonAttributes(
                                PaymentButtonShape.ROUNDED,
                                PaymentButtonSize.LARGE,
                                true
                        )
                )
        );
        paymentButtonContainer1.setup(
                createOrderActions -> {
                    ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                    purchaseUnits.add(
                            new PurchaseUnit.Builder()
                                    .description("starter")
                                    .amount(
                                            new Amount.Builder()
                                                    .currencyCode(CurrencyCode.USD)
                                                    .value("10.00")
                                                    .build()
                                    ).build()
                    );
                    OrderRequest order = new OrderRequest(
                            OrderIntent.CAPTURE,
                            new AppContext.Builder()
                                    .userAction(UserAction.PAY_NOW)
                                    .build(),
                            purchaseUnits
                    );
                    createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                },
                approval ->  {
                    Log.i(TAG, "orderId: " + approval.getData().getOrderId());
                },
                (shippingChangeData, shippingChangeActions) -> {
                    Log.i(TAG, "Shipping type change: " + shippingChangeData.getShippingChangeType());
                },
                () -> Log.i(TAG, "cancelled"),
                errorInfo -> Log.e(TAG, errorInfo.getReason(), errorInfo.getError())
        );

        PaymentButtonContainer paymentButtonContainer2 = findViewById(R.id.payment_button_container2);
        paymentButtonContainer2.setPayPalButtonUi(
                new PayPalButtonUi(
                        PayPalButtonColor.SILVER,
                        PayPalButtonLabel.CHECKOUT,
                        new PaymentButtonAttributes(
                                PaymentButtonShape.ROUNDED,
                                PaymentButtonSize.LARGE,
                                true
                        )
                )
        );

        PaymentButtonContainer paymentButtonContainer3 = findViewById(R.id.payment_button_container3);
        paymentButtonContainer3.setPayPalButtonUi(
                new PayPalButtonUi(
                        PayPalButtonColor.SILVER,
                        PayPalButtonLabel.CHECKOUT,
                        new PaymentButtonAttributes(
                                PaymentButtonShape.ROUNDED,
                                PaymentButtonSize.LARGE,
                                true
                        )
                )
        );

    }

    private void purchase(int amount) {

    }


}