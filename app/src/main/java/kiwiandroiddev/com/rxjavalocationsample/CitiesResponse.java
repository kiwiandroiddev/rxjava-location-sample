package kiwiandroiddev.com.rxjavalocationsample;

import java.util.List;

/**
 * Created by matt on 7/1/15.
 */
public class CitiesResponse {
    public List<City> list;

    @Override
    public String toString() {
        return "CitiesResponse{" +
                "list=" + list +
                '}';
    }
}
