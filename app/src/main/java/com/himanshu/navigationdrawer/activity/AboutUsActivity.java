package com.himanshu.navigationdrawer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.himanshu.navigationdrawer.R;

import org.w3c.dom.Text;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView about = (TextView) findViewById(R.id.about_text);
        about.setText(Html.fromHtml("<b>Welcome to Networking Tools app.</b>" +
                "<br><br>" +
                "This app gives information about current network state and lets you use basic networking tools of linux." +
                "<br><br>" +
                "<b>Our Team: </b><br>" +
                "<i>Himanshu Rawlani</i><br>" +
                "<i>Vignesh Zambre</i><br>" +
                "<i>Jayesh Saita</i><br>"));
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
