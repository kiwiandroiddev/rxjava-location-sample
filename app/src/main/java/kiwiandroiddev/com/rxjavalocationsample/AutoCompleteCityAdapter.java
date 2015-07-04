package kiwiandroiddev.com.rxjavalocationsample;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

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
                    observable.subscribe(new Subscriber<CitiesResponse>() {
                                @Override
                                public void onCompleted() {
                                    Timber.d("onCompleted");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Timber.d("onError e = " + e);
                                }

                                @Override
                                public void onNext(CitiesResponse citiesResponse) {
                                    Timber.d("onNext citiesResponse = " + citiesResponse);
                                    mCities = citiesResponse.list != null ?
                                            citiesResponse.list :
                                            new ArrayList<City>();
                                }
                            });

                    // Now assign the values and count to the FilterResults object
                    Timber.d("assigning to filterresults");
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
