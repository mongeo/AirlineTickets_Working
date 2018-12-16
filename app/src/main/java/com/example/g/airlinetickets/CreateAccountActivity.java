package com.example.g.airlinetickets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {

    DBController dbController;

    private TextView mCreateAccountUsernameField;
    private TextView mCreateAccountPasswordField;
    private Button mSubmitCreateAccountButton;

    private String name;
    private String pass;

    private int count;

    private boolean validCredentials(String username, String password){
        boolean validUser = false;
        boolean validPass = false;

        //test user
        int alphaCount = 0;
        int numericCount = 0;
        for (int i = 0; i < username.length(); i++){
            if (Character.isDigit(username.charAt(i))){
                numericCount++;
            } else if (Character.isAlphabetic(username.charAt(i))){
                alphaCount++;
            }
        }
        if (alphaCount > 2 && numericCount > 0){
            validUser = true;
        }

        //test pass
        alphaCount = 0;
        numericCount = 0;
        for (int i = 0; i < password.length(); i++){
            if (Character.isDigit(password.charAt(i))){
                numericCount++;
            } else if (Character.isAlphabetic(password.charAt(i))){
                alphaCount++;
            }
        }
        if (alphaCount > 2 && numericCount > 0){
            validPass = true;
        }
        if (validUser && validPass)
            return true;
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        dbController = DBController.get(this.getApplicationContext());

        count = 0;

        mCreateAccountUsernameField = (TextView) findViewById(R.id.create_account_username_field);
        mCreateAccountPasswordField = (TextView) findViewById(R.id.create_account_password_field);

        mSubmitCreateAccountButton = (Button) findViewById(R.id.submit_create_account_button);
        mSubmitCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mCreateAccountUsernameField.getText().toString();
                pass = mCreateAccountPasswordField.getText().toString();
                boolean exists = dbController.userExists(name);
                boolean valid = validCredentials(name, pass);
                String errorMsg = "";
                if (count > 0 && (valid != true || exists)){
                    count = 0;
                    if (!valid){
                        errorMsg = "Invalid format.";
                    }
                    else if (exists){
                        errorMsg = "User already exists.";
                    }
                    dbController.addUser(name, pass);
                    dbController.addTransaction(name);
                    AlertDialog userCreatedSuccessfullyAlert = new AlertDialog.Builder(CreateAccountActivity.this).create();
                    userCreatedSuccessfullyAlert.setTitle("Failure");
                    userCreatedSuccessfullyAlert.setMessage(errorMsg);
                    userCreatedSuccessfullyAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    userCreatedSuccessfullyAlert.show();
                } else {
                    //check if proper format
                    if (!valid){
                        Toast.makeText(CreateAccountActivity.this,
                                "Invalid format.",
                                Toast.LENGTH_SHORT).show();
                        count++;
                    }
                    //check if user already exists
                    else if (exists){
                        Toast.makeText(CreateAccountActivity.this,
                                "User already exists.",
                                Toast.LENGTH_SHORT).show();
                        count++;
                    }

                    //user is added
                    else {
                        dbController.addUser(name, pass);
                        dbController.addTransaction(name);
                        AlertDialog userCreatedSuccessfullyAlert = new AlertDialog.Builder(CreateAccountActivity.this).create();
                        userCreatedSuccessfullyAlert.setTitle("Success!");
                        userCreatedSuccessfullyAlert.setMessage("User " + name + " created!");
                        userCreatedSuccessfullyAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                        userCreatedSuccessfullyAlert.show();
                    }
                }
            }
        });
    }
}
