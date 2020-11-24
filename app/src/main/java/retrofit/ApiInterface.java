package retrofit;


import com.example.primevideoclone.model.BannerMovies;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("banner_movie.json")
    Observable<List<BannerMovies>> getAllBanners();

}
