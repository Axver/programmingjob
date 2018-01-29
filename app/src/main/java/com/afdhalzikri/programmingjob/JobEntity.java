package com.afdhalzikri.programmingjob;

import com.orm.SugarRecord;

/**
 * Created by HP on 12/1/2017.
 */

public class JobEntity extends SugarRecord {
    String place;
    String type_job;
    String open;
    String close;
    String url;

    public JobEntity(){
    }
    public JobEntity(String place, String type_job, String open, String close, String url) {
        this.place = place;
        this.type_job = type_job;
        this.open = open;
        this.close = close;
        this.url = url;
    }
}
