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

import com.example.kirill.inostudiotestproject.adapters.ProductAdapter;
import com.example.kirill.inostudiotestproject.dbUtils.ProductAndDB;
import com.example.kirill.inostudiotestproject.objects.ProductObject;

import java.util.List;


public class ShoppingListActivity extends Activity {

    ListView shoppingList;
    ArrayAdapter<ProductObject> adapter;
    int listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        ImageView ivAddProduct = (ImageView) findViewById(R.id.ivAddProduct);
        listId =  getIntent().getIntExtra(getResources().getString(R.string.list_id), -1);
        ivAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingListActivity.this, AddProductActivity.class);
                intent.putExtra(getResources().getString(R.string.list_id), listId);
                startActivity(intent);
            }
        });
        shoppingList = (ListView)findViewById(R.id.lvShoppingList);

    }


    @Override
    protected void onResume() {
        super.onResume();
        final ProductAndDB productAndDB =  new ProductAndDB(this);
        final List<ProductObject> products = productAndDB.getProductsForList(listId);
        adapter = new ProductAdapter(this, products);
        shoppingList.setAdapter(adapter);
        shoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.getItem(position).setIsChecked(!adapter.getItem(position).getIsChecked());
                adapter.notifyDataSetChanged();
                productAndDB.updateProduct(adapter.getItem(position));
            }
        });
        shoppingList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(view, adapter.getItem(position));
                return false;
            }
        });
    }

    private void showPopupMenu(View v, final ProductObject productObject) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        adapter.remove(productObject);
                        new ProductAndDB(ShoppingListActivity.this).deleteProduct(productObject);
                        return true;
                    case R.id.change:
                        Intent intent = new Intent(ShoppingListActivity.this, AddProductActivity.class);
                        intent.putExtra(getResources().getString(R.string.product_id), productObject.getId());
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
