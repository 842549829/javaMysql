package com.company.classes.model;


public class AccountCondition {

    private String name;
    private Paging paging;

    public AccountCondition(Paging paging){
        this.setPaging(paging);
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setPaging(Paging paging){
        this.paging = paging;
    }

    public Paging getPaging(){
        return this.paging;
    }
}
