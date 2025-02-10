package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    private final Context context;
    private final List<BasketItem> basketItems;

    private OnDataChangedListener onDataChangedListener;

    // Constructor for BasketAdapter
    public BasketAdapter(Context context, List<BasketItem> basketItems) {
        this.context = context;
        this.basketItems = basketItems;
    }

    // Set listener to notify when data changes (to update the total price)
    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.basket_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BasketItem basketItem = basketItems.get(position);
        Food food = basketItem.getFood();
        holder.foodName.setText(food.getName());
        holder.quantity.setText(basketItem.getQuantity());

        // Use Locale.UK to format the price to ensure the correct currency format
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK);
        holder.foodPrice.setText(currencyFormat.format(basketItem.getPrice()));

        // Remove item from basket when clicking "Remove"
        holder.removeButton.setOnClickListener(v -> {
            int adapterPosition = holder.getBindingAdapterPosition();  // Use getBindingAdapterPosition instead
            if (adapterPosition != RecyclerView.NO_POSITION) {  // Ensure valid position
                basketItems.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
                notifyItemRangeChanged(adapterPosition, basketItems.size());
                Toast.makeText(context, food.getName() + " removed from basket", Toast.LENGTH_SHORT).show();

                // Notify that data has changed so the total price can be updated
                if (onDataChangedListener != null) {
                    onDataChangedListener.onDataChanged();
                }
            }
        });

        // Update quantity when spinner selection changes
        holder.quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int pos, long id) {
                int adapterPosition = holder.getBindingAdapterPosition();  // Use getBindingAdapterPosition instead
                if (adapterPosition != RecyclerView.NO_POSITION) {  // Ensure valid position
                    String newQuantity = (String) parentView.getItemAtPosition(pos);
                    basketItem.setQuantity(newQuantity);  // Update quantity in the BasketItem
                    notifyItemChanged(adapterPosition);  // Notify that this item has been updated

                    // Notify that data has changed so the total price can be updated
                    if (onDataChangedListener != null) {
                        onDataChangedListener.onDataChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing when no item is selected
            }
        });
    }

    @Override
    public int getItemCount() {
        return basketItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, quantity, foodPrice;
        Spinner quantitySpinner;
        Button removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.basketItemName);
            quantity = itemView.findViewById(R.id.basketItemQuantity);
            foodPrice = itemView.findViewById(R.id.basketItemPrice);
            quantitySpinner = itemView.findViewById(R.id.quantityspinner);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }

    // Interface to notify data changes
    public interface OnDataChangedListener {
        void onDataChanged();
    }
}
