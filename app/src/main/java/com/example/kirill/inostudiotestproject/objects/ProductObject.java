package com.example.kirill.inostudiotestproject.objects;

/**
 * Created by iiopok on 02.04.2015.
 */
public class ProductObject {


    private int id, listId, number;
    private String name;
    private boolean isChecked;

    public ProductObject(int id, String name, int listId, int number, boolean isChecked) {
        this.id = id;
        this.name = name;
        this.listId = listId;
        this.number = number;
        this.isChecked = isChecked;
    }

    public void setId(int value) {
        id = value;
    }

    public int getId() {
        return id;
    }

    public void setName(String value) {
        name = value;
    }

    public String getName() {
        return name;
    }

    public void setListId(int value) {
        listId = value;
    }

    public int getListId() {
        return listId;
    }

    public void setNumber(int value) {
        number = value;
    }

    public int getNumber() {
        return number;
    }

    public void setIsChecked(boolean value) {
        isChecked = value;
    }

    public boolean getIsChecked() {
        return isChecked;
    }
}
