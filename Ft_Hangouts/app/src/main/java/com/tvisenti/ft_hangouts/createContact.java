package com.tvisenti.ft_hangouts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createContact extends AppCompatActivity {

    EditText editFirstName, editLastName, editPhone, editMail, editAddress;
    Button buttonAddContact;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        setTitle(R.string.createContactTitle);

        myDb = DatabaseHelper.getInstance(this);

        editFirstName = (EditText) findViewById(R.id.textFirstName);
        editLastName = (EditText) findViewById(R.id.textLastName);
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
                        Contact contact = new Contact((myDb.getContactsCount() + 1), editFirstName.getText().toString(), editLastName.getText().toString(), editPhone.getText().toString(), editMail.getText().toString(), editAddress.getText().toString());
                        boolean isInserted = myDb.insertDataContact(contact);
                        if (isInserted = true)
                            Toast.makeText(createContact.this, R.string.contactCreated, Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(createContact.this, R.string.contactNotCreated, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );
    }
}
