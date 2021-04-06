package com.example.softwarepatternsca2.CustomerFeatures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softwarepatternsca2.ObjectClasses.Cart;
import com.example.softwarepatternsca2.ObjectClasses.Customer;
import com.example.softwarepatternsca2.ObjectClasses.StockItem;
import com.example.softwarepatternsca2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class PaymentPage extends AppCompatActivity {

    private FirebaseUser user;
    String uid;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    TextView t1, t2, t3, t4;
    EditText e1, e2, e3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        t1 = (TextView) findViewById(R.id.textViewItemTotal);
        t2 = (TextView) findViewById(R.id.textViewDiscount);
        t3 = (TextView) findViewById(R.id.textViewShipping);
        t4 = (TextView) findViewById(R.id.textViewTotal);

        e1 = (EditText) findViewById(R.id.editTextCardNumber);
        e2 = (EditText) findViewById(R.id.editTextExpiry);
        e3 = (EditText) findViewById(R.id.editTextCvv);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        getCart();
    }

    private void getCart() {

        ref.child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    Cart cart = child.getValue(Cart.class);
                    if (cart.getCustomer().getCustomerId().equals(uid) && cart.isActive()) {

                        //Check if user has saved card info, if so prefill
                        if ((!cart.getCustomer().getCardNumber().equalsIgnoreCase("cardnumber")) && (!cart.getCustomer().getCardExpiry().equalsIgnoreCase("cardexpiry"))
                                && (!cart.getCustomer().getSecurityCode().equalsIgnoreCase("securitycode"))) {
                            e1.setText(cart.getCustomer().getCardNumber());
                            e2.setText(cart.getCustomer().getCardExpiry());
                            e3.setText(cart.getCustomer().getSecurityCode());
                        }

                        //Get and calculate price
                        int totalOfItems = 0;
                        for (StockItem myItem : cart.getItems()) {
                            totalOfItems = totalOfItems + myItem.getPrice();
                        }
                        t1.setText("Price of Items : "+String.valueOf(totalOfItems));

                        int shipping = 0;
                        if (totalOfItems > 70) {
                            t3.setText("Shipping : Free Shipping over 70 Euro");
                        } else {
                            shipping = 10;
                            t3.setText("Shipping : 10");
                        }

                        double discount = 0.0;
                        if (!cart.getCustomer().isNewUserDiscountUsed()) {
                            t2.setText("Discount : 10% New User Discount Applied");
                            ref.child("Customer").child(uid).child("newUserDiscountUsed").setValue(true);
                            ref.child("Cart").child(cart.getCartId()).child("customer").child("newUserDiscountUsed").setValue(true);

                            discount = 0.9;
                        }

                        if(discount!=0.0){
                            int total = (int) ((totalOfItems + shipping) * discount);
                            t4.setText("Order Total :"+String.valueOf(total));
                        }else{
                            int total1=totalOfItems+shipping;
                            t4.setText("Order Total :"+String.valueOf(total1));

                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //   Log.m("DBE Error","Cancel Access DB");
            }
        });
    }

    public void pay(View view) {
        ref.child("Customer").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              //  Iterable<DataSnapshot> children = snapshot.getChildren();
                //for (DataSnapshot child : children) {
                    Customer customer = snapshot.getValue(Customer.class);
                   // if (customer.getCustomerId().equals(uid)) {

                        setCartInactive();
                        customer.setCardExpiry(e2.getText().toString());
                        customer.setCardNumber(e1.getText().toString());
                        customer.setSecurityCode(e3.getText().toString());
                        int numOrders=customer.getNumOfOrders();
                        numOrders=numOrders+1;
                        customer.setNumOfOrders(numOrders);
                        customer.setHasNewCart(false);
                        ref.child("Customer").child(uid).setValue(customer);


                        Toast.makeText(getApplicationContext(), "Order Complete, Thank You! ", Toast.LENGTH_SHORT).show();

                        Intent intent= new Intent(getApplicationContext(),WelcomeCustomer.class);
                        startActivity(intent);

                   // }

              //  }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //   Log.m("DBE Error","Cancel Access DB");
            }
        });


    }

    public void setCartInactive() {
        ref.child("Cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    Cart cart = child.getValue(Cart.class);
                    if (cart.isActive()) {

                        ViewCart.myAdapter.notifyDataSetChanged();
                         ref.child("Cart").child(cart.getCartId()).child("active").setValue(false);
                         ref.child("Customer").child(cart.getCustomer().getCustomerId()).child("hasNewCart").setValue(false);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //   Log.m("DBE Error","Cancel Access DB");
            }
        });
    }


}