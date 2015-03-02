package com.avklabs.googleimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.avklabs.googleimagesearch.R;
import com.avklabs.googleimagesearch.model.ImageFilters;

public class FiltersActivity extends ActionBarActivity {

    //public static final int RESULT_OK = 200;

    EditText et;
    Spinner spColor;
    Spinner spFileType;
    Spinner spImageSize;
    Spinner spImgType;

    ImageFilters imageFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        // Use existing filer obj to populate views
        imageFilters = (ImageFilters) getIntent().getSerializableExtra("old_filters");

        setupViews(imageFilters);

    }

    private void setupViews(ImageFilters imageFilters) {
        et = (EditText) findViewById(R.id.etWebsite);
        et.setText(imageFilters.getWebsite());
        et.setSelection(imageFilters.getWebsite().length());

        spColor = (Spinner) findViewById(R.id.spColor);
        ArrayAdapter <CharSequence> spColorAdapter =
                ArrayAdapter.createFromResource(this, R.array.color_filters, android.R.layout.simple_spinner_dropdown_item);
        spColor.setAdapter(spColorAdapter);
        spColor.setSelection(spColorAdapter.getPosition(imageFilters.getImgColor()));

        spFileType = (Spinner) findViewById(R.id.spType);
        ArrayAdapter<CharSequence> spTypeAdapter =
                ArrayAdapter.createFromResource(this, R.array.filetype_filters, android.R.layout.simple_spinner_dropdown_item);
        spFileType.setAdapter(spTypeAdapter);
        spFileType.setSelection(spTypeAdapter.getPosition(imageFilters.getFileType()));

        spImageSize = (Spinner) findViewById(R.id.spSize);
        ArrayAdapter<CharSequence> spSizeAdapter =
                ArrayAdapter.createFromResource(this, R.array.size_filters, android.R.layout.simple_spinner_dropdown_item);
        spImageSize.setAdapter(spSizeAdapter);
        spImageSize.setSelection(spSizeAdapter.getPosition(imageFilters.getImgSize()));

        spImgType = (Spinner) findViewById(R.id.spImgType);
        ArrayAdapter<CharSequence> spImgTypeAdapter =
                ArrayAdapter.createFromResource(this, R.array.image_type_filters, android.R.layout.simple_spinner_dropdown_item);
        spImgType.setAdapter(spImgTypeAdapter);
        spImgType.setSelection(spImgTypeAdapter.getPosition(imageFilters.getImgType()));
    }

    // save button onclick
    public void onSettingsSave(View v) {

        String website = et.getText().toString();
        String imgColor = spColor.getSelectedItem().toString();
        String fileType = spFileType.getSelectedItem().toString();
        String imgSize = spImageSize.getSelectedItem().toString();
        String imgType = spImgType.getSelectedItem().toString();


        // Populate filters model
        imageFilters = new ImageFilters(); // get new filters obj

        imageFilters.setWebsite(website);
        imageFilters.setImgColor(imgColor);
        imageFilters.setFileType(fileType);
        imageFilters.setImgSize(imgSize);
        imageFilters.setImgType(imgType);

        // pass the data back to the parent.
        Intent data = new Intent();
        data.putExtra("filters", imageFilters);
        setResult(ImageSearchActivity.FILTER_RESULT_OK, data);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filters, menu);
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
