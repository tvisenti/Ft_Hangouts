package com.tvisenti.ft_hangouts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class createContact extends AppCompatActivity {
    EditText editLastName, editFirstName, editPhone, editMail, editAddress;
    Button buttonAddContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        editLastName = (EditText) findViewById(R.id.textLastName);
        editFirstName = (EditText) findViewById(R.id.textFirstName);
        editPhone = (EditText) findViewById(R.id.textPhone);
        editMail = (EditText) findViewById(R.id.textMail);
        editAddress = (EditText) findViewById(R.id.textAddress);
        buttonAddContact = (Button) findViewById(R.id.buttonAdd);
    }

    public void addContact() {
        buttonAddContact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // je dois passer la database dans cette classe pour pouvoir ajouter des elem dans la table: video 3, 10min30
                    }
                }
        );
    }
}
