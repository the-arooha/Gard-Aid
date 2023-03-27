package com.example.loginactivity.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.loginactivity.CommentsActivity;
import com.example.loginactivity.Model.Post;
import com.example.loginactivity.Model.User;
import com.example.loginactivity.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class PostAdapter extends  RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public Context mContext;
    public List<Post> mPostList;
    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPostList) {
        this.mContext = mContext;
        this.mPostList = mPostList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.questions_retrived_layout,parent,false);

        return new PostAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        final Post post=mPostList.get(position);
        if (post.getQuestionimage()==null){
            holder.questionImage.setVisibility(View.GONE);
        }
        holder.questionImage.setVisibility(View.VISIBLE);
        Glide.with(mContext).load(post.getQuestionimage()).into(holder.questionImage);
        holder.expandable_text.setText(post.getQuestion());
        holder.topicTextView.setText(post.getTopic());
        holder.askedOnTextView.setText(post.getDate());



        publisherInformation(holder.publisher_profile_image,holder.asked_by_Textview,post.getPublisher());
        isLiked(post.getPostid(),holder.like);
        isDisLiked(post.getPostid(),holder.dislike);
        getLikes(holder.likes,post.getPostid());
        getDisLikes(holder.dislikes,post.getPostid());

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.like.getTag().equals("like") && holder.dislike.getTag().equals("dislike")){
                    FirebaseDatabase.getInstance().getReference().child("likes").child(post.getPostid()).child(firebaseUser.getUid()).setValue(true);

                }
                else if (holder.like.getTag().equals("like") && holder.dislike.getTag().equals("disliked")){
                    FirebaseDatabase.getInstance().getReference().child("dislikes").child(post.getPostid()).child(firebaseUser.getUid()).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("likes").child(post.getPostid()).child(firebaseUser.getUid()).setValue(true);


                }else {
                    FirebaseDatabase.getInstance().getReference().child("likes").child(post.getPostid()).child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.dislike.getTag().equals("dislike") && holder.like.getTag().equals("like")){
                    FirebaseDatabase.getInstance().getReference().child("dislikes").child(post.getPostid()).child(firebaseUser.getUid()).setValue(true);
                }else if (holder.dislike.getTag().equals("dislike") && holder.like.getTag().equals("liked")){
                    FirebaseDatabase.getInstance().getReference().child("likes").child(post.getPostid()).child(firebaseUser.getUid()).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("dislikes").child(post.getPostid()).child(firebaseUser.getUid()).setValue(true);
                }else {
                    FirebaseDatabase.getInstance().getReference().child("dislikes").child(post.getPostid()).child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, CommentsActivity.class);
                intent.putExtra("postid",post.getPostid());
                intent.putExtra("publisher",post.getPublisher());
                mContext.startActivity(intent);
            }
        });

        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,CommentsActivity.class);
                intent.putExtra("postid",post.getPostid());
                intent.putExtra("publisher",post.getPublisher());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return mPostList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView publisher_profile_image;
        public TextView asked_by_Textview,likes,dislikes,comments;
        public ImageView more,questionImage,like,dislike,comment,save;
        public TextView topicTextView,askedOnTextView;
        public ExpandableTextView expandable_text;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            publisher_profile_image=itemView.findViewById(R.id.publisher_profile_image);
            asked_by_Textview=itemView.findViewById(R.id.asked_by_Textview);
            likes=itemView.findViewById(R.id.likes);
            dislikes=itemView.findViewById(R.id.dislikes);
            comments=itemView.findViewById(R.id.comments);
            more=itemView.findViewById(R.id.more);
            questionImage=itemView.findViewById(R.id.questionImage);
            like=itemView.findViewById(R.id.like);
            dislike=itemView.findViewById(R.id.dislike);
            comment=itemView.findViewById(R.id.comment);
            topicTextView=itemView.findViewById(R.id.topicTextView);
            askedOnTextView=itemView.findViewById(R.id.askedOnTextView);
            expandable_text=itemView.findViewById(R.id.expand_text_view);

        }
    }

    private void publisherInformation(ImageView publisherImage,TextView askedBY,String userid){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                Glide.with(mContext).load(user.getProfileimageurl()).into(publisherImage);
                askedBY.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void isLiked(String postid,ImageView imageview){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if(snapshot.child(firebaseUser.getUid()).exists()){
                  imageview.setImageResource(R.drawable.ic_liked);
                  imageview.setTag("liked");
              } else{
                  imageview.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                  imageview.setTag("like");
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void isDisLiked(String postid,ImageView imageview){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("dislikes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(firebaseUser.getUid()).exists()){
                    imageview.setImageResource(R.drawable.ic_disliked);
                    imageview.setTag("disliked");
                } else{
                    imageview.setImageResource(R.drawable.ic_dislike);
                    imageview.setTag("dislike");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getLikes(TextView likes,String postid){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long numberOfLikes=snapshot.getChildrenCount();
                int NOL=(int)numberOfLikes;
                if(NOL>1){
                    likes.setText(snapshot.getChildrenCount()+" likes");
                }else if(NOL==0){
                    likes.setText("0 likes");
                }else{
                    likes.setText(snapshot.getChildrenCount()+" like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDisLikes(TextView dislikes,String postid){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("dislikes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long numberOfDisLikes=snapshot.getChildrenCount();
                int NOD=(int)numberOfDisLikes;
                if(NOD>1){
                    dislikes.setText(snapshot.getChildrenCount()+" dislikes");
                }else if(NOD==0){
                    dislikes.setText("0 dislikes");
                }else{
                    dislikes.setText(snapshot.getChildrenCount()+" dislike");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
