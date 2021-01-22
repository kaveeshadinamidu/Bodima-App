package com.example.bodima;

import android.content.Context;

public class checkerClass {
    private databaseHelper databaseHelper;
    private String tableMonth = "month";
    private String monthFee = "MonthFee";
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
        if (databaseHelper.addColumn(columnName)){
            return true;
        }
        return false;
    }
}
