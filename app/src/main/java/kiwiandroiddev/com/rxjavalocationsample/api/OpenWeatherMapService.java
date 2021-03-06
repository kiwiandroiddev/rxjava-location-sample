package kiwiandroiddev.com.rxjavalocationsample.api;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Service interface for querying weather data by city names.
 *
 * Example of concrete request generated by retrofit using this service:
 * http://api.openweathermap.org/data/2.5/find?q=London&mode=json
 *
 * Created by matt on 6/30/15.
 */
public interface OpenWeatherMapService {
    @GET("/data/2.5/find?mode=json&type=like")
    Observable<CitiesResponse> find(@Query("q") String city);
}
