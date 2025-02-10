package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.content.Intent;

import java.util.List;

public class BasketActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Basket basket;
    private BasketAdapter basketAdapter;
    private TextView totalPriceTextView;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        recyclerView = findViewById(R.id.basket_recyclerview);
        // Get the shared Basket instance
        basket = Basket.getInstance();
        List<BasketItem> basketItems = basket.getBasketItems();



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView = findViewById(R.id.basket_recyclerview);



        basketAdapter = new BasketAdapter(this, basketItems);
        recyclerView.setAdapter(basketAdapter);

        totalPriceTextView = findViewById(R.id.total_price_text_view);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Update total price initially
        updateTotalPrice();

        // Set listener to update total price whenever data changes
        basketAdapter.setOnDataChangedListener(this::updateTotalPrice);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasketActivity.this, BankDetailsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


        // Refresh the RecyclerView when returning to this activity
        basketAdapter.notifyDataSetChanged();

        // Update the total price
        updateTotalPrice();
    }

    // update the total price
    private void updateTotalPrice() {
        double total = 0.0;

        // Loop through the basket items and add up their prices
        for (BasketItem item : basket.getBasketItems()) {
            total += item.getPrice();  // The price reflects any quantity changes
        }

        // Update the TextView to display the formatted total price
        totalPriceTextView.setText("Total: £" + String.format("%.2f", total));
    }
}
