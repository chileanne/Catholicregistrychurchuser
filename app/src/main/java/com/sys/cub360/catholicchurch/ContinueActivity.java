package com.sys.cub360.catholicchurch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ContinueActivity extends AppCompatActivity {
    private DatabaseReference mdatabaseref,mparish,mdenary,mdioces,mprovince;
    private ProgressDialog mp;
    private String found="";
    private String m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);
        mp=new ProgressDialog(this);
        mp.setMessage("Loading");
        mp.setCanceledOnTouchOutside(false);
        mp.setCancelable(false);
        mp.show();

        SimpleDateFormat dateformt=new SimpleDateFormat("dd-MM-yyyy");
        m=dateformt.format(new Date());
        Toast.makeText(getApplicationContext(),m,Toast.LENGTH_SHORT).show();

        FirebaseUser currentuser= FirebaseAuth.getInstance().getCurrentUser();
        String uid=currentuser.getUid();

        mdioces=FirebaseDatabase.getInstance().getReference().child("Diocesblog");
        mprovince=FirebaseDatabase.getInstance().getReference().child("Provinceblog");
        mdenary= FirebaseDatabase.getInstance().getReference().child("Denaryblog");

        mdatabaseref= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        mdatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String province=dataSnapshot.child("Province").getValue().toString();
                String dioces=dataSnapshot.child("dioces").getValue().toString();
                String denary=dataSnapshot.child("denary").getValue().toString();
                String parish=dataSnapshot.child("parish").getValue().toString();
                mp.dismiss();
                nation(province,dioces,denary,parish);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }

    private void nation(final String province, final String dioces, final String denary, final String parish) {
        mdenary.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String provinces = dataSnapshot.child("Province").getValue().toString();
                String diocess = dataSnapshot.child("Dioces").getValue().toString();
                String denarys = dataSnapshot.child("denary").getValue().toString();
                // String parishs = dataSnapshot.child("parish").getValue().toString();
                String dates = dataSnapshot.child("date").getValue().toString();
                final String totalno = dataSnapshot.child("Totalno").getValue().toString();
                final String totalc = dataSnapshot.child("totaalc").getValue().toString();
                final String id= dataSnapshot.child("pushid").getValue().toString();

                if(denarys.equals(denary)){
                    if(dates.equals(m)) {
                        found="yes";
                        mdioces.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                String dioprovinces = dataSnapshot.child("Province").getValue().toString();
                                String diodiocess = dataSnapshot.child("Dioces").getValue().toString();
                                //  String denarys = dataSnapshot.child("denary").getValue().toString();
                                // String parishs = dataSnapshot.child("parish").getValue().toString();
                                String dates = dataSnapshot.child("date").getValue().toString();
                                final String diototalno = dataSnapshot.child("Totalno").getValue().toString();
                                final String diototalc = dataSnapshot.child("totaalc").getValue().toString();
                                final String dioid= dataSnapshot.child("pushid").getValue().toString();

                                if(diodiocess.equals(dioces)) {
                                    if (dates.equals(m)) {
                                        mprovince.addChildEventListener(new ChildEventListener() {
                                            @Override
                                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                String prprovinces = dataSnapshot.child("Province").getValue().toString();
                                                // String diocess = dataSnapshot.child("Dioces").getValue().toString();
                                                //  String denarys = dataSnapshot.child("denary").getValue().toString();
                                                // String parishs = dataSnapshot.child("parish").getValue().toString();
                                                String dates = dataSnapshot.child("date").getValue().toString();
                                                String prtotalno = dataSnapshot.child("Totalno").getValue().toString();
                                                String prtotalc = dataSnapshot.child("totaalc").getValue().toString();
                                                String prid= dataSnapshot.child("pushid").getValue().toString();

                                                if(prprovinces.equals(province)) {
                                                    if (dates.equals(m)) {
                                                        Intent i = new Intent(getApplicationContext(), newparishblogActivity.class);
                                                        i.putExtra("provincei", province);
                                                        i.putExtra("diocesi", dioces);
                                                        i.putExtra("denaryi", denary);
                                                        i.putExtra("parishi", parish);
                                                        i.putExtra("datei", dates);
                                                        i.putExtra("ffound", "yes");
                                                        i.putExtra("totalno", totalno);
                                                        i.putExtra("totalc", totalc);
                                                        i.putExtra("id", id);
                                                        //dioces
                                                        i.putExtra("totalnod", diototalno);
                                                        i.putExtra("totalcd", diototalc);
                                                        i.putExtra("idd", dioid);

                                                        //province
                                                        i.putExtra("totalnodd", prtotalno);
                                                        i.putExtra("totalcdd", prtotalc);
                                                        i.putExtra("iddd", prid);


                                                        finish();
                                                        startActivity(i);


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

                    }

                    if(!dates.equals(m)){
                        found="boy";
                        Intent i = new Intent(getApplicationContext(), newparishblogActivity.class);
                        i.putExtra("provincei", province);
                        i.putExtra("diocesi", dioces);
                        i.putExtra("denaryi", denary);
                        i.putExtra("parishi", parish);
                        i.putExtra("datei", dates);
                        i.putExtra("ffound", "boy");
                        i.putExtra("totalno", totalno);
                        i.putExtra("totalc", totalc);
                        i.putExtra("id", id);
                        startActivity(i);
                        finish();

                    }

                }

                if(found.equals("")){
                    Intent i=new Intent(getApplicationContext(),newparishblogActivity.class);
                    i.putExtra("provincei",province);
                    i.putExtra("diocesi",dioces);
                    i.putExtra("denaryi",denary);
                    i.putExtra("parishi",parish);
                    i.putExtra("ffound","no");
                    startActivity(i);
                    finish();

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
    }
}
