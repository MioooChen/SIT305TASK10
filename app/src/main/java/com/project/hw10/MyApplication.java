package com.project.hw10;

import android.app.Application;

import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.PaymentButtonIntent;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.config.UIConfig;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

public class MyApplication extends Application {

    private static final String CLIENT_ID = "ATyJ9WYzDw4m1-81MXUkqOvpWqrzQNRzgbhxX79GLyvoN-Udvi2b-eKAa6SvBF1-W7pE5CUKJxj76bZ3";
    private static final String PACKAGE_NAME = "com.project.hw10";


    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println(PACKAGE_NAME + "://paypalpay");
        PayPalCheckout.setConfig(new CheckoutConfig(
                this,
                CLIENT_ID,
                Environment.SANDBOX,
                CurrencyCode.USD,
                UserAction.PAY_NOW,
                PaymentButtonIntent.CAPTURE,
                new SettingsConfig(true, false),
                new UIConfig(true),
                PACKAGE_NAME + "://paypalpay"
        ));
    }
}
