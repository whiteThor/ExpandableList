package android.ruben.com.expandablelistviewfilter.adapter;

import android.content.Context;
import android.ruben.com.expandablelistviewfilter.R;
import android.ruben.com.expandablelistviewfilter.model.Continent;
import android.ruben.com.expandablelistviewfilter.model.Country;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MyListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<Continent> continentList;
    private ArrayList<Continent> originalList;

    public MyListAdapter(Context context, ArrayList<Continent> continentList) {
        mContext = context;
        this.continentList = continentList;
        this.originalList = new ArrayList<>();
        this.originalList.addAll(continentList);
    }

    @Override
    public int getGroupCount() {
        return continentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
         ArrayList<Country> countries = continentList.get(groupPosition).getCountries();
        return countries.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return continentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Country> countries = continentList.get(groupPosition).getCountries();

        return countries.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Continent  continent = (Continent) getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group_row,parent,false);
        }

        TextView heading = convertView.findViewById(R.id.heading);
        heading.setText(continent.getName().trim());


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Country country = (Country) getChild(groupPosition, childPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_row, parent,false);
        }

        TextView code = convertView.findViewById(R.id.code);
        TextView name = convertView.findViewById(R.id.name);
        TextView population = convertView.findViewById(R.id.population);

        code.setText(country.getCode().trim());
        name.setText(country.getName().trim());
        population.setText(NumberFormat.getNumberInstance(Locale.US).format(country.getPopulation()));


       return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterDate(String query){
        query = query.toLowerCase();
        continentList.clear();

        if(query.isEmpty()){
            continentList.addAll(originalList);
        }else{
            for (Continent continent: originalList ) {
                ArrayList<Country> countries = continent.getCountries();
                ArrayList<Country> newList = new ArrayList<Country>();
                for (Country  country: countries) {
                    if(country.getCode().toLowerCase().contains(query) ||
                    country.getName().toLowerCase().contains(query)){
                        newList.add(country);
                    }
                }
                if(newList.size()>0){
                    Continent nContinent = new Continent(continent.getName(), newList);
                    continentList.add(nContinent);
                }


            }
        }
        notifyDataSetChanged();



    }
}
