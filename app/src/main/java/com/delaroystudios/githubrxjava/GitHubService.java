package com.delaroystudios.githubrxjava;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
//import rx.Observable;


public interface GitHubService {
   // @GET("users/{user}/starred") Observable<List<GitHubRepo>> getStarredRepositories(@Path("user") String userName);

   /* @GET("users/{user}/starred")
    io.reactivex.Observable<List<GitHubRepo>> getStarredRepositories(@Path("user") String userName);*/

    @GET("users/{user}/starred")
    ListenableFuture<Response<List<GitHubRepo>>> getStarredRepositories(@Path("user") String userName);

}
