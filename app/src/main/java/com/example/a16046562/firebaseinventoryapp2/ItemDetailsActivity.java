package com.example.a16046562.firebaseinventoryapp2;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class ItemDetailsActivity extends AppCompatActivity {
    private static final String TAG = "ItemDetailsActivity";
    private EditText etName, etCost;
    private Button btnUpdate, btnDelete;
    private Item item;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference itemListRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        etName = (EditText)findViewById(R.id.editTextName);
        etCost = (EditText)findViewById(R.id.editTextCost);

        firebaseDatabase = FirebaseDatabase.getInstance();
        itemListRef = firebaseDatabase.getReference("/itemList");

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("Item");

        //Display Student details as retrieved from the intent
        etName.setText(item.getName());
        etCost.setText(String.valueOf(item.getCost()));

        btnUpdate = (Button)findViewById(R.id.buttonUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Update Student record based on input given
                String id = item.getId();
                String name = etName.getText().toString();
                double cost = Double.parseDouble(etCost.getText().toString());

                item.setId(null);
                item.setCost(cost);
                item.setName(name);

                itemListRef.child(id).setValue(item);
                Toast.makeText(getApplicationContext(), "Item record updated successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

        btnDelete = (Button)findViewById(R.id.buttonDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Delete Student record based on student id
                itemListRef.child(item.getId()).removeValue();

                Toast.makeText(getApplicationContext(), " Item record deleted successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
