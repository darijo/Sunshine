package com.example.android.sunshine.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {


    private ArrayAdapter<String> mForecastAdapter;
    private ListView mListViewReference;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // tell fragment to handle menu events
        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_refresh) {

            updateWeather();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);





        mForecastAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                new ArrayList<String>());

        mListViewReference = (ListView) rootView.findViewById(R.id.listview_forecast);
        mListViewReference.setAdapter(mForecastAdapter);

        // set on item click listener
        // start new detail activity and pass a weather information
        mListViewReference.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(getActivity(),DetailActivity.class);
                detailIntent.putExtra(Intent.EXTRA_TEXT,mForecastAdapter.getItem(position));
                startActivity(detailIntent);

            }
        });



        return rootView;
    }
    /**
     * method that read location from settings and updates weather for current location
     */
    private void updateWeather() {


        String location = Utility.getPreferredLocation(getActivity());
        FetchWeatherTask WeatherTask = new FetchWeatherTask(getActivity(), mForecastAdapter);



        WeatherTask.execute(location);


    }









}