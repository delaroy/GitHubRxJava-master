package com.delaroystudios.githubrxjava;

import android.support.annotation.NonNull;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

//import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.guava.GuavaCallAdapterFactory;
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
//import rx.Observable;

public class GitHubClient {

    private static final String GITHUB_BASE_URL = "https://api.github.com/";

    private static GitHubClient instance;
    private GitHubService gitHubService;

    public GitHubClient() {
        final Gson gson =
            new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(GITHUB_BASE_URL)
                                                        .addCallAdapterFactory(GuavaCallAdapterFactory.create())
                                                        .addConverterFactory(GsonConverterFactory.create(gson))
                                                        .build();
        gitHubService = retrofit.create(GitHubService.class);
    }

    public static GitHubClient getInstance() {
        if (instance == null) {
            instance = new GitHubClient();
        }
        return instance;
    }

   /* public Observable<List<GitHubRepo>> getStarredRepos(@NonNull String userName) {
        return gitHubService.getStarredRepositories(userName);
    }*/

    /*public  io.reactivex.Observable<List<GitHubRepo>> getStarredRepos(@NonNull String userName){
        return gitHubService.getStarredRepositories(userName);
    }*/

    public ListenableFuture<Response<List<GitHubRepo>>> getStarredRepos(@NonNull String userName){

        return gitHubService.getStarredRepositories(userName);

    }
}


