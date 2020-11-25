package com.example.primevideoclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.primevideoclone.adapter.BannerMoviesPagerAdapter;
import com.example.primevideoclone.adapter.MainRecylerAdapter;
import com.example.primevideoclone.model.AllCategory;
import com.example.primevideoclone.model.BannerMovies;
import com.example.primevideoclone.model.CategoryItem;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit.RetrofitClient;

public class MainActivity extends AppCompatActivity {
    BannerMoviesPagerAdapter bannerMoviesPagerAdapter;
    TabLayout tabIndicator, categoryTab;
    ViewPager bannerMovieViewPager;
    List<BannerMovies> homeBannerMoviesList;
    List<BannerMovies> tcshowsBannerMoviesList;
    List<BannerMovies> kidsBannerMoviesList;
    List<BannerMovies> moviesBannerMoviesList;
    Toolbar toolbar;
    RecyclerView mainrecyclerView;
    MainRecylerAdapter mainRecylerAdapter;
    List<AllCategory> allCategoryList;
    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabIndicator = findViewById(R.id.tab_indicator);
        categoryTab = findViewById(R.id.tabLayout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ////for default when click on app bar go to default view////
        nestedScrollView = findViewById(R.id.nested_scroll);
        appBarLayout = findViewById(R.id.appbar);
        //////
        homeBannerMoviesList = new ArrayList<>();
        ///tvshows////
        tcshowsBannerMoviesList = new ArrayList<>();
        //MOVIES
        moviesBannerMoviesList = new ArrayList<>();
        //kids
        kidsBannerMoviesList = new ArrayList<>();
//fetch banner data from server
        getBannerData();
        ///


//ONTABSELETEDCHANGE
        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:
                        //lets se behaviour right now
                        setScrollDefaultSatate();
                        setBannerMoviesPagerAdapter(tcshowsBannerMoviesList);
                        return;

                    case 2:
                        setScrollDefaultSatate();
                        setBannerMoviesPagerAdapter(moviesBannerMoviesList);
                        return;

                    case 3:
                        setScrollDefaultSatate();
                        setBannerMoviesPagerAdapter(kidsBannerMoviesList);
                        return;
                    default:
                        setScrollDefaultSatate();
                        setBannerMoviesPagerAdapter(homeBannerMoviesList);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }


            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //first we will dd cat data

