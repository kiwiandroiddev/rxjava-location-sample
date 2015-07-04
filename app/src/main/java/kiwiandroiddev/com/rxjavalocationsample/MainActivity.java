package kiwiandroiddev.com.rxjavalocationsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.RestAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private OpenWeatherMapService mService;

    @Bind(android.R.id.edit)
    AutoCompleteTextView mCityEditText;

//    @Bind(android.R.id.text1)
//    TextView mResultsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RestAdapter retrofit = new RestAdapter.Builder()
//                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://api.openweathermap.org")
                .build();

        mService = retrofit.create(OpenWeatherMapService.class);

        mCityEditText.setAdapter(
                new AutoCompleteCityAdapter(this, mService, android.R.layout.simple_dropdown_item_1line));
        mCityEditText.setThreshold(2);
    }
//
//    @OnClick(android.R.id.button1)
//    public void findCities() {
//        Observable<CitiesResponse> observable = mService.find(mCityEditText.getText().toString());
//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<CitiesResponse>() {
//                    @Override
//                    public void onCompleted() {
//                        Timber.d("onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Timber.d("onError e = " + e);
//                    }
//
//                    @Override
//                    public void onNext(CitiesResponse citiesResponse) {
//                        Timber.d("onNext citiesResponse = " + citiesResponse);
//                        mResultsText.setText(citiesResponse.list.toString());
//                    }
//                });
//    }

}
