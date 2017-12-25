package com.delaroystudios.githubrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.delaroystudios.githubrxjava.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;

//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.guava.GuavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
//import rx.Observer;
//import rx.Subscription;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GitHubRepoAdapter adapter = new GitHubRepoAdapter();
   // private Subscription subscription;
   //private Disposable disposable;

    GitHubRecycler recyclerAdapter = new GitHubRecycler(this);

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final ListView listView = (ListView) findViewById(R.id.list_view_repos);
        //listView.setAdapter(adapter);

        recyclerView = (RecyclerView) findViewById(R.id.list_view_repos);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final EditText editTextUsername = (EditText) findViewById(R.id.edit_text_username);
        final Button buttonSearch = (Button) findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editTextUsername.getText().toString();
                if (!TextUtils.isEmpty(username)) {
                    getStarredRepos(username);
                }
            }
        });
    }

    /*@Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        super.onDestroy();
    }
*/
   /* @Override
    protected void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }*/

   /* private void getStarredRepos(String username) {
        subscription = GitHubClient.getInstance()
                                   .getStarredRepos(username)
                                   .subscribeOn(Schedulers.io())
                                   .observeOn(AndroidSchedulers.mainThread())
                                   .subscribe(new Observer<List<GitHubRepo>>() {
                                       @Override
                                       public void onCompleted() {
                                           Log.d(TAG, "In onCompleted()");
                                       }

                                       @Override
                                       public void onError(Throwable e) {
                                           e.printStackTrace();
                                           Log.d(TAG, "In onError()");
                                       }

                                       @Override
                                       public void onNext(List<GitHubRepo> gitHubRepos) {
                                           Log.d(TAG, "In onNext()");
                                           recyclerAdapter.setGitHubRepos(gitHubRepos);
                                       }
                                   });
    }*/

    /*private void getStarredRepos(String username) {

        disposable = GitHubClient.getInstance()
                .getStarredRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<GitHubRepo>>() {
                            @Override
                            public void accept(List<GitHubRepo> gitHubRepos) throws Exception {
                                Log.i(TAG, "RxJava2: Response from server ...");
                                recyclerAdapter.setGitHubRepos(gitHubRepos);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable t) throws Exception {
                                Log.i(TAG, "RxJava2, HTTP Error: " + t.getMessage());
                            }
                        }
                );


    }
*/

    private void getStarredRepos(String username) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(GuavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        GitHubService gitHubService = retrofit.create(GitHubService.class);

        ListenableFuture<Response<List<GitHubRepo>>> listenableFuture = gitHubService.getStarredRepositories(username);

        try {
            Response<List<GitHubRepo>> response = listenableFuture.get();
            List<GitHubRepo> gitHubRepos = response.body();
            Log.i(TAG, "Guava: Response from server ...");

            recyclerAdapter.setGitHubRepos(gitHubRepos);
        } catch (InterruptedException | ExecutionException e) {
            Log.i(TAG, "Guava, Error: " + e.getMessage());
        }
    }
}
