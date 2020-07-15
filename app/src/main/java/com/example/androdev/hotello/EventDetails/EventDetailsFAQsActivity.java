package com.example.androdev.hotello.EventDetails;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.androdev.hotello.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventDetailsFAQsActivity extends AppCompatActivity {



    DatabaseReference parentReference;
    DatabaseReference eventReference;


    FirebaseUser user;


    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private LinearLayoutManager mLayoutManager;


    List<EventDetailsFAQsData> posts;

    int position=-2;


    String EventKey;

    android.support.v7.widget.Toolbar toolbar;

    String EventNameS;
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details_faqs);



        progressBar=(ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(null);

        user = FirebaseAuth.getInstance().getCurrentUser();
        parentReference = FirebaseDatabase.getInstance().getReference();
        parentReference=parentReference.child("HotelLo");
        eventReference = FirebaseDatabase.getInstance().getReference();
        eventReference=eventReference.child("Hotels");


        Intent i = getIntent();
        EventKey = i.getStringExtra("EventKey");
        EventNameS=i.getStringExtra("EventName");
        toolbar.setTitle(EventNameS);

        posts = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_faqs);
        mLayoutManager = new LinearLayoutManager(EventDetailsFAQsActivity.this);
        mAdapter = new EventDetailsFAQsActivity.FAQAdapter(EventDetailsFAQsActivity.this, posts);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        posts.clear();



        fetch();


        eventReference.child(EventKey).child("FAQs").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String QuesX = dataSnapshot.child("Ques").getValue(String.class);
                String AnsX = dataSnapshot.child("Ans").getValue(String.class);

                posts.add(new EventDetailsFAQsData(QuesX, AnsX));
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }






    class FAQAdapter extends RecyclerView.Adapter<EventDetailsFAQsActivity.FAQAdapter.MyViewHolder>{

        private List<EventDetailsFAQsData> posts;
        private Context context;



        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView Ques;
            TextView Ans;
            View view3;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                Ques=(TextView)itemView.findViewById(R.id.ques);
                Ans=(TextView)itemView.findViewById(R.id.ans);
                view3=itemView.findViewById(R.id.view3);
            }


        }

        public FAQAdapter(Context context,List<EventDetailsFAQsData> posts) {
            this.context=context;
            this.posts=posts;

        }


        @NonNull
        @Override
        public FAQAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_event_details_faqs,parent,false);
            EventDetailsFAQsActivity.FAQAdapter.MyViewHolder vh = new  EventDetailsFAQsActivity.FAQAdapter.MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull FAQAdapter.MyViewHolder holder, int i) {

            final EventDetailsFAQsData c = posts.get(i);
            holder.Ques.setText(c.getQues());
            holder.Ans.setText(c.getAns());

            if(i==posts.size()-1){
                holder.view3.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public int getItemCount() {
            return posts.size();
        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void fetch() {




    }


}
