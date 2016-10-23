package com.example.bobby.cathy;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bobby on 22/10/2016.
 */

public class ServicesActivity  extends AppCompatActivity {
    private List<services> servicesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private servicesadapter mAdapter;
    String seat, item, passenger, key, marco, coffee,water,orange;
    Integer price,i;
    final String ID_TITLE = "TITLE", ID_SUBTITLE = "SUBTITLE";

    TextView a,b,c, total;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mEmailRef = mRootRef.child("Services");

    List<String> keyArray = new ArrayList<String>();

    HashMap<String,Integer> trialmap = new HashMap<String,Integer>();

    public void clearData() {
        int size = servicesList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                servicesList.remove(0);
            }

            mAdapter.notifyItemRangeRemoved(0, size);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicesactivity);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        a = (TextView) findViewById(R.id.a) ;
        b = (TextView) findViewById(R.id.b) ;
        c = (TextView) findViewById(R.id.c) ;
        total = (TextView)findViewById(R.id.total) ;
        toolbar.setTitle("Services");
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler1_view);

        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                coffee = dataSnapshot.child("Services").child("1").child("NO").getValue(String.class);
                water = dataSnapshot.child("Services").child("2").child("No").getValue(String.class);
                orange = dataSnapshot.child("Services").child("4").child("Item").getValue(String.class);

                    if (orange!=null && orange.equals("Orange")) {
                        total.setText("Waiting to be serve: " + coffee + " " + water + " " + "1 Orange");
                    }
                    else {
                        total.setText("Waiting to be serve: " + coffee + " " + water);
                    }
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mEmailRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                servicesList.clear();

                for (DataSnapshot a : dataSnapshot.getChildren()){
                key = a.getKey().toString();
                seat = dataSnapshot.child(key).child("Seat").getValue(String.class);
                item = dataSnapshot.child(key).child("Item").getValue(String.class);
                passenger = dataSnapshot.child(key).child("Passenger").getValue(String.class);
                    marco = dataSnapshot.child(key).child("Marco").getValue(String.class);
//                    price = dataSnapshot.child(key).child("Price").getValue(Integer.class);

                    services services = new services ( seat , "Item: " + item + "       Marco Polo Club: " + marco ,passenger);
                    servicesList.add(services);
                    mAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(mAdapter);

                    if (!trialmap.containsKey(item)){
                        trialmap.put(item,1);
                    }else{
                        trialmap.put(item,trialmap.get(item)+1);
                    }
//                    ArrayList<HashMap<String,String>> myListData = new ArrayList<HashMap<String,String>>();
//                    String[] titles = new String[]{ "Title1" , "Title2", "Title3" };
//                    String[] subtitles = new String[]{ "SubTitle1" , "SubTitle2", "SubTitle3" };
//
//                    for (int i = 0; i<trialmap.size();i++){
//                        HashMap<String,String> item = new HashMap<String,String>();
//                        item.put(ID_TITLE,titles[i]);
//                        item.put(ID_SUBTITLE,subtitles[i]);
//                        myListData.add(item);
//                    }
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mAdapter = new servicesadapter(servicesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
         recyclerView.setAdapter(mAdapter);
}
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ServicesActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ServicesActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
