package com.example.kirill.inostudiotestproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.kirill.inostudiotestproject.dbUtils.ProductAndDB;
import com.example.kirill.inostudiotestproject.objects.ProductObject;


public class AddProductActivity extends Activity {

    EditText etProductName, etProductNumber;
    ImageView ivPlus, ivMinus;
    ProductObject productObject;
    boolean isNewProduct = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        etProductName = (EditText)findViewById(R.id.etProductName);
        etProductNumber = (EditText)findViewById(R.id.etNumber);
        productObject = new ProductObject(-1, "", -1, -1, false);
        int productId = getIntent().getIntExtra(getResources().getString(R.string.product_id), -1);
        if(productId != -1){
            isNewProduct = false;
            productObject = new ProductAndDB(this).getProduct(productId);
            etProductName.setText(productObject.getName());
            etProductNumber.setText(String.valueOf(productObject.getNumber()));
        }
        ivMinus = (ImageView)findViewById(R.id.ivMinus);
        ivPlus = (ImageView)findViewById(R.id.ivPlus);
        ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheNumber(false);
            }
        });
        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheNumber(true);
            }
        });
    }

    private boolean stringValidation(String s){
        if(s.length()>0){
            for(char c: s.toCharArray()){
                if(c != ' '){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private void changeTheNumber(boolean isPlus){
        String productNumber = String.valueOf(etProductNumber.getText());
        int number = Integer.valueOf(productNumber);
        if(isPlus){
            number++;
        }else {
            number = number<1 ? 1:number-1;
        }
        productNumber = String.valueOf(number);
        etProductNumber.setText(productNumber);
    }

    private void saveProduct(){
        String productNumber = String.valueOf(etProductNumber.getText());
        String productName = String.valueOf(etProductName.getText());
        if(stringValidation(productName)&&stringValidation(productNumber)){
            productObject.setName(productName);
            productObject.setNumber(Integer.valueOf(productNumber));
            if(isNewProduct){
                productObject.setListId(getIntent().getIntExtra(getResources().getString(R.string.list_id), -1));
                new ProductAndDB(AddProductActivity.this).insertProduct(productObject);
            }else {
                new ProductAndDB(AddProductActivity.this).updateProduct(productObject);
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        saveProduct();
        super.onBackPressed();
    }
}
