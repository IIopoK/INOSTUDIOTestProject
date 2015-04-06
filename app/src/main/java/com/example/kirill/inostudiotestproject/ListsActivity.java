package com.example.kirill.inostudiotestproject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.kirill.inostudiotestproject.adapters.ListAdapter;
import com.example.kirill.inostudiotestproject.dbUtils.ListAndDB;
import com.example.kirill.inostudiotestproject.dbUtils.ProductAndDB;
import com.example.kirill.inostudiotestproject.objects.ListObject;

import java.util.List;


public class ListsActivity extends Activity {

    ListView lvLists;
    ArrayAdapter<ListObject> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        ImageView ivAddList = (ImageView) findViewById(R.id.ivAddList);
        ivAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListsActivity.this, CreateListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        final List<ListObject> lists = new ListAndDB(this).getLists();
        lvLists = (ListView) findViewById(R.id.lvLists);
        adapter = new ListAdapter(this, lists);
        lvLists.setAdapter(adapter);
        lvLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListsActivity.this, ShoppingListActivity.class);
                intent.putExtra(getResources().getString(R.string.list_id), lists.get(position).getId());
                startActivity(intent);
            }
        });
        lvLists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(view,  lists.get(position));
                return false;
            }
        });
    }

    private void showPopupMenu(View v, final ListObject listObject) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        adapter.remove(listObject);
                        new ListAndDB(ListsActivity.this).deleteList(listObject);
                        new ProductAndDB(ListsActivity.this).deleteProductsForList(listObject.getId());
                        return true;
                    case R.id.change:
                        Intent intent = new Intent(ListsActivity.this, CreateListActivity.class);
                        intent.putExtra(getResources().getString(R.string.list_id), listObject.getId());
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }

        });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {

            @Override
            public void onDismiss(PopupMenu menu) {
                adapter.notifyDataSetChanged();
            }
        });
        popupMenu.show();
    }

}
