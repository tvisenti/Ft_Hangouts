package com.tvisenti.ft_hangouts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayContact extends AppCompatActivity {

    Integer id;
    DatabaseHelper myDb;
    Contact contact = null;

    EditText editFirstName, editLastName, editPhone, editMail, editAddress;
    Button buttonSaveContact, buttonDeleteContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("idContact");
        myDb = DatabaseHelper.getInstance(this);
        contact = myDb.getContact(id);

        setTitle(getString(R.string.displayContactTitle) + " " + contact.getFirstName());

        initText();
        updateContact();
        deleteContact();
    }

    private void initText() {
        editFirstName = (EditText) findViewById(R.id.textFirstName);
        editLastName = (EditText) findViewById(R.id.textLastName);
        editPhone = (EditText) findViewById(R.id.textPhone);
        editMail = (EditText) findViewById(R.id.textMail);
        editAddress = (EditText) findViewById(R.id.textAddress);
        buttonSaveContact = (Button) findViewById(R.id.saveButton);
        buttonDeleteContact = (Button) findViewById(R.id.deleteButton);

        editFirstName.setText(contact.getFirstName(), TextView.BufferType.EDITABLE);
        editLastName.setText(contact.getLastName(), TextView.BufferType.EDITABLE);
        editPhone.setText(contact.getPhone(), TextView.BufferType.EDITABLE);
        editMail.setText(contact.getMail(), TextView.BufferType.EDITABLE);
        editAddress.setText(contact.getAddress(), TextView.BufferType.EDITABLE);
    }

    public void updateContact() {
        buttonSaveContact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Contact contact = new Contact(editFirstName.getText().toString(), editLastName.getText().toString(), editPhone.getText().toString(), editMail.getText().toString(), editAddress.getText().toString());
                        Integer isInserted = myDb.updateContact(contact, id);
                        if (isInserted == 1)
                            Toast.makeText(DisplayContact.this, R.string.contactUpdated, Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(DisplayContact.this, R.string.contactNotUpdated, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );
    }

    public void deleteContact() {
        buttonDeleteContact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDb.deleteContact(id);
                        Toast.makeText(DisplayContact.this, R.string.contactIsDeleted, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );
    }
}
