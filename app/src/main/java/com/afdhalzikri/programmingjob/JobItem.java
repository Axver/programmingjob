package com.afdhalzikri.programmingjob;

/**
 * Created by HP on 12/1/2017.
 */

public class JobItem {

    public String place;
    public String type_job;
    public String open;
    public String close;
    public String url;

    public JobItem(String place, String type_job, String open, String close, String url) {
        this.place = place;
        this.type_job = type_job;
        this.open = open;
        this.close = close;
        this.url = url;
    }
}