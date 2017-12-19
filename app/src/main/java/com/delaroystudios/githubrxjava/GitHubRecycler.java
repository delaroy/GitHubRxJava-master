package com.delaroystudios.githubrxjava;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by delaroy on 12/18/17.
 */

public class GitHubRecycler extends
        RecyclerView.Adapter<GitHubRecycler.GitHubRepoViewHolder> {

    private Context mContext;
    private List<GitHubRepo> gitHubRepos = new ArrayList<>();

    public GitHubRecycler(Context context){

        this.mContext = context;

    }


    @Override
    public GitHubRepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_github_repo, parent, false);
        return new GitHubRepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GitHubRepoViewHolder holder, int position) {
        holder.textRepoName.setText(gitHubRepos.get(position).name);
        holder.textRepoDescription.setText(gitHubRepos.get(position).description);
        holder.textLanguage.setText("Language: " + gitHubRepos.get(position).language);
        holder.textStars.setText("Stars: " + gitHubRepos.get(position).stargazersCount);

    }

    public void setGitHubRepos(@Nullable List<GitHubRepo> repos) {
        if (repos == null) {
            return;
        }
        gitHubRepos.clear();
        gitHubRepos.addAll(repos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return gitHubRepos.size();
    }

    public class GitHubRepoViewHolder extends RecyclerView.ViewHolder {

        TextView textRepoName;
        TextView textRepoDescription;
        TextView textLanguage;
        TextView textStars;

        public GitHubRepoViewHolder(View itemView) {
            super(itemView);


            textRepoName = (TextView) itemView.findViewById(R.id.text_repo_name);
            textRepoDescription = (TextView) itemView.findViewById(R.id.text_repo_description);
            textLanguage = (TextView) itemView.findViewById(R.id.text_language);
            textStars = (TextView) itemView.findViewById(R.id.text_stars);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });



        }
    }

}
