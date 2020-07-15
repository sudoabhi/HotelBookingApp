package com.example.androdev.hotello.EventDetails;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.androdev.hotello.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EventDetailsTicketsActivity extends AppCompatActivity {



    DatabaseReference parentReference;
    DatabaseReference eventReference;
    DatabaseReference adminReference;

    FirebaseUser user;
     String todays_date;
     String todays_time;
    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private LinearLayoutManager mLayoutManager;


    List<EventDetailsTicketsData> posts;


    String EventKey;
    String EventBannerURLX;
    android.support.v7.widget.Toolbar toolbar;

    String EventNameS;
    ProgressBar progressBar;

    ProgressBar progressBar2;
    NestedScrollView parent_view;
    LinearLayout LL_parent;


    String thiseventurl;

    String UserNameX;
    String ClubLocationX;
    String RollNumX;

    String[] MyImageURL = new String[1];;

    String TicketingFlag;
    FrameLayout frameLay;
    String ClubUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details_tickets);

        frameLay=findViewById(R.id.frameLay);
        parent_view=findViewById(R.id.parent_view);
        progressBar2=findViewById(R.id.progress_bar3);
        LL_parent=(LinearLayout) findViewById(R.id.LL_parent);

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
        adminReference = FirebaseDatabase.getInstance().getReference();
        adminReference=adminReference.child("HotelLoAdmin");


        Intent i = getIntent();
        EventKey = i.getStringExtra("EventKey");
        EventNameS=i.getStringExtra("EventName");
        toolbar.setTitle(EventNameS);
        EventBannerURLX=i.getStringExtra("EventBannerURL");
        thiseventurl=i.getStringExtra("thiseventurl");
        MyImageURL[0]=i.getStringExtra("MyImageurl");
        UserNameX=i.getStringExtra("UserName");
        ClubLocationX=i.getStringExtra("ClubLocation");
        RollNumX=i.getStringExtra("RollNum");

        posts = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_tickets);
        mLayoutManager = new LinearLayoutManager(EventDetailsTicketsActivity.this);
        mAdapter = new TicketAdapter(EventDetailsTicketsActivity.this, posts);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        posts.clear();


        eventReference.child(EventKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TicketingFlag=dataSnapshot.child("Ticketing").getValue(String.class);
                ClubUID=dataSnapshot.child("UploadedBy").getValue(String.class);
                Log.e("Ticketing",""+TicketingFlag);
                if(TicketingFlag.equals("Disabled")){
                    Snackbar.make(frameLay, "Ticket booking for this event has been disabled by the organiser", Snackbar.LENGTH_LONG).show();
                }
                eventReference.child(EventKey).removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        eventReference.child(EventKey).child("Tickets").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String TicketName = dataSnapshot.child("Name").getValue(String.class);
                String TicketCategroy = dataSnapshot.child("Category").getValue(String.class);
                String TicketPrice = dataSnapshot.child("Price").getValue(String.class);
                String TicketDescription = dataSnapshot.child("Description").getValue(String.class);
                String MinTeamMembers=dataSnapshot.child("MinTeamMembers").getValue(String.class);
                String TeamMembers=dataSnapshot.child("TeamMembers").getValue(String.class);
                String TEAM_CHECK=dataSnapshot.child("TEAM_CHECK").getValue(String.class);
                String PRICE_CHECK=dataSnapshot.child("PRICE_CHECK").getValue(String.class);
                String TicketKey=dataSnapshot.child("TicketKey").getValue(String.class);


                posts.add(new EventDetailsTicketsData(TicketName, TicketCategroy, TicketDescription, TicketPrice,
                        MinTeamMembers,TeamMembers,TEAM_CHECK,PRICE_CHECK,TicketKey));

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




    class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder>{

        private List<EventDetailsTicketsData> posts2;
        private Context context;



        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView TicketName;
            TextView TicketCategory;
            TextView TicketDescription;
            TextView TicketPrice;
            TextView TeamContext;
            ImageView TeamImage;
            ImageView IndividualImage;
            TextView TeamPayment;
            TextView IndividualPayment;
            LinearLayout ticket1;
            LinearLayout ticket3;
            CardView ticket2;
            TextView book;



            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                TicketName=(TextView)itemView.findViewById(R.id.ticket_name);
                TicketCategory=(TextView)itemView.findViewById(R.id.ticket_category);
                TicketPrice=(TextView)itemView.findViewById(R.id.ticket_price);
                TicketDescription=(TextView)itemView.findViewById(R.id.ticket_description);
                TeamContext=itemView.findViewById(R.id.team_context);
                TeamImage=itemView.findViewById(R.id.team_image);
                IndividualImage=itemView.findViewById(R.id.individual_image);
                TeamPayment=itemView.findViewById(R.id.team_payment);
                IndividualPayment=itemView.findViewById(R.id.individual_payment);
                ticket2 =itemView.findViewById(R.id.ticket2);
                ticket1 =itemView.findViewById(R.id.ticket1);
                ticket3 =itemView.findViewById(R.id.ticket3);
                book=itemView.findViewById(R.id.booked_tickets);

                Log.e("Ticketing33",""+TicketingFlag);


            }


        }

        public TicketAdapter(Context context,List<EventDetailsTicketsData> posts2) {
            this.context=context;
            this.posts2=posts2;

        }



        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_event_details_tickets,parent,false);
            MyViewHolder vh = new  MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

            if(TicketingFlag.equals("Disabled")) {
                holder.ticket1.setClickable(false);
                holder.ticket2.setClickable(false);
                holder.ticket3.setClickable(false);
                holder.book.setClickable(false);
                holder.ticket1.setEnabled(false);
                holder.ticket2.setEnabled(false);
                holder.ticket3.setEnabled(false);
                holder.book.setEnabled(false);

            }
            if(TicketingFlag.equals("Enabled")) {
                holder.ticket1.setClickable(true);
                holder.ticket2.setClickable(true);
                holder.ticket3.setClickable(true);
                holder.book.setClickable(true);
                holder.ticket1.setEnabled(true);
                holder.ticket2.setEnabled(true);
                holder.ticket3.setEnabled(true);
                holder.book.setEnabled(true);

            }

            final EventDetailsTicketsData c = posts2.get(i);
            holder.TicketName.setText(c.getTicketName());
            holder.TicketCategory.setText(c.getTicketCategory());
            holder.TicketDescription.setText(c.getTicketDescription());
            //  holder.TicketPrice.setText("₹ "+c.getTicketPrice());

            Log.e("sdsd",ClubUID);

            holder.ticket2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });

            holder.ticket3.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });

            holder.book.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {




                }
            });

            holder.ticket1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });



            if(c.getPRICE_CHECK().equals("Book and Pay at hotel")){
                holder.TeamImage.setVisibility(View.VISIBLE);
                holder.IndividualImage.setVisibility(View.GONE);
                if(c.getPRICE_CHECK().equals("Book and Pay at hotel")){
                    holder.TeamPayment.setVisibility(View.VISIBLE);
                    holder.TeamContext.setText("Book and Pay at hotel");
                    holder.TicketPrice.setText("₹"+c.getTicketPrice()+" ");
                    holder.IndividualPayment.setVisibility(View.GONE);
                }
                else{
                    holder.TeamPayment.setVisibility(View.GONE);
                    holder.TeamContext.setText("Pay and Book");
                    holder.TicketPrice.setText("₹"+c.getTicketPrice()+"");
                    holder.IndividualPayment.setVisibility(View.VISIBLE);
                }
            }
            else{
                holder.TeamImage.setVisibility(View.GONE);
                holder.IndividualImage.setVisibility(View.VISIBLE);
                holder.TeamPayment.setVisibility(View.GONE);
                holder.IndividualPayment.setVisibility(View.GONE);
                holder.TicketPrice.setText("₹"+c.getTicketPrice()+"");
            }


        }

        @Override
        public int getItemCount() {
            return posts2.size();
        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
