package com.testtarget.network.api;

import com.testtarget.network.model.TopWeekly;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by kuljeetsingh on 12/6/18.
 */

public interface RestService {

    //FETCH TOP WEEKLY REPO  API
    @GET(ApiConstants.DEVELOPERS)
    Observable<List<TopWeekly>> getTopWeeklyRepo(
            @Query(ApiConstants.LANGUAGE) String language,
            @Query(ApiConstants.SINCE) String since);
}
