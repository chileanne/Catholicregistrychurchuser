package com.sys.cub360.catholicchurch;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class parishblogActivity extends AppCompatActivity {
    private EditText count,mcount;
    private ProgressDialog mp;
    private DatabaseReference mdatabaseref,mparish,mdenary,mdioces,mprovince;
    private DatabaseReference mdenaryadd,mdiocesadd,mprovinceadd;
    private Button mbtn;
    private  String m,parishpush,denarypush,diocespush,provincepush;
    private String checkdenary="";
   private  String checkdioces="";
    private  String checkprovince="";
    private String checkdenarydate="";
    private  String checkdiocesdate="";
    private  String checkprovincedate="";
    private   String firstno;
    private String secondno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parishblog);
        mp=new ProgressDialog(this);
        count=findViewById(R.id.pxbedit);
        mcount=findViewById(R.id.pxpbedit);
        mbtn=findViewById(R.id.pxbclick);
        mbtn.setVisibility(View.GONE);

        mp.setMessage("Loading");
        mp.setCanceledOnTouchOutside(false);
        mp.setCancelable(false);
        mp.show();


        mdenaryadd=FirebaseDatabase.getInstance().getReference().child("Denaryblog");
        mdiocesadd=FirebaseDatabase.getInstance().getReference().child("Diocesblog");
        mprovinceadd=FirebaseDatabase.getInstance().getReference().child("Provinceblog");


        mparish= FirebaseDatabase.getInstance().getReference().child("Parishblog");
        mdenary=FirebaseDatabase.getInstance().getReference().child("Denaryblog");
        mdioces=FirebaseDatabase.getInstance().getReference().child("Diocesblog");
        mprovince=FirebaseDatabase.getInstance().getReference().child("Provinceblog");

        SimpleDateFormat dateformt=new SimpleDateFormat("dd-MM-yyyy");
        m=dateformt.format(new Date());

        FirebaseUser currentuser= FirebaseAuth.getInstance().getCurrentUser();
        String uid=currentuser.getUid();
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
        mbtn.setVisibility(View.VISIBLE);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdenary=FirebaseDatabase.getInstance().getReference().child("Denaryblog");
                checkdenary="";
                //lets add totalscore to parishblog
                 firstno = count.getText().toString().trim();
                 secondno = mcount.getText().toString().trim();
                if (!TextUtils.isEmpty(firstno) && !TextUtils.isEmpty(secondno)){

                    mp.setMessage("Loading");
                    mp.setCanceledOnTouchOutside(false);
                    mp.setCancelable(false);
                    mp.show();

                    final int cfirsno=Integer.parseInt(firstno);
                    final int csecondno=Integer.parseInt(secondno);

                    Map updatedd = new HashMap();
                updatedd.put("Province", province);
                updatedd.put("Dioces", dioces);
                updatedd.put("denary", denary);
                updatedd.put("parish", parish);
                updatedd.put("date", m);
                updatedd.put("Totalno", firstno);
                updatedd.put("totaalc", secondno);

                DatabaseReference user_message_push = mparish.push();
                parishpush = user_message_push.getKey();

                Map bd = new HashMap();
                bd.put("/" + parishpush, updatedd);
                mparish.updateChildren(bd).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        //lets check if denaryblog exist
                        if (task.isSuccessful()) {
                            //lets check deneary blog

                                mdenary.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        String provinces = dataSnapshot.child("Province").getValue().toString();
                                        String diocess = dataSnapshot.child("Dioces").getValue().toString();
                                        String denarys = dataSnapshot.child("denary").getValue().toString();
                                        // String parishs = dataSnapshot.child("parish").getValue().toString();
                                        String dates = dataSnapshot.child("date").getValue().toString();
                                        String totalno = dataSnapshot.child("Totalno").getValue().toString();
                                        String totalc = dataSnapshot.child("totaalc").getValue().toString();
                                        String id= dataSnapshot.child("pushid").getValue().toString();

                                        Toast.makeText(getApplicationContext(),"reading",Toast.LENGTH_SHORT).show();

                                        if (denarys.equals(denary) && mdenary!=null) {
                                            Toast.makeText(getApplicationContext(),"it sticks",Toast.LENGTH_SHORT).show();
                                            //denary was found
                                            checkdenary = "found";
                                            if (dates.equals(m)) {
                                                //corresponding date was found
                                                checkdenarydate = "found";
                                                //covert totalno and totalc variables to int
                                                int denaryno = Integer.parseInt(totalno.trim());
                                                int dencaryc = Integer.parseInt(totalc.trim());

                                                //sumup
                                                final int firstsum=denaryno +cfirsno;
                                                final int secondsum=dencaryc + csecondno;

                                                //letsupdate denary values to the current
                                                HashMap usermaps=new HashMap<>();
                                                usermaps.put("Totalno",firstsum);
                                                usermaps.put("totaalc",secondsum);
                                                mdenary.child(id).updateChildren(usermaps).addOnCompleteListener(new OnCompleteListener() {
                                                    @Override
                                                    public void onComplete(@NonNull Task task) {
                                                        if(task.isSuccessful()){
                                                            //lets retrieve all values from the dioces
                                                            mdioces.addChildEventListener(new ChildEventListener() {
                                                                @Override
                                                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                                    String provinces = dataSnapshot.child("Province").getValue().toString();
                                                                    String diocess = dataSnapshot.child("Dioces").getValue().toString();
                                                                    //  String denarys = dataSnapshot.child("denary").getValue().toString();
                                                                    // String parishs = dataSnapshot.child("parish").getValue().toString();
                                                                    String dates = dataSnapshot.child("date").getValue().toString();
                                                                    String totalno = dataSnapshot.child("Totalno").getValue().toString();
                                                                    String totalc = dataSnapshot.child("totaalc").getValue().toString();
                                                                    String id= dataSnapshot.child("pushid").getValue().toString();

                                                                    if (diocess.equals(dioces)) {
                                                                        //denary was found
                                                                        checkdioces = "found";
                                                                        if (dates.equals(m)) {
                                                                            //corresponding date was found
                                                                            //covert totalno and totalc variables to int
                                                                            int diocesno = Integer.parseInt(totalno.trim());
                                                                            int diocesc = Integer.parseInt(totalc.trim());

                                                                            //sumup
                                                                            int diocesfirstsum = firstsum + diocesno;
                                                                            int diocessecondsum = secondsum + diocesc;

                                                                            //letsupdate dioces values to the current
                                                                            HashMap usermaps=new HashMap<>();
                                                                            usermaps.put("Totalno",diocesfirstsum);
                                                                            usermaps.put("totaalc",diocessecondsum);
                                                                            mdioces.child(id).updateChildren(usermaps).addOnCompleteListener(new OnCompleteListener() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task task) {
                                                                                    if(task.isSuccessful()){
                                                                                        //lets update the vales of province
                                                                                        mprovince.addChildEventListener(new ChildEventListener() {
                                                                                            @Override
                                                                                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                                                                String provinces = dataSnapshot.child("Province").getValue().toString();
                                                                                                // String diocess = dataSnapshot.child("Dioces").getValue().toString();
                                                                                                //  String denarys = dataSnapshot.child("denary").getValue().toString();
                                                                                                // String parishs = dataSnapshot.child("parish").getValue().toString();
                                                                                                String dates = dataSnapshot.child("date").getValue().toString();
                                                                                                String totalno = dataSnapshot.child("Totalno").getValue().toString();
                                                                                                String totalc = dataSnapshot.child("totaalc").getValue().toString();
                                                                                                String id= dataSnapshot.child("pushid").getValue().toString();

                                                                                                if (provinces.equals(province)) {
                                                                                                    //denary was found
                                                                                                    checkprovince = "found";
                                                                                                    if (dates.equals(m)) {
                                                                                                        //corresponding date was found
                                                                                                        //covert totalno and totalc variables to int
                                                                                                        int diocesno = Integer.parseInt(totalno.trim());
                                                                                                        int diocesc = Integer.parseInt(totalc.trim());

                                                                                                        //sumup
                                                                                                        int provincefirstsum = firstsum + diocesno;
                                                                                                        int provincesecondsum = secondsum + diocesc;

                                                                                                        //letsupdate dioces values to the current
                                                                                                        HashMap usermaps = new HashMap<>();
                                                                                                        usermaps.put("Totalno", provincefirstsum);
                                                                                                        usermaps.put("totaalc", provincesecondsum);
                                                                                                        mprovince.child(id).updateChildren(usermaps).addOnCompleteListener(new OnCompleteListener() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task task) {
                                                                                                                if(task.isSuccessful()){
                                                                                                                    //complete done
                                                                                                                    mp.dismiss();
                                                                                                                    Toast.makeText(getApplicationContext(),"complete for corresponding date",Toast.LENGTH_SHORT).show();
                                                                                                                }
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
                                                    }
                                                });




                                            }

                                            //if the date wasnt found
                                            if(checkdenarydate.equals("")){
                                                Toast.makeText(getApplicationContext(),"date empty",Toast.LENGTH_SHORT).show();
                                                //if the date wasnt found it means there
                                                //there is no record of the particular day for deanry,dioces,province
                                                // therefore we have to add them
                                                DatabaseReference user_message_push = mdenaryadd.push();
                                                denarypush = user_message_push.getKey();

                                                //insert into denary
                                                Map updatedd = new HashMap();
                                                updatedd.put("Province", province);
                                                updatedd.put("Dioces", dioces);
                                                updatedd.put("denary", denary);
                                                updatedd.put("pushid", denarypush);
                                                updatedd.put("date", m);
                                                updatedd.put("Totalno", firstno);
                                                updatedd.put("totaalc", secondno);


                                                Map bd = new HashMap();
                                                bd.put("/" + denarypush, updatedd);
                                                mdenaryadd.updateChildren(bd).addOnCompleteListener(new OnCompleteListener() {
                                                    @Override
                                                    public void onComplete(@NonNull Task task) {
                                                        if(task.isSuccessful()){
                                                            //insert new dioces

                                                            DatabaseReference user_message_push = mdiocesadd.push();
                                                            diocespush = user_message_push.getKey();


                                                            Map updatedd = new HashMap();
                                                            updatedd.put("pushid", denarypush);
                                                            updatedd.put("Province", province);
                                                            updatedd.put("Dioces", dioces);
                                                            updatedd.put("date", m);
                                                            updatedd.put("Totalno", firstno);
                                                            updatedd.put("totaalc", secondno);



                                                            Map bd = new HashMap();
                                                            bd.put("/" + diocespush, updatedd);
                                                            mdiocesadd.updateChildren(bd).addOnCompleteListener(new OnCompleteListener() {
                                                                @Override
                                                                public void onComplete(@NonNull Task task) {
                                                                    if(task.isSuccessful()){
                                                                        //insert new province
                                                                        DatabaseReference user_message_push = mprovinceadd.push();
                                                                        provincepush = user_message_push.getKey();

                                                                        Map updatedd = new HashMap();
                                                                        updatedd.put("pushid",  provincepush);
                                                                        updatedd.put("Province", province);
                                                                        updatedd.put("date", m);
                                                                        updatedd.put("Totalno", firstno);
                                                                        updatedd.put("totaalc", secondno);




                                                                        Map bd = new HashMap();
                                                                        bd.put("/" + provincepush, updatedd);
                                                                        mprovinceadd.updateChildren(bd).addOnCompleteListener(new OnCompleteListener() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task task) {
                                                                                if(task.isSuccessful()){
                                                                                    //complete done
                                                                                    mp.dismiss();
                                                                                    Toast.makeText(getApplicationContext(),"complete for no corresponding date",Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            });
                                                        }

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

                            if (checkdenary.equals("")) {
                                mdenary=null;
                                //the denary wasnt found
                                //insert new denary

                                Toast.makeText(getApplicationContext(),"inside denary",Toast.LENGTH_SHORT).show();

                                DatabaseReference user_message_push = mdenaryadd.push();
                                denarypush = user_message_push.getKey();


                                Map updatedd = new HashMap();
                                updatedd.put("Province", province);
                                updatedd.put("Dioces", dioces);
                                updatedd.put("denary", denary);
                                updatedd.put("pushid", denarypush);
                                updatedd.put("date", m);
                                updatedd.put("Totalno", firstno);
                                updatedd.put("totaalc", secondno);


                                Map bd = new HashMap();
                                bd.put("/" + denarypush, updatedd);
                                mdenaryadd.updateChildren(bd).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(),"iside dioces",Toast.LENGTH_SHORT).show();

                                            //insert new dioces
                                            DatabaseReference user_message_push = mdiocesadd.push();
                                            diocespush = user_message_push.getKey();

                                            Map updatedd = new HashMap();
                                            updatedd.put("pushid",  diocespush);
                                            updatedd.put("Province", province);
                                            updatedd.put("Dioces", dioces);
                                            updatedd.put("date", m);
                                            updatedd.put("Totalno", firstno);
                                            updatedd.put("totaalc", secondno);




                                            Map bd = new HashMap();
                                            bd.put("/" + diocespush, updatedd);
                                            mdiocesadd.updateChildren(bd).addOnCompleteListener(new OnCompleteListener() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {
                                                    if (task.isSuccessful()) {
                                                        //insert new province
                                                        Toast.makeText(getApplicationContext(),"inside province",Toast.LENGTH_SHORT).show();


                                                        DatabaseReference user_message_push = mprovinceadd.push();
                                                        provincepush = user_message_push.getKey();

                                                        Map updatedd = new HashMap();
                                                        updatedd.put("pushid",  provincepush);
                                                        updatedd.put("Province", province);
                                                        updatedd.put("date", m);
                                                        updatedd.put("Totalno", firstno);
                                                        updatedd.put("totaalc", secondno);



                                                        Map bd = new HashMap();
                                                        bd.put("/" + provincepush, updatedd);
                                                        mprovinceadd.updateChildren(bd).addOnCompleteListener(new OnCompleteListener() {
                                                            @Override
                                                            public void onComplete(@NonNull Task task) {
                                                                if (task.isSuccessful()) {
                                                                    //everything good
                                                                    mp.dismiss();
                                                                    Toast.makeText(getApplicationContext(),"complete for first time record",Toast.LENGTH_SHORT).show();

                                                                }
                                                            }
                                                        });

                                                    }
                                                }
                                            });


                                        }

                                    }
                                });


                            }




                        }
                    }
                });

            }//end of if

            }
        });
    }
}
