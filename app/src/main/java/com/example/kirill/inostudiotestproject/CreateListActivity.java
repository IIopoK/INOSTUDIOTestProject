package com.example.kirill.inostudiotestproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.kirill.inostudiotestproject.dbUtils.ListAndDB;
import com.example.kirill.inostudiotestproject.objects.ListObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CreateListActivity extends Activity {

    EditText etListName;
    ListObject listObject;
    boolean isNewList = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        etListName = (EditText)findViewById(R.id.etListName);
        int listId = getIntent().getIntExtra(getResources().getString(R.string.list_id), -1);
        listObject = new ListObject(-1, "", "");
        if(listId!=-1){
            isNewList = false;
            listObject = new ListAndDB(this).getList(listId);
            etListName.setText(listObject.getName());
        }
    }

    private void saveList(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        listObject.setName(String.valueOf(etListName.getText()));
        listObject.setCreated(dateFormat.format(cal.getTime()));
        if(isNewList){
            new ListAndDB(CreateListActivity.this).insertList(listObject);
        }else {
            new ListAndDB(CreateListActivity.this).updateList(listObject);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveList();
    }
}
