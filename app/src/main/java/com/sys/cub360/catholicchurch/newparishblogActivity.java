package com.sys.cub360.catholicchurch;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class newparishblogActivity extends AppCompatActivity {
    private EditText count,mcount;
    private ProgressDialog mp;
    private DatabaseReference mdatabaseref,mparish,mdenary,mdioces,mprovince;
    private DatabaseReference mdenaryadd,mdiocesadd,mprovinceadd;
    private Button mbtn,mlog;
    private  String m,parishpush,denarypush,diocespush,provincepush;
    private String checkdenary="";
    private  String checkdioces="";
    private  String checkprovince="";
    private String checkdenarydate="";
    private  String checkdiocesdate="";
    private  String checkprovincedate="";
    private   String firstno;
    private String secondno;
    private String province,dioces,denary,parish,found,dateii,totalnoi,totalci,idi;
    private String diototalno,diototalc,dioid;
    private String prtotalno,prtotalc,prid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newparishblog);
        province=getIntent().getStringExtra("provincei");
        dioces=getIntent().getStringExtra("diocesi");
        denary=getIntent().getStringExtra("denaryi");
        parish=getIntent().getStringExtra("parishi");
        found=getIntent().getStringExtra("ffound");
        dateii=getIntent().getStringExtra("datei");
        totalnoi=getIntent().getStringExtra("totalno");
        totalci=getIntent().getStringExtra("totalc");
        idi=getIntent().getStringExtra("id");

        //province
        prid=getIntent().getStringExtra("iddd");
        prtotalc=getIntent().getStringExtra("totalcdd");
        prtotalno=getIntent().getStringExtra("totalnodd");

        //dioces
        diototalc=getIntent().getStringExtra("totalcd");
        diototalno=getIntent().getStringExtra("totalnod");
        dioid=getIntent().getStringExtra("idd");



        mp=new ProgressDialog(this);
        count=findViewById(R.id.pxbedit);
        mcount=findViewById(R.id.pxpbedit);
        mbtn=findViewById(R.id.pxbclick);
        mlog=findViewById(R.id.logclick);
       // mbtn.setVisibility(View.GONE);


        if(found.equals("yes")){
            Toast.makeText(getApplicationContext(),"yes",Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),dioid,Toast.LENGTH_SHORT).show();
        }

        if(found.equals("no")){
            Toast.makeText(getApplicationContext(),"no",Toast.LENGTH_SHORT).show();

        }

        if(found.equals("boy")){
            Toast.makeText(getApplicationContext(),"boy",Toast.LENGTH_SHORT).show();
        }

       /* mp.setMessage("Loading");
        mp.setCanceledOnTouchOutside(false);
        mp.setCancelable(false);
        mp.show();*/



        mdenaryadd= FirebaseDatabase.getInstance().getReference().child("Denaryblog");
        mdiocesadd=FirebaseDatabase.getInstance().getReference().child("Diocesblog");
        mprovinceadd=FirebaseDatabase.getInstance().getReference().child("Provinceblog");


        mparish= FirebaseDatabase.getInstance().getReference().child("Parishblog");
        mdenary=FirebaseDatabase.getInstance().getReference().child("Denaryblog");
        mdioces=FirebaseDatabase.getInstance().getReference().child("Diocesblog");
        mprovince=FirebaseDatabase.getInstance().getReference().child("Provinceblog");

        SimpleDateFormat dateformt=new SimpleDateFormat("dd-MM-yyyy");
        m=dateformt.format(new Date());

