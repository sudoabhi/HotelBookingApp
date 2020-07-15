package com.example.androdev.hotello.EventDetails;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.androdev.hotello.R;

import com.example.androdev.hotello.UserFragments.FirebaseCallBackXM;
import com.example.androdev.hotello.UserFragments.FirebaseCallbackX;
import com.example.androdev.hotello.UserFragments.FirebaseCallbackXXXX;
import com.example.androdev.hotello.UserFragments.FirebaseCallbackXXXXX;
import com.example.androdev.hotello.UserFragments.InterestedPeople;


import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import com.squareup.picasso.Callback;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventDetail extends AppCompatActivity {

    KenBurnsView EventBannerView;
    DatabaseReference eventReference;
    String EventKey;
    TextView EventName;
    TextView EventDetail;
    String EventBannerURL;
    TextView Location;
    ProgressBar progressBar2;
    ProgressBar progress_bar;


    TextView InterestedCountT;

    String EventBannerURLX;
    String todays_date;
    String todays_time;

    TextView PriceSegmentTV;
    CircleImageView InterestedPhoto1;
    CircleImageView InterestedPhoto2;
    ImageView FAQButton;
    DatabaseReference parentReference;
    DatabaseReference adminReference;
    ImageView TicketsButton;
    String thiseventurl;
    ImageView OrganiserButton;
    String FAQStatus;
    FirebaseUser user;
    String TicketsStatus;
    String OrganiserStatus;
    SharedPreferences myPrefs2;
    SharedPreferences.Editor prefsEditor2;

    RelativeLayout parent_view;


    Toolbar toolbar;

    ImageView bookmark;
    ImageView like;
    ImageView share;

    int px9;
    ScrollView scrollView;
    FrameLayout event_banner_container;

    String Bookmark;
    String Like;
    String InterestedPhoto1SS;
    String InterestedPhoto2SS;
    String[] MyImageURL;
    long[] InterestedCount;
    String URLChecker;


    String InterestedCountInit;
    String InterestedCountFinal;
    int position=-2;

    TextView InterestedTextView;
    FrameLayout InterestedPhotosFL;
    String EventNameX;

    String UserNameX;
    String ClubLocationX;







    CardView cv_faq;
    CardView cv_organiser;
    CardView cv_ticket;

    Button interestedB;
    TextView interestedT;
    ImageView interestedIV;

    int likeCount=0;
    int bookmarkCount=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail2);

     //   getTodaysTime();

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        scrollView = findViewById(R.id.scrollView);
        EventBannerView = findViewById(R.id.event_banner);

        RandomTransitionGenerator generator = new RandomTransitionGenerator(5000,new AccelerateDecelerateInterpolator());
        EventBannerView.setTransitionGenerator(generator);



        event_banner_container=findViewById(R.id.event_banner_container);
        parent_view=(RelativeLayout) findViewById(R.id.parent_view);
        FAQStatus = "Closed";
        OrganiserStatus = "Closed";
        TicketsStatus = "Closed";
        progressBar2=(ProgressBar) findViewById(R.id.progress_bar2);
        PriceSegmentTV=(TextView) findViewById(R.id.price_segment);


        user = FirebaseAuth.getInstance().getCurrentUser();
        EventDetail = (TextView) findViewById(R.id.event_details);
        EventName = (TextView) findViewById(R.id.event_name);
        Location = (TextView) findViewById(R.id.location);


        InterestedCountT = (TextView) findViewById(R.id.interested_count);
        InterestedPhoto1 = (CircleImageView) findViewById(R.id.interested_photo1);
        InterestedPhoto2 = (CircleImageView) findViewById(R.id.interested_photo2);

        FAQButton = (ImageView) findViewById(R.id.faq_button);
        TicketsButton = (ImageView) findViewById(R.id.tickets_button);
        OrganiserButton = (ImageView) findViewById(R.id.organisers_button);
        progress_bar=(ProgressBar) findViewById(R.id.progress_bar);
        bookmark = (ImageView) findViewById(R.id.bookmark_event);
        like = (ImageView) findViewById(R.id.like_event);
        share = (ImageView) findViewById(R.id.share_event);

        InterestedTextView=(TextView)findViewById(R.id.interested_tv);
        InterestedPhotosFL=(FrameLayout)findViewById(R.id.interested_photos);
        myPrefs2 = this.getSharedPreferences("myPrefs2", MODE_PRIVATE);
        prefsEditor2 = myPrefs2.edit();



        cv_faq=findViewById(R.id.faq_cardview);
        cv_organiser=findViewById(R.id.org_cardview);
        cv_ticket=findViewById(R.id.tickets_card_view);

        MyImageURL = new String[1];



        Resources r = EventDetail.this.getResources();
        px9 = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                30,
                r.getDisplayMetrics()
        );
        InterestedCount = new long[1];
        // mAdapter.notifyDataSetChanged();

        eventReference = FirebaseDatabase.getInstance().getReference();
        eventReference=eventReference.child("Hotels");
        parentReference = FirebaseDatabase.getInstance().getReference();
        parentReference=parentReference.child("HotelLo");
        adminReference = FirebaseDatabase.getInstance().getReference();
        adminReference=adminReference.child("HotelLoAdmin");



        Intent i = getIntent();
        EventKey = i.getStringExtra("Key");

        if(EventKey==null){
            URLChecker="true";

            FirebaseDynamicLinks.getInstance()
                    .getDynamicLink(getIntent())
                    .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                        @Override
                        public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                            Uri deepLink = null;
                            if (pendingDynamicLinkData != null) {
                                deepLink = pendingDynamicLinkData.getLink();
                                Log.i("Link2", "1 "+deepLink.toString());
                            }
                            EventKey=deepLink.getQueryParameter("Key");
                            getEventURL();
                            getInterestedFlag();
                            fetch();
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Heyya", "getDynamicLink:onFailure", e);
                        }
                    });


        }
        else{
            URLChecker="false";
            position=i.getIntExtra("position",-2);
            getEventURL();
            getInterestedFlag();
            fetch();
        }

        Log.e("EventKey",""+EventKey);



        share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shareEvent(EventKey);
            }
        });



        getUserDetails(new FirebaseCallBackXM() {
            @Override
            public void onCallBack1(String string) {

                UserNameX=string;


            }

            @Override
            public void onCallBack2(String string) {

                ClubLocationX=string;


            }

            @Override
            public void onCallBack3(String string) {


            }
        });






        InterestedTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if(InterestedCount[0]!=0) {
                    Intent intent = new Intent(EventDetail.this, InterestedPeople.class);
                    intent.putExtra("NewsFeedKey", EventKey);
                    // intent.putExtra("InterestedCountX",5);
                    // Log.e("MMS",""+c.getInterestedCount());
                    startActivity(intent);
                }

            }
        });

        InterestedPhotosFL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InterestedCount[0]!=0) {
                    Intent intent = new Intent(EventDetail.this, InterestedPeople.class);
                    intent.putExtra("NewsFeedKey", EventKey);
                    // intent.putExtra("InterestedCountX",5);
                    //  Log.e("MMS",""+c.getInterestedCount());
                    startActivity(intent);

                }

            }
        });

        InterestedCountT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InterestedCount[0]!=0) {
                    Intent intent = new Intent(EventDetail.this, InterestedPeople.class);
                    intent.putExtra("NewsFeedKey", EventKey);
                    // intent.putExtra("InterestedCountX",5);
                    // Log.e("MMS",""+c.getInterestedCount());
                    startActivity(intent);
                }

            }
        });


     //   getTodaysTime();





        bookmark.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Bookmark == null || Bookmark.equals("false")) {
                    Bookmark = "true";
                    bookmark.setBackgroundResource(R.drawable.ic_bookmark_red);
                    bookmarkCount=bookmarkCount+1;
                    eventReference.child(EventKey).child("BookmarkCount").setValue(""+(bookmarkCount));


                    if (Like == null || Like.equals("false")) {

                        interestedB.setVisibility(View.GONE);
                        interestedIV.setVisibility(View.VISIBLE);
                        interestedT.setText("I am interested");



                        eventReference.child(EventKey).child("Interested").child(user.getUid()).child("Status").setValue("true");
                        eventReference.child(EventKey).child("Interested").child(user.getUid()).child("ImageUrl").setValue(MyImageURL[0]);
                        InterestedCount[0] = InterestedCount[0] + 1;
                        InterestedCountT.setText("" + InterestedCount[0]);
                        InterestedCountFinal=String.valueOf(InterestedCount[0]);

                        if (InterestedCount[0] == 1) {
                            eventReference.child(EventKey).child("InterestedPerson1").setValue(MyImageURL[0]);
                            InterestedPhoto1.setVisibility(View.VISIBLE);
                            InterestedPhoto1SS = MyImageURL[0];
                            Picasso.get()
                                    .load(Uri.parse(InterestedPhoto1SS))

                                    .into(InterestedPhoto1);
                        }
                        if (InterestedCount[0] == 2) {
                            eventReference.child(EventKey).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild("InterestedPerson1")){
                                        dataSnapshot.getRef().child("InterestedPerson2").setValue(MyImageURL[0]);
                                    }
                                    else{
                                        dataSnapshot.getRef().child("InterestedPerson1").setValue(MyImageURL[0]);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                          //  eventReference.child(EventKey).child("InterestedPerson2").setValue(MyImageURL[0]);
                            InterestedPhoto1.setVisibility(View.VISIBLE);
                            InterestedPhoto2.setVisibility(View.VISIBLE);
                            InterestedPhoto2SS = MyImageURL[0];
                            Picasso.get()
                                    .load(Uri.parse(InterestedPhoto2SS))

                                    .into(InterestedPhoto2);
                        }

                    }
                    eventReference.child(EventKey).child("Interested").child(user.getUid()).child("Bookmark").setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(EventDetail.this, "Added to bookmarks", Toast.LENGTH_SHORT).show();

                        }
                    });


                } else {
                    Bookmark = "false";
                    bookmark.setBackgroundResource(R.drawable.ic_bookmark_white);
                    bookmarkCount=bookmarkCount-1;
                    eventReference.child(EventKey).child("BookmarkCount").setValue(""+(bookmarkCount));

                    if (Like == null || Like.equals("false")) {

                        interestedB.setVisibility(View.VISIBLE);
                        interestedT.setText("Are you interested?");
                        interestedIV.setVisibility(View.GONE);



                        eventReference.child(EventKey).child("Interested").child(user.getUid()).removeValue();
                        InterestedCount[0] = InterestedCount[0] - 1;
                        InterestedCountT.setText("" + InterestedCount[0]);
                        InterestedCountFinal=String.valueOf(InterestedCount[0]);
                        if (InterestedCount[0] == 1) {
                            eventReference.child(EventKey).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child("InterestedPerson1").getValue(String.class)!=null){
                                        Log.e("Kex1","1");
                                        if(dataSnapshot.child("InterestedPerson1").getValue(String.class).equals(MyImageURL[0])){
                                            Log.e("Kex2","2");
                                            eventReference.child(EventKey).child("InterestedPerson1").removeValue();
                                            InterestedPhoto1.setVisibility(View.GONE);
                                        }
                                    }
                                     if(dataSnapshot.child("InterestedPerson2").getValue(String.class)!=null){
                                        Log.e("Kex3","1");
                                        if(dataSnapshot.child("InterestedPerson2").getValue(String.class).equals(MyImageURL[0])){
                                            Log.e("Kex4","1");
                                            eventReference.child(EventKey).child("InterestedPerson2").removeValue();
                                            InterestedPhoto2.setVisibility(View.GONE);
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                        if (InterestedCount[0] == 0) {
                            eventReference.child(EventKey).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child("InterestedPerson1").getValue(String.class)!=null){
                                        Log.e("Kex1","1");
                                        if(dataSnapshot.child("InterestedPerson1").getValue(String.class).equals(MyImageURL[0])){
                                            Log.e("Kex2","2");
                                            eventReference.child(EventKey).child("InterestedPerson1").removeValue();
                                            InterestedPhoto1.setVisibility(View.GONE);
                                        }
                                    }
                                     if(dataSnapshot.child("InterestedPerson2").getValue(String.class)!=null){
                                        Log.e("Kex3","1");
                                        if(dataSnapshot.child("InterestedPerson2").getValue(String.class).equals(MyImageURL[0])){
                                            Log.e("Kex4","1");
                                            eventReference.child(EventKey).child("InterestedPerson2").removeValue();
                                            InterestedPhoto2.setVisibility(View.GONE);
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                    } else {
                        eventReference.child(EventKey).child("Interested").child(user.getUid()).child("Bookmark").setValue("false");
                    }

                }


            }
        });


        like.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Like == null || Like.equals("false")) {
                    Like = "true";
                    like.setBackgroundResource(R.drawable.ic_heart_red);
                    likeCount=likeCount+1;
                    eventReference.child(EventKey).child("LikeCount").setValue(""+(likeCount));


                    if (Bookmark == null || Bookmark.equals("false")) {
                        interestedB.setVisibility(View.GONE);
                        interestedIV.setVisibility(View.VISIBLE);
                        interestedT.setText("I am interested");



                        eventReference.child(EventKey).child("Interested").child(user.getUid()).child("Status").setValue("true");
                        eventReference.child(EventKey).child("Interested").child(user.getUid()).child("ImageUrl").setValue(MyImageURL[0]);
                        InterestedCount[0] = InterestedCount[0] + 1;
                        InterestedCountT.setText("" + InterestedCount[0]);
                        InterestedCountFinal=String.valueOf(InterestedCount[0]);
                        if (InterestedCount[0] == 1) {
                            eventReference.child(EventKey).child("InterestedPerson1").setValue(MyImageURL[0]);
                            InterestedPhoto1.setVisibility(View.VISIBLE);
                            InterestedPhoto1SS = MyImageURL[0];
                            Picasso.get()
                                    .load(Uri.parse(InterestedPhoto1SS))

                                    .into(InterestedPhoto1);
                        }
                        if (InterestedCount[0] == 2) {
                            eventReference.child(EventKey).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild("InterestedPerson1")){
                                        dataSnapshot.getRef().child("InterestedPerson2").setValue(MyImageURL[0]);
                                    }
                                    else{
                                        dataSnapshot.getRef().child("InterestedPerson1").setValue(MyImageURL[0]);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            InterestedPhoto1.setVisibility(View.VISIBLE);
                            InterestedPhoto2.setVisibility(View.VISIBLE);
                            InterestedPhoto2SS = MyImageURL[0];
                            Picasso.get()
                                    .load(Uri.parse(InterestedPhoto2SS))

                                    .into(InterestedPhoto2);
                        }
                          /*  c.setInterested("true");
                            if(c.getInterestedPhoto().size()<2){
                                c.setInterestedPhoto(c.getInterestedPhoto().add(MyImageURL));

                            }*/

                    }
                    eventReference.child(EventKey).child("Interested").child(user.getUid()).child("Like").setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //  Toast.makeText(getActivity(),"Added to bookmarks",Toast.LENGTH_SHORT).show();

                        }
                    });


                } else {
                    Like = "false";
                    like.setBackgroundResource(R.drawable.ic_heart_white);
                    likeCount=likeCount-1;
                    eventReference.child(EventKey).child("LikeCount").setValue(""+(likeCount));

                    if (Bookmark == null || Bookmark.equals("false")) {

                        interestedB.setVisibility(View.VISIBLE);
                        interestedT.setText("Are you interested?");
                        interestedIV.setVisibility(View.GONE);



                        // events_reference.child(c.getKey()).child("Interested").child(user.getUid()).child("Status").setValue("false");
                        eventReference.child(EventKey).child("Interested").child(user.getUid()).removeValue();
                        InterestedCount[0] = InterestedCount[0] - 1;
                        InterestedCountT.setText("" + InterestedCount[0]);
                        InterestedCountFinal=String.valueOf(InterestedCount[0]);
                        if (InterestedCount[0] == 1) {
                            eventReference.child(EventKey).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child("InterestedPerson1").getValue(String.class)!=null){
                                        Log.e("Kex1","1");
                                        if(dataSnapshot.child("InterestedPerson1").getValue(String.class).equals(MyImageURL[0])){
                                            Log.e("Kex2","2");
                                            eventReference.child(EventKey).child("InterestedPerson1").removeValue();
                                            InterestedPhoto1.setVisibility(View.GONE);
                                        }
                                    }
                                    if(dataSnapshot.child("InterestedPerson2").getValue(String.class)!=null){
                                        Log.e("Kex3","1");
                                        if(dataSnapshot.child("InterestedPerson2").getValue(String.class).equals(MyImageURL[0])){
                                            Log.e("Kex4","1");
                                            eventReference.child(EventKey).child("InterestedPerson2").removeValue();
                                            InterestedPhoto2.setVisibility(View.GONE);
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                           // eventReference.child(EventKey).child("InterestedPerson2").removeValue();

                        }
                        if (InterestedCount[0] == 0) {
                            eventReference.child(EventKey).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child("InterestedPerson1").getValue(String.class)!=null){
                                        Log.e("Kex1","1");
                                        if(dataSnapshot.child("InterestedPerson1").getValue(String.class).equals(MyImageURL[0])){
                                            Log.e("Kex2","2");
                                            eventReference.child(EventKey).child("InterestedPerson1").removeValue();
                                            InterestedPhoto1.setVisibility(View.GONE);
                                        }
                                    }
                                    if(dataSnapshot.child("InterestedPerson2").getValue(String.class)!=null){
                                        Log.e("Kex3","1");
                                        if(dataSnapshot.child("InterestedPerson2").getValue(String.class).equals(MyImageURL[0])){
                                            Log.e("Kex4","1");
                                            eventReference.child(EventKey).child("InterestedPerson2").removeValue();
                                            InterestedPhoto2.setVisibility(View.GONE);
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                           // eventReference.child(EventKey).child("InterestedPerson1").removeValue();
                           // InterestedPhoto1.setVisibility(View.GONE);
                        }

                    } else {

                        eventReference.child(EventKey).child("Interested").child(user.getUid()).child("Like").setValue("false");

                    }


                }

                // mAdapter.notifyDataSetChanged();

            }
        });


        /*if(c.getLike()==null||c.getLike().equals("false")){

            holder.LikeButton.setBackgroundResource(R.drawable.ic_heart2);

        }
        else{

            holder.LikeButton.setBackgroundResource(R.drawable.ic_like2);

        }*/








        FAQButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(EventDetail.this,EventDetailsFAQsActivity.class);
                i.putExtra("EventKey",EventKey);
                i.putExtra("EventName",EventNameX);

                startActivity(i);
            }
        });

        cv_faq.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(EventDetail.this,EventDetailsFAQsActivity.class);
                i.putExtra("EventKey",EventKey);
                i.putExtra("EventName",EventNameX);

                startActivity(i);

            }
        });


        OrganiserButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(EventDetail.this,EventDetailsOrganisersActivity.class);
                i.putExtra("EventKey",EventKey);
                i.putExtra("EventName",EventNameX);

                startActivity(i);
            }
        });

        cv_organiser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(EventDetail.this,EventDetailsOrganisersActivity.class);
                i.putExtra("EventKey",EventKey);
                i.putExtra("EventName",EventNameX);

                startActivity(i);

            }
        });

        TicketsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(EventDetail.this,EventDetailsTicketsActivity.class);
                i.putExtra("EventKey",EventKey);
                i.putExtra("EventName",EventNameX);
                i.putExtra("EventBannerURL",EventBannerURLX);
                i.putExtra("thiseventurl",thiseventurl);
                i.putExtra("UserName",UserNameX);
                i.putExtra("ClubLocation",ClubLocationX);

                i.putExtra("MyImageurl",MyImageURL[0]);
                startActivity(i);
            }
        });

        cv_ticket.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(EventDetail.this,EventDetailsTicketsActivity.class);
                i.putExtra("EventKey",EventKey);
                i.putExtra("EventName",EventNameX);
                i.putExtra("EventBannerURL",EventBannerURLX);
                i.putExtra("thiseventurl",thiseventurl);
                i.putExtra("UserName",UserNameX);
                i.putExtra("ClubLocation",ClubLocationX);

                i.putExtra("MyImageurl",MyImageURL[0]);
                startActivity(i);

            }
        });


        event_banner_container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EventDetail.this,EventDetailsPostersActivity.class);
                i.putExtra("EventKey",EventKey);
                i.putExtra("EventName",EventNameX);
                startActivity(i);
            }
        });
        EventBannerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EventDetail.this,EventDetailsPostersActivity.class);
                i.putExtra("EventKey",EventKey);
                i.putExtra("EventName",EventNameX);
                startActivity(i);
            }
        });


        interestedB =findViewById(R.id.interested);
        interestedIV=findViewById(R.id.interestedIV);
        interestedT=findViewById(R.id.interestedT);
    //    getInterestedFlag();

        interestedB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //interestedB.setBackgroundColor(Color.parseColor("#E3E3E3"));

                interestedB.setVisibility(View.GONE);
                interestedIV.setVisibility(View.VISIBLE);
                interestedT.setText("I am interested");



                if (Bookmark == null || Bookmark.equals("false")) {

                    Bookmark = "true";
                    bookmark.setBackgroundResource(R.drawable.ic_bookmark_red);
                    bookmarkCount=bookmarkCount+1;
                    eventReference.child(EventKey).child("BookmarkCount").setValue(""+bookmarkCount);

                    if (Like == null || Like.equals("false")) {

                        interestedB.setVisibility(View.GONE);
                        interestedIV.setVisibility(View.VISIBLE);
                        interestedT.setText("I am interested");



                        eventReference.child(EventKey).child("Interested").child(user.getUid()).child("Status").setValue("true");
                        eventReference.child(EventKey).child("Interested").child(user.getUid()).child("ImageUrl").setValue(MyImageURL[0]);
                        InterestedCount[0] = InterestedCount[0] + 1;
                        InterestedCountT.setText("" + InterestedCount[0]);
                        InterestedCountFinal=String.valueOf(InterestedCount[0]);

                        if (InterestedCount[0] == 1) {
                            eventReference.child(EventKey).child("InterestedPerson1").setValue(MyImageURL[0]);
                            InterestedPhoto1.setVisibility(View.VISIBLE);
                            InterestedPhoto1SS = MyImageURL[0];
                            Picasso.get()
                                    .load(Uri.parse(InterestedPhoto1SS))

                                    .into(InterestedPhoto1);
                        }
                        if (InterestedCount[0] == 2) {
                            eventReference.child(EventKey).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild("InterestedPerson1")){
                                        dataSnapshot.getRef().child("InterestedPerson2").setValue(MyImageURL[0]);
                                    }
                                    else{
                                        dataSnapshot.getRef().child("InterestedPerson1").setValue(MyImageURL[0]);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            InterestedPhoto1.setVisibility(View.VISIBLE);
                            InterestedPhoto2.setVisibility(View.VISIBLE);
                            InterestedPhoto2SS = MyImageURL[0];
                            Picasso.get()
                                    .load(Uri.parse(InterestedPhoto2SS))

                                    .into(InterestedPhoto2);
                        }

                    }
                    eventReference.child(EventKey).child("Interested").child(user.getUid()).child("Bookmark").setValue("true");

                }

            }
        });


        /*// ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();*/
    }

    public void getInterestedFlag(){

        eventReference.child(EventKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("Interested")){

                    eventReference.child(EventKey).child("Interested").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //String bookmarkS=dataSnapshot.child(user.getUid()).child("Bookmark").getValue(String.class);
                            //String likeS=dataSnapshot.child(user.getUid()).child("Like").getValue(String.class);
                            String statusS=dataSnapshot.child(user.getUid()).child("Status").getValue(String.class);

                            if(Objects.equals(statusS, "true")){
                                interestedB.setVisibility(View.GONE);
                                interestedT.setText("I am interested");
                                interestedIV.setVisibility(View.VISIBLE);

                            }else{
                                interestedB.setVisibility(View.VISIBLE);
                                interestedT.setText("Are you interested?");
                                interestedIV.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }









    public void getInterestedDetails(final FirebaseCallbackXXXXX firebaseCallbackXXXX){

        eventReference.child(EventKey).child("Interested").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String bookmarkS=   dataSnapshot.child(user.getUid()).child("Bookmark").getValue(String.class);
                String likeS=dataSnapshot.child(user.getUid()).child("Like").getValue(String.class);
                long interested_countS=dataSnapshot.getChildrenCount();

                Log.e("Like2",""+Like);
                Log.e("Bookmark2",""+Bookmark);

                firebaseCallbackXXXX.onCallBack1(bookmarkS);
                firebaseCallbackXXXX.onCallBack2(likeS);
                firebaseCallbackXXXX.onCallBack3(interested_countS);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getProfileImage(final FirebaseCallbackX firebaseCallbackX){

        final String[] MyImageURLS = new String[1];

        parentReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user.getUid())){
                    MyImageURLS[0] =dataSnapshot.child(user.getUid()).child("ImageUrl").getValue(String.class);
                    Log.e("url2","hii"+MyImageURLS[0]);
                    firebaseCallbackX.onCallBack(MyImageURLS[0]);
                }
                else{

                    Log.e("url2","hii"+MyImageURLS[0]);

                    adminReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(user.getUid())){
                                MyImageURLS[0] =dataSnapshot.child(user.getUid()).child("ProfileImageUrl").getValue(String.class);
                                firebaseCallbackX.onCallBack(MyImageURLS[0]);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








    }


    @Override
    public void onBackPressed() {

        /*if(URLChecker.equals("true")){}*/
        prefsEditor2.putString("Hii","Hii");
        prefsEditor2.putInt("EventPos",position);
        prefsEditor2.putString("Like",Like);
        prefsEditor2.putString("Bookmark",Bookmark);
        prefsEditor2.putString("InterestedCountInit",InterestedCountInit);
        prefsEditor2.putString("InterestedCountFinal",InterestedCountFinal);
        prefsEditor2.putString("MyImageURL",MyImageURL[0]);
        prefsEditor2.putInt("LikeCount",likeCount);
        prefsEditor2.putInt("BookmarkCount",bookmarkCount);

        prefsEditor2.commit();
        super.onBackPressed();
    }


    public void fetch(){

        getEventImageURL(new FirebaseCallbackXXXX() {
            @Override
            public void onCallBack1(String string) {
                EventBannerURLX=string;

                Log.e("WWE",""+EventBannerURLX);


            }

            @Override
            public void onCallBack2(String string) {
                EventNameX=string;

            }


        });

        getProfileImage(new FirebaseCallbackX() {
            @Override
            public void onCallBack(String string) {

                MyImageURL[0] = string;
                Log.e("url", "" + MyImageURL[0]);

            }


        });

        getInterestedDetails(new FirebaseCallbackXXXXX() {
            @Override
            public void onCallBack1(String string) {

                Bookmark = string;


            }

            @Override
            public void onCallBack2(String string) {

                Like = string;

            }

            @Override
            public void onCallBack3(long string) {

                InterestedCount[0] = string;
                InterestedCountInit=String.valueOf(string);

            }
        });

        eventReference.child(EventKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                EventBannerURL = dataSnapshot.child("EventThumbnailURL").getValue(String.class);
                String EventNameS = dataSnapshot.child("EventName").getValue(String.class);
                final String EventDetailS = dataSnapshot.child("EventDescription").getValue(String.class);



                String InterestedPhoto1S = dataSnapshot.child("InterestedPerson1").getValue(String.class);
                String InterestedPhoto2S = dataSnapshot.child("InterestedPerson2").getValue(String.class);





                if(dataSnapshot.hasChild("LikeCount")){
                    likeCount= Integer.parseInt(dataSnapshot.child("LikeCount").getValue(String.class));
                }
                if(dataSnapshot.hasChild("BookmarkCount")){
                    bookmarkCount=Integer.parseInt(dataSnapshot.child("BookmarkCount").getValue(String.class));
                }


                long InterestedCount = dataSnapshot.child("Interested").getChildrenCount();
                int StartingPrice;
                int EndingPrice;
                String PriceSegment=null;
                if(dataSnapshot.hasChild("StartingPrice")){
                    StartingPrice=dataSnapshot.child("StartingPrice").getValue(Integer.class);
                }
                else{
                    StartingPrice=0;
                }
                if(dataSnapshot.hasChild("EndingPrice")){
                    EndingPrice=dataSnapshot.child("EndingPrice").getValue(Integer.class);
                }
                else{
                    EndingPrice=0;
                }

                if(StartingPrice==0&&EndingPrice==0){

                    PriceSegment="Free";

                }
                else if(StartingPrice!=0&&StartingPrice!=EndingPrice){
                    PriceSegment="₹ "+String.valueOf(StartingPrice)+" - "+String.valueOf(EndingPrice);
                }
                else if(StartingPrice==0&&EndingPrice!=0){
                    PriceSegment="Upto ₹ "+String.valueOf(EndingPrice);
                }
                else if(StartingPrice==EndingPrice&&StartingPrice!=0){
                    PriceSegment="₹ "+String.valueOf(StartingPrice);
                }

                PriceSegmentTV.setText(PriceSegment);





                Log.e("Like", "" + Like);
                Log.e("Bookmark", "" + Bookmark);

                if (Like == null || Like.equals("false")) {
                    like.setBackgroundResource(R.drawable.ic_heart_white);
                } else {
                    like.setBackgroundResource(R.drawable.ic_heart_red);
                }
                if (Bookmark == null || Bookmark.equals("false")) {
                    bookmark.setBackgroundResource(R.drawable.ic_bookmark_white);
                } else {
                    bookmark.setBackgroundResource(R.drawable.ic_bookmark_red);
                }
                String LocationS = dataSnapshot.child("EventAddress").getValue(String.class);
                Location.setText(LocationS);





                EventDetail.setText(EventDetailS);
                InterestedCountT.setText("" + InterestedCount);
                EventName.setText(EventNameS);
                /*final Transformation transformation = new Transformation() {


                    @Override
                    public Bitmap transform(Bitmap source) {
                        int targetWidth;
                        int targetHeight;


                        Log.e("heee", "hee");


                        //   Log.e("Width1","h "+targetWidth);


                        double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                        if (aspectRatio >= 1.0) {

                            Log.e("HEE", "HEE" + aspectRatio);
                            Resources r = EventDetail.this.getResources();
                            final int px = (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP,
                                    60,
                                    r.getDisplayMetrics()
                            );

                            final int px2 = (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP,
                                    15,
                                    r.getDisplayMetrics()
                            );


                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // Stuff that updates the UI
                                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) EventBannerView.getLayoutParams();
                                    params.setMargins(px, px2, px, px2);
                                    EventBannerView.setLayoutParams(params);

                                }
                            });


                        }


                        targetWidth = EventBannerView.getWidth();


                        targetHeight = (int) (targetWidth * aspectRatio);
                        Log.e("Height1", "h " + targetHeight);


                        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                        if (result != source) {

                            source.recycle();
                        }
                        return result;


                    }

                    @Override
                    public String key() {
                        return "transformation" + " desiredHeight";
                    }
                };*/


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI
                        Picasso.get()
                                .load(Uri.parse(EventBannerURL))
                                //.transform(transformation)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .into(EventBannerView, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        progress_bar.setVisibility(View.GONE);
                                        parent_view.setVisibility(View.VISIBLE);




                                    }

                                    @Override
                                    public void onError(Exception e) {

                                    }
                                });

                    }
                });





              /*  if (InterestedPhoto1S != null) {
                    InterestedPhoto1.setVisibility(View.VISIBLE);
                    Picasso.get()
                            .load(Uri.parse(InterestedPhoto1S))

                            .into(InterestedPhoto1);
                } else {
                    InterestedPhoto1.setVisibility(View.GONE);
                    InterestedPhoto2.setVisibility(View.GONE);
                }
                if (InterestedPhoto2S != null) {
                    InterestedPhoto1.setVisibility(View.VISIBLE);
                    InterestedPhoto2.setVisibility(View.VISIBLE);
                    Picasso.get()
                            .load(Uri.parse(InterestedPhoto2S))

                            .into(InterestedPhoto2);
                } else {
                    InterestedPhoto2.setVisibility(View.GONE);
                }*/

                if(InterestedPhoto1S==null&&InterestedPhoto2S!=null){
                    InterestedPhoto2.setVisibility(View.GONE);
                    InterestedPhoto1.setVisibility(View.VISIBLE);
                    Log.e("jhum","jhum");
                    Picasso.get()
                            .load(Uri.parse(InterestedPhoto2S))

                            .into(InterestedPhoto1);

                }
                if(InterestedPhoto2S==null&&InterestedPhoto1S!=null){
                    InterestedPhoto2.setVisibility(View.GONE);
                    InterestedPhoto1.setVisibility(View.VISIBLE);
                    Log.e("jhum","jhum");
                    Picasso.get()
                            .load(Uri.parse(InterestedPhoto1S))

                            .into(InterestedPhoto1);

                }
                if(InterestedPhoto1S==null&&InterestedPhoto2S==null){
                    InterestedPhoto2.setVisibility(View.GONE);
                    InterestedPhoto1.setVisibility(View.GONE);
                }
                if(InterestedPhoto1S!=null&&InterestedPhoto2S!=null){
                    InterestedPhoto2.setVisibility(View.VISIBLE);
                    InterestedPhoto1.setVisibility(View.VISIBLE);
                    Picasso.get()
                            .load(Uri.parse(InterestedPhoto1S))

                            .into(InterestedPhoto1);
                    Picasso.get()
                            .load(Uri.parse(InterestedPhoto2S))

                            .into(InterestedPhoto2);
                  //  Log.e("jhum1","j"+InterestedPhoto1.getVisibility());
                  //  Log.e("jhum2","j"+InterestedPhoto2.getVisibility());
                }
              /*  if(InterestedPhoto1S!=null){
                    InterestedPhoto1.setVisibility(View.VISIBLE);
                    Picasso.get()
                            .load(Uri.parse(InterestedPhoto1S))

                            .into(InterestedPhoto1);
                }
                if(InterestedPhoto2S!=null){
                    InterestedPhoto2.setVisibility(View.VISIBLE);
                    Picasso.get()
                            .load(Uri.parse(InterestedPhoto2S))

                            .into(InterestedPhoto2);
                }*/



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }



    private String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }


    public void getEventURL(){


        String uri="http://hungryhunter96.github.io/share_event?Key="+EventKey;


        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(uri))
                .setDomainUriPrefix("https://hotello.page.link")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())

                .buildShortDynamicLink()
                .addOnCompleteListener(EventDetail.this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {


                            Uri shortLink = task.getResult().getShortLink();
                            thiseventurl=shortLink.toString();


                        } else {

                            Toast.makeText(EventDetail.this,"Event link cannot be created.",Toast.LENGTH_SHORT).show();

                        }
                    }
                });



    }



    public void getEventImageURL(final FirebaseCallbackXXXX firebaseCallbackX){

        eventReference.child(EventKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String EventBannerURL=dataSnapshot.child("EventThumbnailURL").getValue(String.class);
                String EventName=dataSnapshot.child("EventName").getValue(String.class);
                firebaseCallbackX.onCallBack1(EventBannerURL);
                firebaseCallbackX.onCallBack2(EventName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    public void getUserDetails(final FirebaseCallBackXM firebaseCallBackXM){

        parentReference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username=  dataSnapshot.child("UserName").getValue(String.class);
                String clublocation=dataSnapshot.child("ClubLocation").getValue(String.class);
                String rollnum=dataSnapshot.child("RollNum").getValue(String.class);

                firebaseCallBackXM.onCallBack1(username);
                firebaseCallBackXM.onCallBack2(clublocation);
                firebaseCallBackXM.onCallBack3(rollnum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void shareEvent(String EventKey){


        final Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);


        String uri="http://hungryhunter96.github.io/share_event?Key="+EventKey;


        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(uri))
                .setDomainUriPrefix("https://hotello.page.link")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())

                .buildShortDynamicLink()
                .addOnCompleteListener(EventDetail.this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            //  progress_edit_delete.setVisibility(View.GONE);

                            // Toast.makeText(EventDetail.this,"Link Copied",Toast.LENGTH_SHORT).show();
                            String thiseventurl;
                            Uri shortLink = task.getResult().getShortLink();
                            thiseventurl=shortLink.toString();
                            sendIntent.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
                            sendIntent.setType("text/plain");
                            startActivity(sendIntent);
                                        /*Uri flowchartLink = task.getResult().getPreviewLink();

                                        ClipboardManager clipboard = (ClipboardManager) EventDetail.this.getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clip = ClipData.newPlainText(null, shortLink.toString());
                                        if (clipboard == null) return;
                                        clipboard.setPrimaryClip(clip);*/

                        } else {

                            // Toast.makeText(EventDetail.this,"Link Copy Failure",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

















    }

    private void getTodaysTime(){

        Calendar calendar = Calendar.getInstance();
        String am_pm = null;
        String monthS=null;


        int hourS=calendar.get(Calendar.HOUR_OF_DAY);
        String yearS = Integer.toString(calendar.get(Calendar.YEAR)) ;
        int monthofYear = calendar.get(Calendar.MONTH);
        String dayS = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        int minuteS=calendar.get(Calendar.MINUTE);
        //    Toast.makeText(getActivity(),hourS+minutesS,Toast.LENGTH_SHORT).show();

        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY, hourS);
        datetime.set(Calendar.MINUTE, minuteS);

        String minute;
        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm= "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";

        if(datetime.get(Calendar.MINUTE)<10){

            minute= "0"+datetime.get(Calendar.MINUTE);
        }
        else minute=""+datetime.get(Calendar.MINUTE);

        String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";
        todays_time=strHrsToShow + ":" + minute + " " + am_pm;



        switch(monthofYear+1) {
            case 1:
                monthS = "Jan";
                break;
            case 2:
                monthS = "Feb";
                break;
            case 3:
                monthS= "March";
                break;
            case 4:
                monthS= "April";
                break;
            case 5:
                monthS = "May";
                break;
            case 6:
                monthS = "June";
                break;
            case 7:
                monthS = "July";
                break;
            case 8:
                monthS = "August";
                break;
            case 9:
                monthS = "Sept";
                break;
            case 10:
                monthS = "Oct";
                break;
            case 11:
                monthS = "Nov";
                break;
            case 12:
                monthS = "Dec";
                break;
        }

        todays_date=""+dayS+" "+monthS+", "+yearS;


        Log.e("todays_date",todays_date);
        Log.e("todays_time",todays_time);





    }







}
