package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DeliveryActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<DeliveryItem> itemList;
    private DeliveryAdapter adapter;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        // Initialize the ListView and the list
        listView = findViewById(R.id.items);
        itemList = new ArrayList<>();

        // Generate a random number of delivery items between 5 and 50
        generateRandomItems(random.nextInt(46) + 5);

        // Set up the adapter for the ListView
        adapter = new DeliveryAdapter(this, itemList);
        listView.setAdapter(adapter);

        // Barcode button functionality
        findViewById(R.id.BarcodeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeliveryActivity.this, "Function not available right now.", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Order Completed button
        findViewById(R.id.completeBtn).setOnClickListener(v -> completeOrder());
    }

    // Complete order logic
    private void completeOrder() {
        Toast.makeText(this, "Sending Confirmation email. Logging out...", Toast.LENGTH_SHORT).show();

        // Navigate back to login screen
        Intent intent = new Intent(DeliveryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Generate random items
    private void generateRandomItems(int numItems) {
        String[] itemNames = {
                "Apples", "Bananas", "Oranges", "Grapes", "Strawberries", "Blueberries", "Chicken Breast",
                "Beef Steak", "Pork Chops", "Salmon Fillet", "Lamb Chops", "Ground Turkey", "Shrimp", "Carrots",
                "Broccoli", "Spinach", "Lettuce", "Cucumbers", "Tomatoes", "Potatoes", "Sweet Potatoes", "Eggplant",
                "Zucchini", "Mushrooms", "Onions", "Garlic", "Cabbage", "Peas", "Corn", "Peppers", "Brussels Sprouts",
                "Cherries", "Pomegranates", "Figs", "Grapefruit", "Pineapple", "Mango", "Avocados", "Limes",
                "Olives", "Feta Cheese", "Cheddar Cheese", "Yogurt", "Milk", "Butter", "Olive Oil", "Bacon",
                "Bread", "Pasta", "Rice", "Granola", "Cereal"
        };

        for (int i = 0; i < numItems; i++) {
            String itemName = itemNames[random.nextInt(itemNames.length)];
            String quantity = String.valueOf(random.nextInt(10) + 1);
            String expiryDate = generateRandomExpiryDate();

            // Add the random item to the list
            itemList.add(new DeliveryItem(itemName, quantity, expiryDate));
        }
    }

    // Generate a random expiry date
    private String generateRandomExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        int daysToAdd = random.nextInt(30) + 1;
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);

        Date expiryDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(expiryDate);
    }

    // DeliveryItem class
    public static class DeliveryItem {
        private String name;
        private String quantity;
        private String expiryDate;
        private boolean isChecked;

        public DeliveryItem(String name, String quantity, String expiryDate) {
            this.name = name;
            this.quantity = quantity;
            this.expiryDate = expiryDate;
            this.isChecked = false;
        }

        public String getName() {
            return name;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }

    // Adapter class for ListView
    public class DeliveryAdapter extends ArrayAdapter<DeliveryItem> {
        public DeliveryAdapter(DeliveryActivity context, ArrayList<DeliveryItem> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, parent, false);
            }

            // Get the current item
            final DeliveryItem item = getItem(position);

            // Set the item name, quantity, and expiry date
            TextView itemName = convertView.findViewById(R.id.itemName);
            TextView itemQuantity = convertView.findViewById(R.id.quantity);
            TextView itemExpiry = convertView.findViewById(R.id.itemExpiry);
            CheckBox itemCheckbox = convertView.findViewById(R.id.itemCheckbox);

            itemName.setText(item.getName());
            itemQuantity.setText(item.getQuantity());
            itemExpiry.setText(item.getExpiryDate());

            // Set the checkbox status
            itemCheckbox.setChecked(item.isChecked());

            // Handle checkbox toggle
            itemCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> item.setChecked(isChecked));

            return convertView;
        }
    }
}
