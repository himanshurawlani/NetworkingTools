package com.himanshu.navigationdrawer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.himanshu.navigationdrawer.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView privacy = (TextView) findViewById(R.id.privacy_text);
        privacy.setText(Html.fromHtml("<b>License</b><br><br>" +
                "Copyright 2017 The Android Open Source Project, Inc." +
                "<br><br>" +
                "Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements." +
                "<br>" +
                "See the NOTICE file distributed with this work for additional information regarding copyright ownership." +
                "<br><br>" +
                "The ASF licenses this file to you under the Apache License, Version 2.0 (the \"License\") " +
                "you may not use this file except in compliance with the License. You may obtain a copy of the License at" +
                "<br><br>" +
                "http://www.apache.org/licenses/LICENSE-2.0" +
                "<br><br>" +
                "Unless required by applicable law or agreed to in writing, " +
                "software distributed under the License is distributed on an \"AS IS\" BASIS, " +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied." +
                "<br><br>" +
                "See the License for the specific language governing permissions and limitations under the License."));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
