package com.sys.cub360.catholicchurch.Read;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sys.cub360.catholicchurch.R;
import com.sys.cub360.catholicchurch.Readadaptes.provinceadapter;
import com.sys.cub360.catholicchurch.Readmodels.provinces;

import java.util.ArrayList;
import java.util.List;

public class Readprovince extends AppCompatActivity {
    private String provinced,m;
    private DatabaseReference mref;
    private TextView name,no,noc;
    private CardView mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readprovince);
        provinced=getIntent().getStringExtra("province");
        m=getIntent().getStringExtra("date");
        mref= FirebaseDatabase.getInstance().getReference().child("Provinceblog");
        name=findViewById(R.id.pname);
        no=findViewById(R.id.pno);
        noc=findViewById(R.id.pnoc);
        mc=findViewById(R.id.beb);





        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String province=dataSnapshot.child("Province").getValue().toString();
                String date=dataSnapshot.child("date").getValue().toString();
                String totalno=dataSnapshot.child("Totalno").getValue().toString();
                String totalc=dataSnapshot.child("totaalc").getValue().toString();

                Toast.makeText(getApplicationContext(),"inisde",Toast.LENGTH_SHORT).show();

                if(province.equals(provinced)){
                    if (date.equals(m)) {
                        name.setText(province);
                        no.setText(totalno);
                        noc.setText(totalc);

                    }else{
                        Toast.makeText(getApplicationContext(),"no match",Toast.LENGTH_SHORT).show();
                    }

                }

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

        mc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Readdioces.class);
                i.putExtra("province", provinced);
                i.putExtra("date", m);
                finish();
                startActivity(i);
            }
        });
    }
}
