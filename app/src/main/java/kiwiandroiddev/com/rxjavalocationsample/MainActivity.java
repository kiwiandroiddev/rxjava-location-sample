package kiwiandroiddev.com.rxjavalocationsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import kiwiandroiddev.com.rxjavalocationsample.adapter.AutoCompleteCityAdapter;
import kiwiandroiddev.com.rxjavalocationsample.api.OpenWeatherMapService;
import retrofit.RestAdapter;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    public static final String API_OPENWEATHERMAP_URL = "http://api.openweathermap.org";

    @Bind(android.R.id.edit)
    AutoCompleteTextView mCityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RestAdapter retrofit = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_OPENWEATHERMAP_URL)
                .build();

        OpenWeatherMapService service = retrofit.create(OpenWeatherMapService.class);
        mCityEditText.setAdapter(
                new AutoCompleteCityAdapter(this, service, android.R.layout.simple_dropdown_item_1line));
    }
}
