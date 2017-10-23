package com.pgssoft.forgetAdaptersDemo.models;

/**
 * Created by wsura on 23.10.2017.
 */

public class PersonModel {

    private String name;
    private String surname;
    private String company;

    public PersonModel() {

    }

    public PersonModel(String name, String surname, String company) {

        this.name = name;
        this.surname = surname;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
