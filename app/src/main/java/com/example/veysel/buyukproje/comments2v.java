package com.example.veysel.buyukproje;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import info.androidhive.slidingmenu.CommentClass;
import info.androidhive.slidingmenu.CommentsAdapter;


public class comments2v extends Activity {
    ArrayList<String>blocks=new ArrayList<>();
    String phone1,phone2;
    boolean kontrol=false;
    Button btn_yorum;
    EditText yorum;
    RecyclerView recyclerView;
    ProgressDialog pDialog;

    CommentsAdapter adapter;
    ArrayList<String> phoneNumbers=new ArrayList<String>();
    static ArrayList<CommentClass>commentClasses2=new ArrayList<CommentClass>();
    static ArrayList<CommentClass>commentClasses=new ArrayList<CommentClass>();
    private OnFragmentInteractionListener mListener;

    public comments2v() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_comments2v);
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String titleID = sharedPreferences.getString("titleID", "");

        phone2 = sharedPreferences.getString("MyPhone", "");
        blocks.clear();
        blocks.add("");
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Yorumlar Getiriliyor...");
        pDialog.setCancelable(false);
        pDialog.show();
        yorum = (EditText) findViewById(R.id.etComment);

        //yorum ekleme
        btn_yorum= (Button) findViewById(R.id.btnYorumEkle);
        btn_yorum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(yorum.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Lütfen yorum giriniz.", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    String titleID = sharedPreferences.getString("titleID", "");

                     phone1 = sharedPreferences.getString("MyPhone", "");








                    Firebase fir = new Firebase(MyFireBaseClass2.url + "titles/" + titleID + "/comments/");
                    Firebase fir2 = fir.push();

                    fir2.child("comment").setValue(yorum.getText().toString());
                    // yeni skor alanÄ± aÃ§Ä±p onun altÄ±na like ve dislike alanlarÄ± aÃ§Ä±p bir de ona oy veren telefon numaralarÄ±n tutacaÄŸÄ±m
                    // maksat birden fazla oyveren kiÅŸi engellemek olacask
                    Firebase fir3 = fir2.child("skor");
                    fir3.child("like").setValue(0);
                    fir3.child("dislike").setValue(0);
                    fir2.child("phoneNumber").setValue(phone1);
                    Toast.makeText(getApplicationContext(), "eklendi", Toast.LENGTH_LONG).show();
                    yorum.setText("");

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "NaptÄ±n yaa bozdun gÃ¼zelim uygulamayÄ±", Toast.LENGTH_LONG).show();

                }

            }


        });

        final String phone=sharedPreferences.getString("MyPhone","");
        final Firebase firebase=//new Firebase(MyFireBaseClass.url);
                new Firebase(MyFireBaseClass2.url+"/titles/"+titleID+"/comments/");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        int a=ListSize();




       final Firebase firebase2=new Firebase(MyFireBaseClass2.url+"users/"+phone+"/engelle/comments");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                commentClasses.clear();



                String s = "";
                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                    if(blocks!=null) {

try
{
    kontrol=blocks.contains(ds1.child("phoneNumber").getValue().toString());
}
          catch (Exception e)
          {

          }
                            if (!kontrol) {
                                int like = 0;
                                int dislike = 0;
                                String comment = "";
                                String phonumber = "";


                                try {

                                    comment = (String) ds1.child("comment").getValue();
                                    phonumber = (String) ds1.child("phoneNumber").getValue();
                                    if (ds1.child("skor").child("like").getValue().toString() == null)
                                        like = 0;
                                    else
                                        like = Integer.parseInt(ds1.child("skor").child("like").getValue().toString());

                                    if (ds1.child("skor").child("dislike").getValue().toString() == null)
                                        dislike = 0;
                                    else
                                        dislike = Integer.parseInt(ds1.child("skor").child("dislike").getValue().toString());
                                } catch (Exception e) {

                                }

                                String id = ds1.getKey();
                                commentClasses.add(new CommentClass(comment, like, dislike, id, phonumber));

                        }

                    }
                }
                    //Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                    adapter = new CommentsAdapter(commentClasses);

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);

                //    Toast.makeText(getActivity(), "comment" + commentClasses.get(1).getId(), Toast.LENGTH_LONG).show();
                // true like false dislike ,burada hangisinie basarsa o bir artacak veya artmalÄ±l
                adapter.setOnItemClickListener(new CommentsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, boolean b,boolean c) {

                        if (b && !c) {
                            if(dataSnapshot.child(commentClasses.get(position).getId()).child("skor").child("phoneNumbers").child(phone).exists())
                            {
                                Toast.makeText(getApplicationContext(),"daha once oy verdiniz bu yoruma",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                firebase.child(commentClasses.get(position).getId()).child("skor").child("like")
                                        .setValue(String.valueOf(commentClasses.get(position).getLike() + 1));
                                firebase.child(commentClasses.get(position).getId()).child("skor").child("phoneNumbers").child(phone).setValue(true);
                            }
                        }
                        else if(!c) {
                            if(dataSnapshot.child(commentClasses.get(position).getId()).child("skor").child("phoneNumbers").child(phone).exists())
                                Toast.makeText(getApplicationContext(), "daha once oy verdiniz bu yoruma", Toast.LENGTH_SHORT).show();
                            else {
                                firebase.child(commentClasses.get(position).getId()).child("skor").child("dislike")
                                        .setValue(String.valueOf(commentClasses.get(position).getDislike() + 1));
                                firebase.child(commentClasses.get(position).getId()).child("skor").child("phoneNumbers").child(phone).setValue(true);
                            }
                        }
                        else
                        {

                            String commentPhone=commentClasses.get(position).getPhoneNumber();
                            if(!phone.equals(commentPhone)) {
                                Toast.makeText(getApplicationContext(),"bu yorumu yapan kisiyi engellediniz",Toast.LENGTH_SHORT).show();
                               Intent inta=new Intent(getApplicationContext(),comments2v.class);
                                firebase2.child(commentPhone).setValue(true);
                                startActivity(inta);
                            }
                            else Toast.makeText(getApplicationContext(),"bu senin numaran",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        pDialog.hide();

// like artÄ±rma


    }



  /*  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    public interface OnItemClickListener {
        void onItemClick(CommentClass cm);
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public int ListSize()
    {
       final ArrayList<String> blocking=new ArrayList<>();

        Firebase firebase3=new Firebase(MyFireBaseClass2.url+"users/"+phone2+"/engelle/comments");

        firebase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    blocks.add(ds.getKey());


                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return blocks.size();
    }
    public void GeriGit(View v) {

        switch (v.getId()) {
            case R.id.gerigit:

                Intent intent = new Intent(comments2v.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                finish();
                break;
        }
    }

}
