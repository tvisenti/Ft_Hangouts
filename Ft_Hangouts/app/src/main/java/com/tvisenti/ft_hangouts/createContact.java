package com.tvisenti.ft_hangouts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createContact extends AppCompatActivity {
    EditText editLastName, editFirstName, editPhone, editMail, editAddress;
    Button buttonAddContact;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        myDb = DatabaseHelper.getInstance(this);

        editLastName = (EditText) findViewById(R.id.textLastName);
        editFirstName = (EditText) findViewById(R.id.textFirstName);
        editPhone = (EditText) findViewById(R.id.textPhone);
        editMail = (EditText) findViewById(R.id.textMail);
        editAddress = (EditText) findViewById(R.id.textAddress);
        buttonAddContact = (Button) findViewById(R.id.buttonAdd);
        addContact();
    }

    public void addContact() {
        buttonAddContact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertDataContact(editFirstName.getText().toString(), editLastName.getText().toString(), editPhone.getText().toString(), editMail.getText().toString(), editAddress.getText().toString());
                        if (isInserted = true)
                            Toast.makeText(createContact.this, "Contact created", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(createContact.this, "Contact not created", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
