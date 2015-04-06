package com.example.kirill.inostudiotestproject.objects;

/**
 * Created by iiopok on 02.04.2015.
 */
public class ListObject {

    private int id;
    private String name, created;

    public ListObject(int id, String name, String created){
        this.id = id;
        this.name = name;
        this.created = created;
    }

    public void setId(int value){
        id = value;
    }

    public int getId(){
        return id;
    }

    public void setName(String value){
        name = value;
    }

    public String getName(){
        return name;
    }

    public void setCreated(String value){
        created = value;
    }

    public String getCreated(){
        return created;
    }
}
