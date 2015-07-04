package kiwiandroiddev.com.rxjavalocationsample;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by matt on 6/30/15.
 *
 * http://api.openweathermap.org/data/2.5/find?q=London&mode=json
 *
 *
 *
 */
public interface OpenWeatherMapService {
    @GET("/data/2.5/find?mode=json&type=like")
    Observable<CitiesResponse> find(@Query("q") String location);
}
