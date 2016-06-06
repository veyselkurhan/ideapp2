package info.androidhive.slidingmenu;

/**
 * Created by admin on 25.03.2016.
 */

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.veysel.buyukproje.R;
import com.example.veysel.buyukproje.comments2v;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {
TitlesAdapter adapter;
    boolean kontol;
    Button button1;
    Button button2;
     String phone;
    ListView lv;
    ArrayList <String> titleID=new ArrayList<>();
    ArrayList <TitleClass> titleClasses=new ArrayList<>();
    /* people friendlistesi ve kişinin kendi numarasını içeriyor*/
    ArrayList<String> people=new ArrayList<>();
    ArrayList<PeopleClass> peopleClasses=new ArrayList<>();
    TextView tv;
    RecyclerView recyclerView;
    ProgressDialog pDialog;
    ArrayList<String> blocks=new ArrayList<>();
    private SwipeRefreshLayout swipeContainer2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home2, container, false);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Başlıklar yükleniyor...");
        pDialog.setCancelable(false);
        pDialog.show();

        Firebase.setAndroidContext(getActivity());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        //lv= (ListView) rootView.findViewById(R.id.lvShowhometitles);
        recyclerView= (RecyclerView) rootView.findViewById(R.id.rcshowTitle);

     phone=sharedPreferences.getString("MyPhone", "");
        int a=blocksTitles();
        Firebase fir1=new Firebase(MyFireBaseClass.url);
        fir1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            /* people.clear();
                people.add(phone);
                for (DataSnapshot ds : dataSnapshot.child("users").child(phone).child("frineds").getChildren()) {
                    people.add(ds.getKey());
                    if (!dataSnapshot.child("users").child(ds.getKey()).exists()) {
                        String name=dataSnapshot.child("users").child(ds.getKey()).child("Name").getValue().toString();
            peopleClasses.add(new PeopleClass(name,ds.getKey()));

                    }

                }
                */
                titleID.clear();
                people.clear();
                titleClasses.clear();
                for (DataSnapshot ds : dataSnapshot.child("titles").getChildren()) {
                    try {
                        if (ds.child("creatorPhone").getValue().toString().equals(phone)) {
                            titleID.add(ds.getKey());
                            String name = ds.child("name").getValue().toString();
                            String text = ds.child("titleDetail").getValue().toString();
                            String zaman = ds.child("titleDate").getValue().toString();
                            String day = zaman.substring(8, 10);
                            String month = zaman.substring(5, 7);
                            titleClasses.add(new TitleClass(phone, name, text, day, month));

                        } else {

                            if (ds.child("friends").child(phone).exists()) {
                                String phone2=ds.child("creatorPhone").getValue().toString();
                                kontol=blocks.contains(phone2);
                                if(!kontol)
                                {
                                    titleID.add(ds.getKey());
                                    String name = ds.child("name").getValue().toString();
                                    String text = ds.child("titleDetail").getValue().toString();
                                    String zaman = ds.child("titleDate").getValue().toString();
                                    String day = zaman.substring(8, 10);
                                    String month = zaman.substring(5, 7);

                                    titleClasses.add(new TitleClass(phone2, name, text, day, month));
                                }
                            }
                        }
                    } catch (Exception e) {

                    }

                }
                try {
                    Collections.reverse(titleClasses);

                    // TitleListViewAdapter adapter = new TitleListViewAdapter(getActivity(), R.layout.showmytitles, titleClasses);
                    // lv.setAdapter(adapter);
                    adapter = new TitlesAdapter(titleClasses);

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {

                }
                adapter.setOnItemClickListener(new TitlesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, int yer) {
                        Collections.reverse(titleID);
                        SharedPreferences shared = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putString("titleID", titleID.get(position));
                        editor.putString("profilPhone", titleClasses.get(position).getPhone());

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

                pDialog.hide();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



/*
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  onItemClickListener.onItemClicked(position);
// titleIDleri de reverse ediyoruz
                Collections.reverse(titleID);
                SharedPreferences shared = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("titleID", titleID.get(position));
                editor.apply();
                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_LONG).show();

                comments2v c = new comments2v();
                getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_container, c, "cm").addToBackStack(null).commit();
            }
        });*/
        return rootView;
    }
    public int blocksTitles()
    {
        Firebase firebase=new Firebase(MyFireBaseClass.url+"/users/"+phone+"/engelle/takip");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                blocks.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {

                        blocks.add(ds.getKey());

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return  blocks.size();
    }
}