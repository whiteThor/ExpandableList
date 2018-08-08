package android.ruben.com.expandablelistviewfilter.model;

import java.util.ArrayList;

public class Continent {

    private String mName;
    private ArrayList<Country> mCountries ;

    public Continent(String name, ArrayList<Country> countries) {
        this.mName = name;
        mCountries = countries;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ArrayList<Country> getCountries() {
        return mCountries;
    }

    public void setCountries(ArrayList<Country> countries) {
        mCountries = countries;
    }
}
