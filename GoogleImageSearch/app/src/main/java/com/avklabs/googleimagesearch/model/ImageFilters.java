package com.avklabs.googleimagesearch.model;

import java.io.Serializable;

/**
 * Created by avkadam on 3/1/15.
 */
public class ImageFilters implements Serializable{

    //filters: &imgcolor=blue|black|...|&
    // &imgsz=icon|small|medium|large|xlarge|xxlarge|huge
    // &as_filetype=jpg|png|gif|bmp
    // &as_sitesearch=espn.com
    // &imgtype=face|photo|clipart|lineart

    private String website;
    private String imgColor;
    private String fileType;
    private String imgSize;
    private String imgType;

    public ImageFilters() {
        website = "";
        imgColor = "";
        fileType = "";
        imgSize = "";
        imgType = "";
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setImgColor(String imgColor) {
        this.imgColor = imgColor;
    }

    public void setFileType(String filetype) {
        this.fileType = filetype;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    public String getWebsite() {
        return website;
    }

    public String getImgColor() {
        return imgColor;
    }

    public String getFileType() {
        return fileType;
    }

    public String getImgSize() {
        return imgSize;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String generateFilterQuery () {
        String filterQ="";

        if (website.length() > 0) {
            filterQ = filterQ + "&as_sitesearch="+website;
        }

        if (imgColor.length() > 0) {
            filterQ = filterQ + "&imgcolor="+imgColor;
        }

        if (fileType.length() > 0) {
            filterQ = filterQ + "&as_filetype="+fileType;
        }

        if (imgSize.length() > 0) {
            filterQ = filterQ + "&imgsz="+imgSize;
        }

        if (imgType.length() > 0) {
            filterQ = filterQ + "&imgtype="+imgType;
        }

        return filterQ;
    }

}
