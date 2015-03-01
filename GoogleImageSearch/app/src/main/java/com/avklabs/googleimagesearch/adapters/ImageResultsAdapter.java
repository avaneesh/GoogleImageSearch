package com.avklabs.googleimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avklabs.googleimagesearch.R;
import com.avklabs.googleimagesearch.model.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by avkadam on 2/24/15.
 */
public class ImageResultsAdapter extends ArrayAdapter {

    public ImageResultsAdapter(Context context, List objects) {
        super(context, R.layout.item_image_result, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageResult imageResult = (ImageResult) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);

        tvTitle.setText(Html.fromHtml(imageResult.title));
        Picasso.with(getContext()).load(imageResult.thumb_url).into(ivImage);

        return convertView;
    }
}
