package com.afdhalzikri.programmingjob;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afdhalzikri.programmingjob.retrofit.Model.Job;
import com.afdhalzikri.programmingjob.retrofit.UsaJob;
import com.afdhalzikri.programmingjob.retrofit.UsaJobBuilder;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity
        extends AppCompatActivity
        implements JobAdapter.JobHolderClickListener {

    private static final String TAG = "MainActivity";

    RecyclerView mListJobRecyclerView;
    JobAdapter mJobAdapter;
    ProgressBar mProgressBar;
    JobScheduler jobScheduler;
    private static final int MYJOBID = 1;

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SugarContext.init(this);

        mListJobRecyclerView = (RecyclerView) findViewById(R.id.rv_list_job);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_progress_bar);

        mListJobRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);

        //Layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mListJobRecyclerView.setLayoutManager(layoutManager);

        //Adapater
        mJobAdapter = new JobAdapter();
        mJobAdapter.setClickListener(this);
        mListJobRecyclerView.setAdapter(mJobAdapter);

        if(isConnectedToInternet()) {
            //Ambil data ke server


                loadDataFromServer();
            }
        else{
            Toast.makeText(this, "No Internet. Data loaded from Database", Toast.LENGTH_SHORT).show();
            loadDatafromDb();
        }
    }

    public void loadDataFromServer() {
        Log.d(TAG, "Ambil data ke server");
        mListJobRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);

        UsaJob client = UsaJobBuilder.getUsaRetrofitClient();

        client.getListJob().enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "koneksi retrofit berhasil");
                    List<Job> result = response.body();
                    List<JobItem> listJob = new ArrayList<>();
                    for (Job a : result){
                        String place = a.getOrganizationName();
                        String type_job = a.getPositionTitle();
                        String open = a.getStartDate();
                        String close =  a.getEndDate();
                        String url =  a.getUrl();
                        listJob.add(new JobItem(place, type_job, open, close, url));
                    }
                    Log.d(TAG, "Jumlah data gempa " + listJob.size());
                    saveJobToDb(listJob);
                    mListJobRecyclerView.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    loadDatafromDb();
                    }
                }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                Log.d(TAG, "koneksi retrofit gagal");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                if(isConnectedToInternet()){
                    loadDataFromServer();
                }
                else{
                    loadDatafromDb();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onJobClick(int position) {

        List<JobEntity> data = mJobAdapter.getData();
        JobEntity job = data.get(position);

        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("place", job.place);
        detailIntent.putExtra("type_job", job.type_job);
        detailIntent.putExtra("open", job.open);
        detailIntent.putExtra("close", job.close);
        detailIntent.putExtra("url", job.url);

        startActivity(detailIntent);
    }
    //Method untuk mengecek koneksi ke internet
    public boolean isConnectedToInternet(){
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = (activeNetwork != null) &&  (activeNetwork.isConnectedOrConnecting());

        return isConnected;
    }

    public void loadDatafromDb(){
        List<JobEntity> listJob = JobEntity.listAll(JobEntity.class);
        mJobAdapter.setData(listJob);
    }

    public void saveJobToDb(List<JobItem> listJobItem){
        JobEntity.deleteAll(JobEntity.class);
        for (JobItem jobItem : listJobItem) {
            JobEntity jobEntity = new JobEntity(jobItem.place, jobItem.type_job, jobItem.open, jobItem.close, jobItem.url);
            jobEntity.save();
        }
    }


}