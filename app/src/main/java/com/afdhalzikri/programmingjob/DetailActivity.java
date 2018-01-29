package com.afdhalzikri.programmingjob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by HP on 11/30/2017.
 */

public class DetailActivity extends AppCompatActivity {

    TextView mPlaceText, mType_JobText, mOpenText, mCloseText, mUrlText;

    Button mButtonComment;
    TextView mTextComment;
    EditText mEditComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPlaceText = (TextView) findViewById(R.id.text_place);
        mType_JobText = (TextView) findViewById(R.id.text_job);
        mOpenText = (TextView) findViewById(R.id.text_open);
        mCloseText = (TextView) findViewById(R.id.text_close);
        mUrlText = (TextView) findViewById(R.id.text_url);
        mButtonComment = (Button) findViewById(R.id.button_comment);
//        mEditComment = (EditText) findViewById(R.id.edit_comment);
//        mTextComment = (TextView) findViewById(R.id.text_comment);
//
//        if(savedInstanceState != null){
//            String comment = savedInstanceState.getString("text_comment");
//            mTextComment.setText(comment);
//        }


        Intent detailIntent = getIntent();
        if(detailIntent != null){
            String place = detailIntent.getStringExtra("place");
            String type_job = detailIntent.getStringExtra("type_job");
            String open = detailIntent.getStringExtra("open");
            String close = detailIntent.getStringExtra("close");
            String url = detailIntent.getStringExtra("url");
            mPlaceText.setText(String.valueOf(place));
            mType_JobText.setText(String.valueOf(type_job));
            mOpenText.setText(String.valueOf(open));
            mCloseText.setText(String.valueOf(close));
            mUrlText.setText(String.valueOf(url));
        }
        mButtonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String sharesub = "Company :"+ mPlaceText.getText()+"\n"+
                        "Position :"+ mType_JobText.getText()+"\n"+
                        "Open Date :"+ mOpenText.getText()+"\n"+
                        "Close Date :"+ mCloseText.getText()+"\n"+
                        "More Information :"+ mUrlText.getText();
                String shareJud = "Job Vacancies";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareJud);
                myIntent.putExtra(Intent.EXTRA_TEXT, sharesub);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
//                String comment = mEditComment.getText().toString();
//                mTextComment.setText(share1);
//                mEditComment.setText("");
           }
        });
    }
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        String comment = mTextComment.getText().toString();
//        outState.putString("text_comment", comment);
//    }
}