        List<CategoryItem> homecatlistitem1 = new ArrayList<>();
        homecatlistitem1.add(new CategoryItem(1, "Lov  Other Drugs", "https://images-na.ssl-images-amazon.com/images/S/sgp-catalog-images/region_US/20th_century_fox-031258b-Full-Image_GalleryCover-en-US-1484001584246._UY500_UX667_RI_Vo4zAI5r1mVwhj8j6TDGP35lma3ZngR_TTW_.jpg", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem1.add(new CategoryItem(2, "Bewakoofiyaan", "https://theworldofmovies.com/wp-content/uploads/2016/03/Bewakoofiyaan.1-1200x842.jpg", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem1.add(new CategoryItem(3, "Supernatural", "data:image/jpeg;base64,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", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem1.add(new CategoryItem(4, "T-34", "https://i.ytimg.com/vi/jPPglWcJF1U/maxresdefault.jpg", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem1.add(new CategoryItem(5, "season of the witch", "https://occ-0-1722-1723.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABUePHxjW_7WfhV8PTtoepCd92YMJJVVAtsbQv-7QBL5lQi3kIYSqGvHo6BGXPN8LsqL2YvNYC9b8DVyoX89aZxTWX8Vb.jpg?r=198", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        ///////////////////////
        ///similrly add category s muc as we want
        //i hae same category lets copy and past///
        List<CategoryItem> homecatlistitem2 = new ArrayList<>();
        homecatlistitem2.add(new CategoryItem(1, "Bhoot", "https://i.ytimg.com/vi/ELcRnZ3kP08/hqdefault.jpg", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem2.add(new CategoryItem(2, "Black Mail", "https://i2.cinestaan.com/image-bank/1500-1500/115001-116000/115625.jpg", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem2.add(new CategoryItem(3, "kaarwaan", "https://english.cdn.zeenews.com/sites/default/files/2018/07/27/707288-karwaan-bbfc.jpg", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem2.add(new CategoryItem(4, "102 Not-Out", "https://i.ytimg.com/vi/qrks9Zu0f1w/maxresdefault.jpg", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem2.add(new CategoryItem(5, "Shikara", "https://i.ytimg.com/vi/LoZXboySl2I/maxresdefault.jpg", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        List<CategoryItem> homecatlistitem3 = new ArrayList<>();

        homecatlistitem3.add(new CategoryItem(1, "Madagascar", "https://cdn.onebauer.media/one/empire-tmdb/films/953/images/wUetiqu2EFhHW94yTP0pWABfyUG.jpg?quality=50&width=1800&ratio=16-9&resizeStyle=aspectfill&format=jpg", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem3.add(new CategoryItem(2, "Harry Potter", "https://www.soda.com/wp-content/uploads/2020/04/harry-potter-streaming-guide.jpg", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem3.add(new CategoryItem(3, "Jurassic Park", "https://www.jurassicworld.com/sites/default/files/2020-06/JWE_Jurassic_Website_External_945x455_Key%20Art_1.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem3.add(new CategoryItem(4, "Stroks", "https://crypticrock.com/wp-content/uploads/2016/10/storks-quad.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem3.add(new CategoryItem(5, "Lost and Found", "https://i.ytimg.com/vi/s_poLMl_B8Q/maxresdefault.jpg", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        List<CategoryItem> homecatlistitem4 = new ArrayList<>();
        homecatlistitem4.add(new CategoryItem(1, "El Prasident", "https://2.bp.blogspot.com/-IQ04dDsUILE/VP_Ucb5AuaI/AAAAAAAAAOc/pvjz8C_RraM/s1600/images%2B(6).jpg", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem4.add(new CategoryItem(2, "PatalLok", "https://www.samaa.tv/wp-content/uploads/2020/05/Paatal_v1.jpg", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem4.add(new CategoryItem(3, "Upload", "data:image/jpeg;base64,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", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homecatlistitem4.add(new CategoryItem(4, "The Family Man", "https://akm-img-a-in.tosshub.com/indiatoday/images/story/201909/the-family-man-review-647x363.jpeg?.1s.HUEsI2hadK2mmHlSPXe4LGBM23zU", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));


        //////////////////////////////////
        allCategoryList = new ArrayList<>();
        allCategoryList.add(new AllCategory(1, "Watch Next Tv and Movies", homecatlistitem1));
        allCategoryList.add(new AllCategory(2, "Movies in Hindi", homecatlistitem2));
        allCategoryList.add(new AllCategory(3, "Kids and Family Movies", homecatlistitem3));
        allCategoryList.add(new AllCategory(3, "Amazon Original Series", homecatlistitem4));
        //here we pass array to recyler view
        setMainrecyclerView(allCategoryList);

    }

    private void setBannerMoviesPagerAdapter(List<BannerMovies> bannerMoviesList) {
        bannerMovieViewPager = findViewById(R.id.banner_viewPager);
        bannerMoviesPagerAdapter = new BannerMoviesPagerAdapter(this, bannerMoviesList);
        bannerMovieViewPager.setAdapter(bannerMoviesPagerAdapter);
        tabIndicator.setupWithViewPager(bannerMovieViewPager);
        Timer slideTimer = new Timer();
        slideTimer.scheduleAtFixedRate(new AutoSlider(), 4000, 6000);
        tabIndicator.setupWithViewPager(bannerMovieViewPager, true);

    }

    class AutoSlider extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (bannerMovieViewPager.getCurrentItem() < homeBannerMoviesList.size() - 1) {
                        bannerMovieViewPager.setCurrentItem(bannerMovieViewPager.getCurrentItem() + 1);
                    } else {
                        bannerMovieViewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    public void setMainrecyclerView(List<AllCategory> allCategoryList) {
        mainrecyclerView = findViewById(R.id.main_recyler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mainrecyclerView.setLayoutManager(layoutManager);
        mainRecylerAdapter = new MainRecylerAdapter(this, allCategoryList);
        mainrecyclerView.setAdapter(mainRecylerAdapter);


    }
    ////this is app bar default click/////
    private void setScrollDefaultSatate(){
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);

    }
    private void getBannerData(){

   CompositeDisposable compositeDisposable=new CompositeDisposable();
   compositeDisposable.add(RetrofitClient.getRetrofitClient().getAllBanners()
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribeWith(new DisposableObserver<List<BannerMovies>>() {
               @Override
               public void onNext(@NonNull List<BannerMovies> bannerMovies) {
                 for(int i=0;i<bannerMovies.size();i++){

                if(bannerMovies.get(i).getBannerCategoryId().toString().equals("1"))
                {
                   homeBannerMoviesList.add(bannerMovies.get(i));

                }else if(bannerMovies.get(i).getBannerCategoryId().toString().equals("2")){
                    tcshowsBannerMoviesList.add(bannerMovies.get(i));
                }else if(bannerMovies.get(i).getBannerCategoryId().toString().equals("3")){
                    moviesBannerMoviesList.add(bannerMovies.get(i));
                }else if(bannerMovies.get(i).getBannerCategoryId().toString().equals("4")){
                    kidsBannerMoviesList.add(bannerMovies.get(i));
                }else
                {

                }

                 }

                   bannerMoviesPagerAdapter = new BannerMoviesPagerAdapter(MainActivity.this, homeBannerMoviesList);
                   bannerMoviesPagerAdapter.notifyDataSetChanged();
               }

               @Override
               public void onError(@NonNull Throwable e) {
              Log.d("bannerData",""+e);
               }

               @Override
               public void onComplete() {
                   //this is default select tab
                   setBannerMoviesPagerAdapter(homeBannerMoviesList);
               }
           })




   );

    }
}