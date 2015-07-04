package kiwiandroiddev.com.rxjavalocationsample.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import kiwiandroiddev.com.rxjavalocationsample.api.CitiesResponse;
import kiwiandroiddev.com.rxjavalocationsample.api.City;
import kiwiandroiddev.com.rxjavalocationsample.api.OpenWeatherMapService;
import rx.Observable;

/**
 * Filterable adapter for populating suggestions of city names into an AutoCompleteTextView
 * as the user types.
 *
 * Created by matt on 7/4/15.
 */
public class AutoCompleteCityAdapter extends ArrayAdapter<City> implements Filterable {

    private List<City> mCities;
    private OpenWeatherMapService mService;

    public AutoCompleteCityAdapter(Context context, OpenWeatherMapService service, @LayoutRes int resource) {
        super(context, resource);
        mService = service;
        mCities = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mCities.size();
    }

    @Override
    public City getItem(int position) {
        return mCities.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence partialCityName) {
                FilterResults filterResults = new FilterResults();
                if (!TextUtils.isEmpty(partialCityName)) {
                    Observable<CitiesResponse> observable = mService.find(partialCityName.toString());

                    // performFiltering is already performed on a worker thread
                    // so we don't need to subscribeOn a background thread explicitly
                    CitiesResponse citiesResponse = observable.toBlocking().single();
                    mCities = citiesResponse.list != null ?
                            citiesResponse.list :
                            new ArrayList<City>();

                    filterResults.values = mCities;
                    filterResults.count = mCities.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence partialCityName, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
