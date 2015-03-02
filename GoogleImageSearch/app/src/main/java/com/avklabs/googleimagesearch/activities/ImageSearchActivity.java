package com.avklabs.googleimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.avklabs.googleimagesearch.R;
import com.avklabs.googleimagesearch.adapters.EndlessScrollListener;
import com.avklabs.googleimagesearch.adapters.ImageResultsAdapter;
import com.avklabs.googleimagesearch.model.ImageFilters;
import com.avklabs.googleimagesearch.model.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ImageSearchActivity extends ActionBarActivity {

    //https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=Cat

    EditText etQuery;
    GridView gvResults;

    ArrayList<ImageResult> imageResults;
    ImageResultsAdapter aResults;

    String search_url;
    private static int IMAGES_PER_PAGE = 8;

    public static final int FILTER_RESULT_OK = 200;

    ImageFilters imageFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);

        imageFilters = new ImageFilters();
        imageResults = new ArrayList();
        aResults = new ImageResultsAdapter(this, imageResults);

        setViews();
    }

    private void show_full_image(ImageResult imageResult){
        Intent i = new Intent(this, DisplayImageActivity.class);

        //i.putExtra("url", imageResult.image_url);
        i.putExtra("imageObj", imageResult);

        startActivity(i);
    }

    private void setViews(){
        etQuery = (EditText) findViewById(R.id.tvQuery);
        gvResults = (GridView) findViewById(R.id.gvResult);

        gvResults.setAdapter(aResults);


        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ImageSearchActivity.this, "Item clicked"+position, Toast.LENGTH_SHORT).show();
                show_full_image(imageResults.get(position));
            }
        });

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemCount) {
                fetchImages(page);
            }
        });
    }

    // OnClick for Search button
    public void onImageSearchClick(View v){
        String query = etQuery.getText().toString();

        this.search_url =
                "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=" + query;
        imageResults.clear();
        fetchImages(0);
    }

    private void fetchImages(int currPage){
        AsyncHttpClient client = new AsyncHttpClient();

        String currUrl = this.search_url +"&start="+currPage*IMAGES_PER_PAGE;

        // Add filters
        currUrl += imageFilters.generateFilterQuery();

        Log.i("Search", "Using search URL: "+ currUrl);

        client.get(this, currUrl, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // responseData -> results -> [x] -> tbUrl, title, url
                try {
                    aResults.addAll(ImageResult.fromJSONArray(response.getJSONObject("responseData").getJSONArray("results")));
                    aResults.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_search, menu);
        return true;
    }

    private void getSearchFilters () {
        Intent i = new Intent(this, FiltersActivity.class);
        i.putExtra("old_filters", imageFilters);
        startActivityForResult(i, FILTER_RESULT_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == FILTER_RESULT_OK) {
            //String website = data.getStringExtra("website");
            imageFilters = (ImageFilters) data.getSerializableExtra("filters");
            Toast.makeText(this, "Got filter data. Filter query: "+imageFilters.generateFilterQuery(), Toast.LENGTH_SHORT).show();
        }
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

        // Display filters
        if (id == R.id.filter_settings) {
            // Launch new activity to get user input
            getSearchFilters();
            // Return false to allow normal menu processing to proceed, true to consume it here.
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
