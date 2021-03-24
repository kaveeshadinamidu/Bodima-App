package com.example.bodima;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    checkerClass manageApp;
    ListView monthListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manageApp = new checkerClass(this);
        monthListView = (ListView) findViewById(R.id.monthViewList);
        updateMonthViewList();


    }

    public void setMonthFee(String month){
        TextView monthfeeViewValue = (TextView) findViewById(R.id.monthfeeView);
        String fee = manageApp.getMonthFeeValue(month);
        monthfeeViewValue.setText("Rs: "+fee);

    }

    public void enterNamesToApp(View view){
        setContentView(R.layout.entervalues);
        updateNameList(view);
    }
    public void backToActivity(View view){
        setContentView(R.layout.activity_main);
        updateMonthViewList();
    }
    public void setValuesOfBodima(View view){
        setContentView(R.layout.setvalues);
    }

    public void enterPersonNameToApp(View view){
        EditText editText = findViewById(R.id.inputNametext);
        String name = editText.getText().toString();
        if (name==null){
            sendToast("Enter name!");
        }else{
            if (manageApp.addPersonName(name)){
                editText.setText("");
                sendToast("Succesfully Entered!");
                updateNameList(view);
            }else{
                sendToast("Error in Entering Data!");
            }
        }
    }
    public void sendToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void enterValuesToDatabase(View view){
        EditText monthFeeInput = (EditText) findViewById(R.id.monthfeeValueInput);
        EditText monthNameInput = (EditText) findViewById(R.id.monthNameInput);
        EditText yearNameInput = (EditText) findViewById(R.id.yearNameInput);
        String monthFee = monthFeeInput.getText().toString();
        String monthName = monthNameInput.getText().toString();
        String yearName = yearNameInput.getText().toString();
        if (monthFee==null || monthName==null || yearName==null){
            sendToast("Enter All Values");
        }else{
            String value = yearName+" "+monthName;
            if (manageApp.addMonthRow(value)){
                sendToast("Add Successfully!");
            }else{
                sendToast("Error in Entering the values!");
            }

            if (manageApp.addFeeValues(value,monthFee)){
                sendToast("Sucessfully Added!");
            }else {
                sendToast("Error in Adding Values!");
            }
        }
    }
    public void updateNameList(View view){
        ListView nameList = (ListView) findViewById(R.id.personNameList);
        ArrayList<String> names = new ArrayList<String>();
        names = manageApp.getPersonNames();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        nameList.setAdapter(arrayAdapter);
    }

    public void updateMonthViewList(){
        ArrayList<String> monthViewList = manageApp.getMonthViewList();
        Log.i(TAG,Integer.toString(monthViewList.size()));
        if (monthViewList.size()!=0){
            monthListView = (ListView) findViewById(R.id.monthViewList);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,monthViewList);
            monthListView.setAdapter(arrayAdapter);
        }
        monthListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String month = (String) monthListView.getItemAtPosition(position);
                toPayNameList(month);
            }
        });
    }

    public void toPayNameList(String month){
        setContentView(R.layout.payablenamelistview);
        setMonthFee(month);
        ArrayList<String> toPayList = manageApp.getToPayNameList(month);
        ListView toPayNameLisView = (ListView) findViewById(R.id.viewPayableNameList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,toPayList);
        toPayNameLisView.setAdapter(arrayAdapter);

        toPayNameLisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) toPayNameLisView.getItemAtPosition(position);
                buildAlertAndAddValue(month,name);
            }
        });
    }

    private void buildAlertAndAddValue(String month,String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Is "+name+" paid?");
        builder.setTitle("Alert!");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                personPaid(month, name);
                toPayNameList(month);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void personPaid(String month,String name) {
        String monthFee = manageApp.getMonthFeeValue(month);
        if (manageApp.studentPay(month,name,monthFee)){
            sendToast("added Sucessfully!");
        }else {
            sendToast("Error in Adding values!");
        }

    }
}