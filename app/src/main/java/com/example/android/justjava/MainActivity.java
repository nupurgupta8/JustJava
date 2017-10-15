package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    int price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String name = addName();
        boolean hasWhippedCream = verifyIfHasWhippedCream();
        boolean hasChocolate = verifyIfHasChocolate();
        calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(hasWhippedCream, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java Order for:" + name);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private boolean verifyIfHasWhippedCream(){
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.checbox_whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        return hasWhippedCream;
    }

    private boolean verifyIfHasChocolate(){
        CheckBox chocolateToppingCheckbox = (CheckBox) findViewById(R.id.checbox_chocholate_topping);
        boolean hasChocholate = chocolateToppingCheckbox.isChecked();
        return hasChocholate;
    }

    private String addName(){
        EditText text = (EditText) findViewById(R.id.add_name_view);
        String name = text.getText().toString();
        Log.v("MainActivity", "Name:" + name);
        return name;
    }

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocholate) {
        // price of 1 cup of coffee
        int basePrice = 5;
        // Add $1 if the user wants whipped cream
        if (hasWhippedCream){
            basePrice = basePrice + 1;
        }
        if (hasChocholate){
            basePrice = basePrice + 2;
        }
        return price = quantity * basePrice;
    }

    private String createOrderSummary(boolean hasWhippedCream, boolean hasChocholate, String name){
        String priceMessage = "Name:" + name;
        priceMessage = priceMessage + "\n Quantity: " + quantity;
        priceMessage = priceMessage + "\n Add Whipped Cream?" + hasWhippedCream;
        priceMessage = priceMessage + "\n Add Chocolate?" + hasChocholate;
        priceMessage = priceMessage + "\n Total: $" + price;
        priceMessage = priceMessage + "\n Thank You";
        return priceMessage;
    }


}