mlog.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), UsercreateActivity.class));
        finish();
    }
});


        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lets add totalscore to parishblog
                firstno = count.getText().toString().trim();
                secondno = mcount.getText().toString().trim();

                    if(found.equals("yes")&& !TextUtils.isEmpty(firstno) && !TextUtils.isEmpty(secondno)){
                    Toast.makeText(getApplicationContext(),"yes",Toast.LENGTH_SHORT).show();
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

                       /* Map bd = new HashMap();
                        bd.put("/" + parishpush, updatedd);*/
                        mparish.child(parishpush).updateChildren(updatedd).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){
                                    //adding initial score to denary
                                    //covert totalno and totalc variables to int
                                    int denaryno = Integer.parseInt(totalnoi);
                                    int dencaryc = Integer.parseInt(totalci);

                                    //sumup
                                    final int firstsum=denaryno +cfirsno;
                                    final int secondsum=dencaryc + csecondno;

                                    //letsupdate denary values to the current
                                    HashMap usermaps=new HashMap<>();
                                    usermaps.put("Totalno",firstsum);
                                    usermaps.put("totaalc",secondsum);
                                    mdenary.child(idi).updateChildren(usermaps).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            if(task.isSuccessful()){
                                                //adding initial score to dioces
                                                //adding initial score to denary
                                                //covert totalno and totalc variables to int
                                                int diocesno = Integer.parseInt(diototalno);
                                                int diocesc = Integer.parseInt(diototalc);

                                                //sumup
                                                int diocesfirstsum = cfirsno + diocesno;
                                                int diocessecondsum = csecondno + diocesc;

                                                //letsupdate dioces values to the current
                                                HashMap usermaps=new HashMap<>();
                                                usermaps.put("Totalno",diocesfirstsum);
                                                usermaps.put("totaalc",diocessecondsum);
                                                mdioces.child(dioid).updateChildren(usermaps).addOnCompleteListener(new OnCompleteListener() {
                                                    @Override
                                                    public void onComplete(@NonNull Task task) {
                                                        if(task.isSuccessful()){
                                                            //corresponding date was found
                                                            //covert totalno and totalc variables to int
                                                            int prdiocesnos = Integer.parseInt(prtotalno);
                                                            int prdiocescs = Integer.parseInt(prtotalc);

                                                            //sumup
                                                            int provincefirstsum = cfirsno + prdiocesnos;
                                                            int provincesecondsum = csecondno+ prdiocescs;

                                                            //letsupdate dioces values to the current
                                                            HashMap usermaps = new HashMap<>();
                                                            usermaps.put("Totalno", provincefirstsum);
                                                            usermaps.put("totaalc", provincesecondsum);
                                                            mprovince.child(prid).updateChildren(usermaps).addOnCompleteListener(new OnCompleteListener() {
                                                                @Override
                                                                public void onComplete(@NonNull Task task) {
                                                                    if(task.isSuccessful()){
                                                                        Toast.makeText(getApplicationContext(),"corresponding date complete",Toast.LENGTH_SHORT).show();


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
                        });

                }  else if(found.equals("no") && !TextUtils.isEmpty(firstno) && !TextUtils.isEmpty(secondno)){
                    Toast.makeText(getApplicationContext(),"no",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"date empty",Toast.LENGTH_SHORT).show();
                    //if the date wasnt found it means there
                    //there is no record of the particular day for deanry,dioces,province
                    // therefore we have to add them
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

                       /* Map bd = new HashMap();
                        bd.put("/" + parishpush, updatedd);*/
                        mparish.child(parishpush).updateChildren(updatedd).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){


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
                    mdenaryadd.child(denarypush).updateChildren(updatedd).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                //insert new dioces

                                DatabaseReference user_message_push = mdiocesadd.push();
                                diocespush = user_message_push.getKey();


                                Map updatedd = new HashMap();
                                updatedd.put("pushid", diocespush);
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
                        });




                    }else if(found.equals("boy") && !TextUtils.isEmpty(firstno) && !TextUtils.isEmpty(secondno)){
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

                       /* Map bd = new HashMap();
                        bd.put("/" + parishpush, updatedd);*/
                        mparish.child(parishpush).updateChildren(updatedd).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){


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
        });

                    }
            }
        });
    }


}
