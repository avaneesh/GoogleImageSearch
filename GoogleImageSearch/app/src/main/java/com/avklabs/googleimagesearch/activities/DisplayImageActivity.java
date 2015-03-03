package com.avklabs.googleimagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.avklabs.googleimagesearch.R;
import com.avklabs.googleimagesearch.model.ImageResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DisplayImageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // MUST request the feature before setting content view
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_display_image);

        showProgressBar();

        //String image_url = getIntent().getStringExtra("url");
        ImageResult imageResult = (ImageResult) getIntent().getSerializableExtra("imageObj");

        Log.e("Loading", imageResult.image_url);

        ImageView iv = (ImageView) findViewById(R.id.ivFullImage);
        TextView tvTitle = (TextView) findViewById(R.id.tvFullTitle);

        Picasso.with(this).load(imageResult.image_url).into(iv, new Callback() {
            @Override
            public void onSuccess() {
                hideProgressBar();
            }

            @Override
            public void onError() {
                hideProgressBar();
                TextView tvTitle = (TextView) findViewById(R.id.tvFullTitle);
                tvTitle.setText(Html.fromHtml("Failed to load: "+tvTitle.getText().toString()));
                //reportError(tvTitle.getText().toString());
            }
        });
        tvTitle.setText(Html.fromHtml(imageResult.title));
    }

    public void reportError(String url) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(url));
        startActivity(Intent.createChooser(sharingIntent,"Share using"));
    }

    // Should be called manually when an async task has started
    public void showProgressBar() {
        setProgressBarIndeterminateVisibility(true);
    }

    // Should be called when an async task has finished
    public void hideProgressBar() {
        setProgressBarIndeterminateVisibility(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
