package com.sys.cub360.catholicchurch.Read;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sys.cub360.catholicchurch.R;
import com.sys.cub360.catholicchurch.Readadaptes.provinceadapter;
import com.sys.cub360.catholicchurch.Readmodels.diocess;
import com.sys.cub360.catholicchurch.Readmodels.provinces;

import java.util.ArrayList;
import java.util.List;

public class Readdioces extends AppCompatActivity {
    private DatabaseReference mref;
    private List<provinces> mlist;
    private provinceadapter madapter;
    private RecyclerView mrecyclervies;
    private provinces mstationmodel;
    private String provinced,m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readdioces);

        provinced=getIntent().getStringExtra("province");
        m=getIntent().getStringExtra("date");

        mlist=new ArrayList<>();
        mrecyclervies=(RecyclerView)findViewById(R.id.recystation);
        mrecyclervies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //madapter=new provinceadapter(getApplicationContext(),mlist, name);


        mlist.clear();

        mref= FirebaseDatabase.getInstance().getReference().child("Diocesblog");
        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String province=dataSnapshot.child("Province").getValue().toString();
                String date=dataSnapshot.child("date").getValue().toString();
                String totalno=dataSnapshot.child("Totalno").getValue().toString();
                String totalc=dataSnapshot.child("totaalc").getValue().toString();
                String dio=dataSnapshot.child("Dioces").getValue().toString();

                Toast.makeText(getApplicationContext(),"no match",Toast.LENGTH_SHORT).show();

                mstationmodel=new provinces(province,dio,date,totalno,totalc);
                mlist.add(mstationmodel);
                madapter=new provinceadapter(getApplicationContext(),mlist, m,provinced);
                mrecyclervies.setAdapter(madapter);




            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
