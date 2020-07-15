package com.example.androdev.hotello.SearchFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.androdev.hotello.R;
import com.example.androdev.hotello.UserFragments.FragmentSearch;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchOrganizers extends Fragment implements TextWatcher {

    FirebaseDatabase ClubDatabase;
    DatabaseReference ClubReference;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;

    RecyclerView.Adapter mAdapter;
    ArrayList<SearchOrganizersObject> newList;
    ArrayList<SearchOrganizersObject> posts;
    LottieAnimationView animation;

    String ClubUID;
    String ClubName;
    String CollegeName;
    String ClubFollowers;
    String ClubProfileImage;

    String k;

    ArrayList<String> A;
    Integer AdapterCount;
    String l;



    public SearchOrganizers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_organizers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        animation=(LottieAnimationView) view.findViewById(R.id.animation_view);
        AdapterCount=0;


        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setItemViewCacheSize(20);
        mRecyclerView.setDrawingCacheEnabled(true);
        A = new ArrayList<>();
        posts = new ArrayList<>();
        posts.clear();
        newList = new ArrayList<>();
        newList.clear();








        mRecyclerView.setNestedScrollingEnabled(false
        );
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        FragmentSearch.SearchEdittext.addTextChangedListener(this);


        ClubDatabase = FirebaseDatabase.getInstance();
        ClubReference = ClubDatabase.getReference();
        ClubReference=ClubReference.child("HotelLoAdmin");
        mAdapter = new OrganizerAdapter(getActivity(), newList);




        mRecyclerView.setAdapter(mAdapter);


    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        animation.setVisibility(View.VISIBLE);
        AdapterCount = 0;
        k = FragmentSearch.getEdittext();

        posts.clear();
        newList.clear();
        mAdapter.notifyDataSetChanged();

        if (k.length() == 0) {
            animation.setVisibility(View.VISIBLE);

            posts.clear();
            newList.clear();
            mAdapter.notifyDataSetChanged();




        } else {



            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ClubUID= dataSnapshot.child("UserUID").getValue(String.class);
                    ClubName= dataSnapshot.child("ClubName").getValue(String.class);


                    ClubProfileImage = dataSnapshot.child("ProfileImageUrl").getValue(String.class);
                    CollegeName=dataSnapshot.child("ClubLocation").getValue(String.class);

                    ClubFollowers="0";



                    posts.add(new SearchOrganizersObject(ClubUID,ClubProfileImage,ClubName,CollegeName, ClubFollowers));
                    newList = removeDuplicateDistricts(posts, posts.size());

                    Log.e("Change",String.valueOf(A));
                    mAdapter.notifyDataSetChanged();

                    OrganizerAdapter adapter=new OrganizerAdapter(getActivity(),posts);
                    AdapterCount= adapter.getCount();

                    if(AdapterCount==0){
                        animation.setVisibility(View.VISIBLE);
                    }
                    else animation.setVisibility(View.GONE);

                    if (k.length() == 0) {
                        animation.setVisibility(View.VISIBLE);

                        posts.clear();
                        newList.clear();
                        mAdapter.notifyDataSetChanged();
                    }














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
            };



            Query query = ClubReference.orderByChild("ClubName").startAt(k).endAt(k + "\uf8ff");
            query.addChildEventListener(childEventListener);




            posts.clear();
            A.clear();

        }
    }




    @Override
    public void afterTextChanged(Editable editable) {


    }


    private ArrayList<SearchOrganizersObject> removeDuplicateDistricts(ArrayList<SearchOrganizersObject> list, int size) {


        for (SearchOrganizersObject element : list) {

            // If this element is not present in newList
            // then add it
            String s = element.getClubName();

            //  Toast.makeText(getActivity(),"hello"+element.getClubName(),Toast.LENGTH_SHORT).show();
            if (!newList.contains(element)) {

                if (newList.size() > 0) {

                    int sizeS = newList.size();
                    for(int i=0; i<newList.size(); i++) {

                        if (newList.get(i).getClubName().equals(element.getClubName())) {

                         //   Toast.makeText(getActivity(), "hello" + element.getClubName(), Toast.LENGTH_LONG).show();
                            break;




                        }
                        else{
                            if(i==newList.size()-1){
                                newList.add(element);
                            }
                        }


                    }



                } else{


                    newList.add(element);
                  //  Toast.makeText(getActivity(), "hii" + element.getClubName(), Toast.LENGTH_LONG).show();
                }


            }







        }
        return newList;


    }

}







