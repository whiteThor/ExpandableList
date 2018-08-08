package android.ruben.com.expandablelistviewfilter;

import android.app.SearchManager;
import android.content.Context;
import android.ruben.com.expandablelistviewfilter.adapter.MyListAdapter;
import android.ruben.com.expandablelistviewfilter.model.Continent;
import android.ruben.com.expandablelistviewfilter.model.Country;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private  SearchView mSearchView;
    private MyListAdapter mMyListAdapter;
    private ExpandableListView mExpandableListView ;
    private ArrayList<Continent> mContinents = new ArrayList<Continent>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        mSearchView  = findViewById(R.id.searchView);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);

        displayList();

        expandAll();
    }

    private void displayList() {
        loadSomeDate();

        mExpandableListView = findViewById(R.id.expandableList);
        mMyListAdapter = new MyListAdapter(MainActivity.this, mContinents);
        mExpandableListView.setAdapter(mMyListAdapter);
    }

    private void loadSomeDate() {
        ArrayList<Country> countryList = new ArrayList<Country>();
        Country country = new Country("BMU","Bermuda",10000000);
        countryList.add(country);
        country = new Country("CAN","Canada",20000000);
        countryList.add(country);
        country = new Country("USA","United States",50000000);
        countryList.add(country);

        Continent continent = new Continent("North America",countryList);
        mContinents.add(continent);

        countryList = new ArrayList<Country>();
        country = new Country("CHN","China",10000100);
        countryList.add(country);
        country = new Country("JPN","Japan",20000200);
        countryList.add(country);
        country = new Country("THA","Thailand",50000500);
        countryList.add(country);

        continent = new Continent("Asia",countryList);
        mContinents.add(continent);

    }

    private void expandAll() {
        int count = mMyListAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            mExpandableListView.expandGroup(i);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onClose() {
        mMyListAdapter.filterDate("");
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mMyListAdapter.filterDate(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mMyListAdapter.filterDate(newText);
        expandAll();
        return false;
    }
}
