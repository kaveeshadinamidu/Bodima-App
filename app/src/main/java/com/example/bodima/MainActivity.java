package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    checkerClass manageApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manageApp = new checkerClass(this);
    }

    public void enterNamesToApp(View view){
        setContentView(R.layout.entervalues);
    }
    public void backToActivity(View view){
        setContentView(R.layout.activity_main);
    }
    public void setValuesOfBodima(View view){
        setContentView(R.layout.setvalues);
    }

    public void enterPersonNameToApp(View view){
        EditText editText = (EditText) findViewById(R.id.inputNametext);
        String name = editText.getText().toString();
        if (name==null){
            sendToast("Enter name!");
        }else{
            if (manageApp.addPersonName(name)){
                sendToast("Succesfully Entered!");
            }else{
                sendToast("Error in Entering Data!");
            }
        }
    }
    public void sendToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}