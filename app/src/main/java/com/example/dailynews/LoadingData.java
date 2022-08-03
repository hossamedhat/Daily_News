package com.example.dailynews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.dailynews.retrofit.ApiClient;
import com.example.dailynews.retrofit.ApiInterface;
import com.example.dailynews.retrofit.BaseClass;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingData {
   public static ArrayList<News> headLinesList = new ArrayList<>();
    public static ArrayList<News> sportsList= new ArrayList<>();
    public static ArrayList<News> bussinessList= new ArrayList<>();
    public static ArrayList<News> scienceList= new ArrayList<>();
    public static ArrayList<News> technologyList= new ArrayList<>();
    public static ArrayList<News> healthList= new ArrayList<>();
    public static ArrayList<News> entartinmentList= new ArrayList<>();
    public static ArrayList<NewsOffLine> dataBaseHeadLineOffline = new ArrayList<>();
    public static ArrayList<NewsOffLine> dataBaseSportOffline = new ArrayList<>();
    public static ArrayList<NewsOffLine> dataBaseBussinessOffline = new ArrayList<>();
    public static ArrayList<NewsOffLine> dataBaseScienceOffline = new ArrayList<>();
    public static ArrayList<NewsOffLine> dataBaseTechnologyOffline = new ArrayList<>();
    public static ArrayList<NewsOffLine> dataBaseHealthOffline = new ArrayList<>();
    public static ArrayList<NewsOffLine> dataBaseEntartinmentOffline = new ArrayList<>();
    final static String API_NUMBER="11cbb68249454fdb90edc5086f6fd24a";



    public static String name;
    public  static String date;

    public static void getHeadLines(String country){
        headLinesList = new ArrayList<>();
        dataBaseHeadLineOffline = new ArrayList<>();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseClass> call = apiInterface.getNews(country,"",API_NUMBER);
        call.enqueue(new Callback<BaseClass>() {
            @Override
            public void onResponse(Call<BaseClass> call, Response<BaseClass> response) {
                for (int i =0; i <20; i++) {
                    String img_source1 = getImgSource(response.body().getArticles().get(i).getNewsUrl());
                    String img_news1= response.body().getArticles().get(i).getImageUrl();
                    String nameTitle=response.body().getArticles().get(i).getSources().getName();
                    name="";
                    String date_url = response.body().getArticles().get(i).getDateUrl();
                    date="";
                    for (int k=0;k<nameTitle.length();k++){
                        if(nameTitle.charAt(k) == '.'  ){
                            break;
                        }
                        else {
                            name+=nameTitle.charAt(k);
                        }
                    }
                    for (int k=0;k<date_url.length();k++){
                        if(date_url.charAt(k) == 'T'  ){
                            date+=" ";
                            continue;
                        }
                        else if(date_url.charAt(k) == 'Z') {
                            break;
                        }else {
                            date+=date_url.charAt(k);
                        }

                    }
                    String titleNews = response.body().getArticles().get(i).getTitle();
                    String description = response.body().getArticles().get(i).getDescription();
                    String newsUrl = response.body().getArticles().get(i).getNewsUrl();
                    headLinesList.add(new News(img_news1,img_source1,name,date,titleNews,description,newsUrl));
                    dataBaseHeadLineOffline.add(new NewsOffLine(titleNews,name,date,description));
                    //Insert To Database
                }


            }
            @Override
            public void onFailure(Call<BaseClass> call, Throwable t) {
                Log.e("getApiError",t.getMessage());
            }
        });
    }
    public static void getSportNews(String country){
        sportsList= new ArrayList<>();
        dataBaseSportOffline = new ArrayList<>();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseClass> call = apiInterface.getNews(country,"sports",API_NUMBER);
        call.enqueue(new Callback<BaseClass>() {
            @Override
            public void onResponse(Call<BaseClass> call, Response<BaseClass> response) {
                for (int i =0; i <20; i++) {
                    String img_source1 = getImgSource(response.body().getArticles().get(i).getNewsUrl());
                    String img_news1= response.body().getArticles().get(i).getImageUrl();
                    String nameTitle=response.body().getArticles().get(i).getSources().getName();
                    name="";
                    String date_url = response.body().getArticles().get(i).getDateUrl();
                    date="";
                    for (int k=0;k<nameTitle.length();k++){
                        if(nameTitle.charAt(k) == '.'  ){
                            break;
                        }
                        else {
                            name+=nameTitle.charAt(k);
                        }
                    }
                    for (int k=0;k<date_url.length();k++){
                        if(date_url.charAt(k) == 'T'  ){
                            date+=" ";
                            continue;
                        }
                        else if(date_url.charAt(k) == 'Z') {
                            break;
                        }else {
                            date+=date_url.charAt(k);
                        }

                    }
                    String titleNews = response.body().getArticles().get(i).getTitle();
                    String description = response.body().getArticles().get(i).getDescription();
                    String newsUrl = response.body().getArticles().get(i).getNewsUrl();
                    sportsList.add(new News(img_news1,img_source1,name,date,titleNews,description,newsUrl));
                    dataBaseSportOffline.add(new NewsOffLine(titleNews,name,date,description));

                    //Insert To Database
                }

            }
            @Override
            public void onFailure(Call<BaseClass> call, Throwable t) {
                Log.e("getApiError",t.getMessage());
            }
        });
    }
    public static void getBussinessNews(String country ){
        bussinessList= new ArrayList<>();
        dataBaseBussinessOffline = new ArrayList<>();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseClass> call = apiInterface.getNews(country,"business",API_NUMBER);
        call.enqueue(new Callback<BaseClass>() {
            @Override
            public void onResponse(Call<BaseClass> call, Response<BaseClass> response) {
                for (int i =0; i <20; i++) {
                    String img_source1 = getImgSource(response.body().getArticles().get(i).getNewsUrl());
                    String img_news1= response.body().getArticles().get(i).getImageUrl();
                    String nameTitle=response.body().getArticles().get(i).getSources().getName();
                    name="";
                    String date_url = response.body().getArticles().get(i).getDateUrl();
                    date="";
                    for (int k=0;k<nameTitle.length();k++){
                        if(nameTitle.charAt(k) == '.'  ){
                            break;
                        }
                        else {
                            name+=nameTitle.charAt(k);
                        }
                    }
                    for (int k=0;k<date_url.length();k++){
                        if(date_url.charAt(k) == 'T'  ){
                            date+=" ";
                            continue;
                        }
                        else if(date_url.charAt(k) == 'Z') {
                            break;
                        }else {
                            date+=date_url.charAt(k);
                        }

                    }
                    String titleNews = response.body().getArticles().get(i).getTitle();
                    String description = response.body().getArticles().get(i).getDescription();
                    String newsUrl = response.body().getArticles().get(i).getNewsUrl();
                    bussinessList.add(new News(img_news1,img_source1,name,date,titleNews,description,newsUrl));
                    dataBaseBussinessOffline.add(new NewsOffLine(titleNews,name,date,description));

                    //Insert To Database
                }

            }
            @Override
            public void onFailure(Call<BaseClass> call, Throwable t) {
                Log.e("getApiError",t.getMessage());
            }
        });
    }
    public static void getScienceNews(String country ){
        scienceList= new ArrayList<>();
        dataBaseScienceOffline = new ArrayList<>();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseClass> call = apiInterface.getNews(country,"science",API_NUMBER);
        call.enqueue(new Callback<BaseClass>() {
            @Override
            public void onResponse(Call<BaseClass> call, Response<BaseClass> response) {
                for (int i =0; i <20; i++) {
                    String img_source1 = getImgSource(response.body().getArticles().get(i).getNewsUrl());
                    String img_news1= response.body().getArticles().get(i).getImageUrl();
                    String nameTitle=response.body().getArticles().get(i).getSources().getName();
                    name="";
                    String date_url = response.body().getArticles().get(i).getDateUrl();
                    date="";
                    for (int k=0;k<nameTitle.length();k++){
                        if(nameTitle.charAt(k) == '.'  ){
                            break;
                        }
                        else {
                            name+=nameTitle.charAt(k);
                        }
                    }
                    for (int k=0;k<date_url.length();k++){
                        if(date_url.charAt(k) == 'T'  ){
                            date+=" ";
                            continue;
                        }
                        else if(date_url.charAt(k) == 'Z') {
                            break;
                        }else {
                            date+=date_url.charAt(k);
                        }

                    }
                    String titleNews = response.body().getArticles().get(i).getTitle();
                    String description = response.body().getArticles().get(i).getDescription();
                    String newsUrl = response.body().getArticles().get(i).getNewsUrl();
                    scienceList.add(new News(img_news1,img_source1,name,date,titleNews,description,newsUrl));
                    dataBaseScienceOffline.add(new NewsOffLine(titleNews,name,date,description));
                    //Insert To Database
                }

            }
            @Override
            public void onFailure(Call<BaseClass> call, Throwable t) {
                Log.e("getApiError",t.getMessage());
            }
        });
    }
    public static void getTechnology(String country ){
        technologyList= new ArrayList<>();
        dataBaseTechnologyOffline = new ArrayList<>();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseClass> call = apiInterface.getNews(country,"technology",API_NUMBER);
        call.enqueue(new Callback<BaseClass>() {
            @Override
            public void onResponse(Call<BaseClass> call, Response<BaseClass> response) {
                for (int i =0; i <20; i++) {
                    String img_source1 = getImgSource(response.body().getArticles().get(i).getNewsUrl());
                    String img_news1= response.body().getArticles().get(i).getImageUrl();
                    String nameTitle=response.body().getArticles().get(i).getSources().getName();
                    name="";
                    String date_url = response.body().getArticles().get(i).getDateUrl();
                    date="";
                    for (int k=0;k<nameTitle.length();k++){
                        if(nameTitle.charAt(k) == '.'  ){
                            break;
                        }
                        else {
                            name+=nameTitle.charAt(k);
                        }
                    }
                    for (int k=0;k<date_url.length();k++){
                        if(date_url.charAt(k) == 'T'  ){
                            date+=" ";
                            continue;
                        }
                        else if(date_url.charAt(k) == 'Z') {
                            break;
                        }else {
                            date+=date_url.charAt(k);
                        }

                    }
                    String titleNews = response.body().getArticles().get(i).getTitle();
                    String description = response.body().getArticles().get(i).getDescription();
                    String newsUrl = response.body().getArticles().get(i).getNewsUrl();
                    technologyList.add(new News(img_news1,img_source1,name,date,titleNews,description,newsUrl));
                    dataBaseTechnologyOffline.add(new NewsOffLine(titleNews,name,date,description));
                    //Insert To Database
                }

            }
            @Override
            public void onFailure(Call<BaseClass> call, Throwable t) {
                Log.e("getApiError",t.getMessage());
            }
        });
    }
    public static void getHealthList(String country ){
        healthList= new ArrayList<>();
        dataBaseHealthOffline = new ArrayList<>();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseClass> call = apiInterface.getNews(country,"health",API_NUMBER);
        call.enqueue(new Callback<BaseClass>() {
            @Override
            public void onResponse(Call<BaseClass> call, Response<BaseClass> response) {
                for (int i =0; i <20; i++) {
                    String img_source1 = getImgSource(response.body().getArticles().get(i).getNewsUrl());
                    String img_news1= response.body().getArticles().get(i).getImageUrl();
                    String nameTitle=response.body().getArticles().get(i).getSources().getName();
                    name="";
                    String date_url = response.body().getArticles().get(i).getDateUrl();
                    date="";
                    for (int k=0;k<nameTitle.length();k++){
                        if(nameTitle.charAt(k) == '.'  ){
                            break;
                        }
                        else {
                            name+=nameTitle.charAt(k);
                        }
                    }
                    for (int k=0;k<date_url.length();k++){
                        if(date_url.charAt(k) == 'T'  ){
                            date+=" ";
                            continue;
                        }
                        else if(date_url.charAt(k) == 'Z') {
                            break;
                        }else {
                            date+=date_url.charAt(k);
                        }

                    }
                    String titleNews = response.body().getArticles().get(i).getTitle();
                    String description = response.body().getArticles().get(i).getDescription();
                    String newsUrl = response.body().getArticles().get(i).getNewsUrl();
                    healthList.add(new News(img_news1,img_source1,name,date,titleNews,description,newsUrl));
                    dataBaseHealthOffline.add(new NewsOffLine(titleNews,name,date,description));

                    //Insert To Database
                }

            }
            @Override
            public void onFailure(Call<BaseClass> call, Throwable t) {
                Log.e("getApiError",t.getMessage());
            }
        });
    }
    public static void getEntartinmentNews(String country ){
        entartinmentList= new ArrayList<>();
        dataBaseEntartinmentOffline = new ArrayList<>();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseClass> call = apiInterface.getNews(country,"entertainment",API_NUMBER);
        call.enqueue(new Callback<BaseClass>() {
            @Override
            public void onResponse(Call<BaseClass> call, Response<BaseClass> response) {
                for (int i =0; i <20; i++) {
                    String img_source1 = getImgSource(response.body().getArticles().get(i).getNewsUrl());
                    String img_news1= response.body().getArticles().get(i).getImageUrl();
                    String nameTitle=response.body().getArticles().get(i).getSources().getName();
                    name="";
                    String date_url = response.body().getArticles().get(i).getDateUrl();
                    date="";
                    for (int k=0;k<nameTitle.length();k++){
                        if(nameTitle.charAt(k) == '.'  ){
                            break;
                        }
                        else {
                            name+=nameTitle.charAt(k);
                        }
                    }
                    for (int k=0;k<date_url.length();k++){
                        if(date_url.charAt(k) == 'T'  ){
                            date+=" ";
                            continue;
                        }
                        else if(date_url.charAt(k) == 'Z') {
                            break;
                        }else {
                            date+=date_url.charAt(k);
                        }

                    }
                    String titleNews = response.body().getArticles().get(i).getTitle();
                    String description = response.body().getArticles().get(i).getDescription();
                    String newsUrl = response.body().getArticles().get(i).getNewsUrl();
                    entartinmentList.add(new News(img_news1,img_source1,name,date,titleNews,description,newsUrl));
                    dataBaseEntartinmentOffline.add(new NewsOffLine(titleNews,name,date,description));

                    //Insert To Database
                }

            }
            @Override
            public void onFailure(Call<BaseClass> call, Throwable t) {
                Log.e("getApiError",t.getMessage());
            }
        });
    }

    public static String getImgSource(String uri){
        int k=0;
        String new_url="";
        String url=uri;
        for(int i=0;i<url.length();i++){
            if(url.charAt(i)=='/' && k<3){
                k++;
                continue;
            }else if(k==3){
                break;
            }
            else{
                new_url+=url.charAt(i);
            }
        }
        new_url+="/favicon.ico";
        return new_url;
    }

}
