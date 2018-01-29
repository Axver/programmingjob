package com.afdhalzikri.programmingjob.retrofit;

import com.afdhalzikri.programmingjob.retrofit.Model.Job;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by HP on 12/1/2017.
 */

public interface UsaJob {
    @GET("/jobs/search.json?query=programming+jobs")
    Call<List<Job>> getListJob();
}
