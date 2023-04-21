package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.loginactivity.Adapters.PlaceYourOrderAdapter;
import com.example.loginactivity.Model.Menu;
import com.example.loginactivity.Model.Order;
import com.example.loginactivity.Model.StoreModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlaceYourOrderActivity extends AppCompatActivity {
    private EditText inputName, inputAddress, inputCity, inputState, inputZip;
    private RecyclerView cartItemsRecyclerView;
    private TextView tvSubtotalAmount, tvDeliveryChargeAmount, tvDeliveryCharge, tvTotalAmount, buttonPlaceYourOrder;
    private SwitchCompat switchDelivery;
    private boolean isDeliveryOn;
    private PlaceYourOrderAdapter placeYourOrderAdapter;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_your_order);

        StoreModel storeModel = getIntent().getParcelableExtra("RestaurantModel");

        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputCity = findViewById(R.id.inputCity);
        inputState = findViewById(R.id.inputState);
        inputZip = findViewById(R.id.inputZip);
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);
        switchDelivery = findViewById(R.id.switchDelivery);

        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceOrderButtonClick(storeModel);
            }
        });

        switchDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    inputAddress.setVisibility(View.VISIBLE);
                    inputCity.setVisibility(View.VISIBLE);
                    inputState.setVisibility(View.VISIBLE);
                    inputZip.setVisibility(View.VISIBLE);
                    tvDeliveryChargeAmount.setVisibility(View.VISIBLE);
                    tvDeliveryCharge.setVisibility(View.VISIBLE);
                    isDeliveryOn = true;
                    calculateTotalAmount(storeModel);
                } else {
                    inputAddress.setVisibility(View.GONE);
                    inputCity.setVisibility(View.GONE);
                    inputState.setVisibility(View.GONE);
                    inputZip.setVisibility(View.GONE);
                    tvDeliveryChargeAmount.setVisibility(View.GONE);
                    tvDeliveryCharge.setVisibility(View.GONE);
                    isDeliveryOn = false;
                    calculateTotalAmount(storeModel);
                }
            }
        });
        initRecyclerView(storeModel);
        calculateTotalAmount(storeModel);
    }

    private void calculateTotalAmount(StoreModel storeModel) {
        float subTotalAmount = 0f;

        for (Menu m : storeModel.getMenus()) {
            subTotalAmount += m.getPrice() * m.getTotalInCart();
        }

        tvSubtotalAmount.setText("Rs." + String.format("%.2f", subTotalAmount));
        if (isDeliveryOn) {
            tvDeliveryChargeAmount.setText("Rs." + String.format("%.2f", storeModel.getDelivery_charge()));
            subTotalAmount += storeModel.getDelivery_charge();
        }
        tvTotalAmount.setText("Rs." + String.format("%.2f", subTotalAmount));
    }

    private void onPlaceOrderButtonClick(StoreModel storeModel) {
        if (TextUtils.isEmpty(inputName.getText().toString())) {
            inputName.setError("Please enter name ");
            return;
        } else if (isDeliveryOn && TextUtils.isEmpty(inputAddress.getText().toString())) {
            inputAddress.setError("Please enter address ");
            return;
        } else if (isDeliveryOn && TextUtils.isEmpty(inputCity.getText().toString())) {
            inputCity.setError("Please enter city ");
            return;
        } else if (isDeliveryOn && TextUtils.isEmpty(inputState.getText().toString())) {
            inputState.setError("Please enter zip ");
            return;
        }

        // Store the data into Firebase Realtime Database
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
        String orderId = ordersRef.push().getKey();

        Order order = new Order(
                orderId,
                inputName.getText().toString(),
                inputAddress.getText().toString(),
                inputCity.getText().toString(),
                inputState.getText().toString(),
                inputZip.getText().toString(),
                tvSubtotalAmount.getText().toString(),
                tvDeliveryChargeAmount.getText().toString(),
                tvTotalAmount.getText().toString(),
                storeModel.getMenus(),
                isDeliveryOn,
                storeModel.getDelivery_charge()
        );

        ordersRef.child(orderId).setValue(order);

        // Start success activity
        Intent i = new Intent(PlaceYourOrderActivity.this, OrderSuccessActivity.class);
        i.putExtra("StoreModel", storeModel);
        startActivityForResult(i, 1000);
    }

    private void initRecyclerView(StoreModel storeModel) {
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeYourOrderAdapter = new PlaceYourOrderAdapter(storeModel.getMenus());
        cartItemsRecyclerView.setAdapter(placeYourOrderAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}