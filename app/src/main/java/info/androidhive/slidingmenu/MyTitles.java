package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.veysel.buyukproje.R;
import com.example.veysel.buyukproje.comments2v;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class MyTitles extends Fragment {
    ArrayList<String> titleID=new ArrayList<String>();
TitlesAdapter adapter;
    RecyclerView rc;
    int count=1;
    OnItemClickListener onItemClickListener;
    ListView lv;
    ArrayAdapter<String> ar;
    int posts=0;
    int comments=0;
    boolean evre=false;
    ArrayList<TitleClass> titleclasses=new ArrayList<TitleClass>();
    Button btnAddTitle;
    private ProgressDialog pDialog;


    public MyTitles() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClickListener) {
            onItemClickListener = (OnItemClickListener) context;
        } else {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_my_titles2, container, false);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("BaÅŸlÄ±klarÄ±nÄ±z Getiriliyor...");
        pDialog.setCancelable(false);
        pDialog.show();
        Firebase.setAndroidContext(getActivity());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("MyData",Context.MODE_PRIVATE);

        final String phone=sharedPreferences.getString("MyPhone", "");
        lv= (ListView) rootView.findViewById(R.id.lvShowtitles);
        final EditText etTitle= (EditText) rootView.findViewById(R.id.etTitleDetail);
        btnAddTitle= (Button) rootView.findViewById(R.id.btnAddNewTitle);
rc= (RecyclerView) rootView.findViewById(R.id.rcMytitles);
        Firebase firebase=new Firebase(MyFireBaseClass.url+"titles/");


        btnAddTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    pDialog = new ProgressDialog(getActivity());
                    pDialog.setMessage("Başlık açılıyor...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    Firebase firebase = new Firebase(MyFireBaseClass.url + "titles/");
                    final Firebase fir2 = firebase.push();
                    Firebase firebase1 = new Firebase(MyFireBaseClass.url + "users/" + phone);
                    final Firebase fir1 = firebase1;

                    fir2.child("titleDetail").setValue(etTitle.getText().toString());
                    fir2.child("creatorPhone").setValue(phone);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    System.out.println(dateFormat.format(date).substring(8, 10));
                    fir2.child("titleDate").setValue(dateFormat.format(date));
                    evre = true;
count=2;
                    firebase1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            titleclasses.clear();
                            fir2.child("name").setValue(dataSnapshot.child("Name").getValue());
                            if (evre) {

                                fir1.child("stats").child("Posts").setValue(Integer.parseInt(dataSnapshot.child("stats").child("Posts").getValue().toString()) + 1);
                                evre = false;
                            }
                            /*int cnt=0;
                            if(cnt==0){
                                posts= Integer.parseInt(dataSnapshot.child("Posts").getValue().toString());
                                fir1.child("Posts").setValue(posts + 1);
                                cnt++;
                            }*/

                            //fir1.child("Comments").setValue(((int) dataSnapshot.child("Comments").getValue()) + 1);
                            if (count == 2) {
                                for (DataSnapshot ds : dataSnapshot.child("friends").getChildren()) {
                                    if (!(boolean) ds.getValue())
                                        fir2.child("friends").child(ds.getKey()).setValue("true");
                                    count = 1;
                                }
                                HomeFragment c = new HomeFragment();
                                getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_container, c, "cm").addToBackStack(null).commit();
                            }

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });





                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Hata gibi bir ÅŸeyler oldu sanki ya", Toast.LENGTH_LONG).show();

                }
                pDialog.hide();
                Toast.makeText(getActivity(), "eklendi", Toast.LENGTH_SHORT).show();
                etTitle.setText("");
            }


        });

        firebase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                titleclasses.clear();
                try {
                    titleID.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        if (ds.child("creatorPhone").getValue().toString().equals(phone)) {
                            titleID.add(ds.getKey());

                        }
                    }
                } catch (Exception e) {

                }



                for (int a = 0; a <titleID.size(); a++) {

                    // str.add(dataSnapshot.child(titleID.get(a)).child("titleDetail").getValue().toString());
                    // titleclasses ekleme yapÄ±yor aÅŸaÄŸÄ±sÄ±
                    String text = "";
                    String name = "";
                    String day = "";
                    String month = "";
                    try {
                        text = dataSnapshot.child(titleID.get(a)).child("titleDetail").getValue().toString();
                        name = dataSnapshot.child(titleID.get(a)).child("name").getValue().toString();
                        String zaman = dataSnapshot.child(titleID.get(a)).child("titleDate").getValue().toString();
                        day = zaman.substring(8, 10);
                        month = zaman.substring(5, 7);

                    } catch (Exception e) {

                    }

                    titleclasses.add(new TitleClass(phone,name, text, day, month));
                }

                if (titleclasses.size() != 0) {
                    //  ar= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, str);

                    try {

                        //TitleListViewAdapter titles = new TitleListViewAdapter(getActivity(), R.layout.showmytitles, titleclasses);
                        // lv.setAdapter(titles);
                        Collections.reverse(titleclasses);
                        adapter = new TitlesAdapter(titleclasses);

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        rc.setLayoutManager(mLayoutManager);
                        rc.setItemAnimator(new DefaultItemAnimator());
                        rc.setAdapter(adapter);

                    } catch (Exception e) {

                    }
                    adapter.setOnItemClickListener(new TitlesAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, int yer) {
                            Collections.reverse(titleID);
                            SharedPreferences shared = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = shared.edit();
                            editor.putString("titleID", titleID.get(position));
                            editor.putString("profilPhone", titleclasses.get(position).getPhone());

                            editor.apply();

                            if (yer == 0) {
                                editor.putBoolean("profile", true);
                                editor.apply();
                                Profil c = new Profil();
                                getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_container, c, "cm").addToBackStack(null).commit();
                                //    Intent intent = new Intent(getActivity(), Profil.class);
                                //  startActivity(intent);
                            }
                            else if(yer==1)
                            {
                                //  comments2v c = new comments2v();
                                //     getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_container, c, "cm").addToBackStack(null).commit();
                                Intent intent = new Intent(getActivity(), comments2v.class);
                                startActivity(intent);
                            }

                        }
                    });

                }

                pDialog.hide();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        //listview item seÃ§me
/*
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  onItemClickListener.onItemClicked(position);

                SharedPreferences shared = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("titleID", titleID.get(position));
                editor.apply();
                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_LONG).show();

             //   comments2v c = new comments2v();
             //   getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_container, c, "cm").addToBackStack(null).commit();
                Intent intent = new Intent(getActivity(), comments2v.class);
                startActivity(intent);

            }
        });


*/
        return  rootView;
    }

    // Create callback interface
    public interface OnItemClickListener{
        void onItemClicked(int index);
    }
}
