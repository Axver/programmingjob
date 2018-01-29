package com.afdhalzikri.programmingjob.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HP on 12/1/2017.
 */

public class UsaJobBuilder {
    static String API_BASE_URL = "https://api.usa.gov";

    public static UsaJob getUsaRetrofitClient(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(UsaJob.class);
    }
}
