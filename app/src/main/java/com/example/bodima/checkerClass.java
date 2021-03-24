package com.example.bodima;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class checkerClass {
    private final databaseHelper databaseHelper;
    private final String tableMonth = "month";
    private final String monthFee = "month_fee";
    public checkerClass(Context context){
        databaseHelper = new databaseHelper(context);
    }

    public boolean addPersonName(String columnName){
        String[] allColumnNames = databaseHelper.getAllColumnNames();
        for (String temp:allColumnNames){
            if (temp.equals(columnName)){
                return false;
            }
        }
        return databaseHelper.addColumn(columnName);
    }

    public boolean addMonthRow(String month){
        ArrayList<String> valuesInTheColumn = databaseHelper.returnAllValuesInColumn(tableMonth);
        for (int i=0 ; i<valuesInTheColumn.size(); i++){
            String temp = valuesInTheColumn.get(i);
            if (temp.equals(month)){
                return false;
            }
        }
        if (databaseHelper.addRow(tableMonth,month)){
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<String> getPersonNames(){
        ArrayList<String> returnArray = new ArrayList<String>();
        String[] columns = databaseHelper.getAllColumnNames();
        for (int i = 2; i<columns.length ; i++){
            returnArray.add(columns[i]);
        }
        return returnArray;
    }

    public boolean addFeeValues(String month, String monthFeeValue){
        if (databaseHelper.addValues(month,monthFee,monthFeeValue)){
            return true;
        }
        return false;
    }

    public boolean studentPay(String month, String studentName,String monthlyFee){
        if (databaseHelper.addValues(month,studentName,monthlyFee)){
            return true;
        }
        return false;
    }

    public ArrayList<String> getMonthViewList(){
        ArrayList<String> monthList;
        monthList = databaseHelper.returnAllValuesInColumn(tableMonth);
        if (monthList.size()==0){
            return monthList;
        }
        ArrayList<String> returnList = new ArrayList<String>();
        for (int i=monthList.size()-1;i>=0;i--){
            returnList.add(monthList.get(i));
        }
        return returnList;
    }

    public ArrayList<String> getToPayNameList(String month){
        ArrayList<String> toPayList = new ArrayList<>();
        ArrayList<String>nameList = getPersonNames();
        for (String temp: nameList){
            String value = databaseHelper.getValueInDatabase(month,temp);
            if (value==null){
                toPayList.add(temp);
            }
        }
        return toPayList;
    }

    public String getMonthFeeValue(String month){
       String monthValue = databaseHelper.getValueInDatabase(month,monthFee);
       return monthValue;
    }

}
