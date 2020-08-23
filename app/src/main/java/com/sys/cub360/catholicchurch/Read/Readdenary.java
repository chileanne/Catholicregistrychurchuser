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
import com.sys.cub360.catholicchurch.Readadaptes.denaryadapter;
import com.sys.cub360.catholicchurch.Readadaptes.provinceadapter;
import com.sys.cub360.catholicchurch.Readmodels.denarry;
import com.sys.cub360.catholicchurch.Readmodels.provinces;

import java.util.ArrayList;
import java.util.List;

public class Readdenary extends AppCompatActivity {

    private DatabaseReference mref;
    private List<denarry> mlist;
    private denaryadapter madapter;
    private RecyclerView mrecyclervies;
    private denarry mstationmodel;
    private String provinced,m,dios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readdenary);

        dios=getIntent().getStringExtra("dioces");
        m=getIntent().getStringExtra("date");

        mlist=new ArrayList<>();
        mrecyclervies=(RecyclerView)findViewById(R.id.recystations);
        mrecyclervies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //madapter=new provinceadapter(getApplicationContext(),mlist, name);


        mlist.clear();

        mref= FirebaseDatabase.getInstance().getReference().child("Denaryblog");
        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String province=dataSnapshot.child("Province").getValue().toString();
                String date=dataSnapshot.child("date").getValue().toString();
                String totalno=dataSnapshot.child("Totalno").getValue().toString();
                String totalc=dataSnapshot.child("totaalc").getValue().toString();
                String dio=dataSnapshot.child("Dioces").getValue().toString();
                String den=dataSnapshot.child("denary").getValue().toString();

                Toast.makeText(getApplicationContext(),"no match",Toast.LENGTH_SHORT).show();

                mstationmodel=new denarry(den,dio,date,totalno,totalc);
                mlist.add(mstationmodel);
                madapter=new denaryadapter(getApplicationContext(),mlist, m,dios);
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
