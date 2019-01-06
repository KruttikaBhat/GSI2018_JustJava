package com.example.android.justjava;

        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.CheckBox;
        import android.widget.Toast;

        import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=1;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox WhippedCream= findViewById(R.id.Whipped_cream_checkbox);
        CheckBox Chocolate= findViewById(R.id.Chocolate_check_box);
        EditText getName= findViewById(R.id.Name);
        boolean hasWhippedCream=WhippedCream.isChecked();
        boolean hasChocolate=Chocolate.isChecked();
        String Name=getName.getText().toString();
        int price=calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage=createOrderSummary(price,hasWhippedCream,hasChocolate,Name);
        String orderFor=getString(R.string.orderFor)+Name;
        composeMail(priceMessage,orderFor);

    }
    /**
     * Calculates the price of the order.
     *
     * quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean hasWhippedCream,boolean hasChocolate) {
        int base=5;
        if(hasWhippedCream){
            base+=1;
        }
        if(hasChocolate){
            base+=2;
        }
        return (quantity * base);
    }

    private String createOrderSummary(int price,boolean addWhippedCream,boolean addChocolate,String Name) {
        String summary=getString(R.string.orderSummaryName,Name);
        summary+=getString(R.string.orderSummaryWC,addWhippedCream);
        summary+=getString(R.string.orderSummaryChoc,addChocolate);
        summary+=getString(R.string.orderSummaryQuantity,quantity);
        summary+=getString(R.string.orderSummaryTotal,price);
        return summary;
    }

    public void composeMail(String content,String subject){
        Intent intent= new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT,content);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }

    }

    public void increment(View view){
        if (quantity == 100) {
            Toast.makeText(this, "You can order maximum 100 cups of coffee", Toast.LENGTH_SHORT).show();
        } else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You have to order at least 1 cup of coffee", Toast.LENGTH_SHORT).show();
        } else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int value) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + value);
    }

}