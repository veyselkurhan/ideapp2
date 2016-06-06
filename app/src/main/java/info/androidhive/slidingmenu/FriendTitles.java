package info.androidhive.slidingmenu;

import android.app.Fragment;
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
import android.widget.ListView;

import com.example.veysel.buyukproje.R;
import com.example.veysel.buyukproje.comments2v;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class FriendTitles extends Fragment {
    ArrayList <String> blocks=new ArrayList<>();
    ListView lv ;
     String myPhone;
    RecyclerView rc;
    TitlesAdapter adapter;
    View rootView;
    int a=0;
    ArrayList <String> myFriendsTitles=new ArrayList<>();
    ArrayList <String> titleID=new ArrayList<>();

    ArrayList<TitleClass> titleClasses=new ArrayList<>();
    OnItemClickListener onItemClickListener;

    public FriendTitles() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_friend_titles2, container, false);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
          myPhone=sharedPreferences.getString("MyPhone", "");
        final ArrayList <String> myFriends=new ArrayList<>();

        //lv= (ListView) rootView.findViewById(R.id.lvFrientitles);
rc= (RecyclerView) rootView.findViewById(R.id.rcfriendtitles);
        Firebase.setAndroidContext(getActivity());
        Firebase firebase=new Firebase(MyFireBaseClass.url+"/titles");
blocksTitles();


        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                titleClasses.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (ds.child("friends").child(myPhone).exists()) {
                        titleID.add(ds.getKey());
                        String phone2 = ds.child("creatorPhone").getValue().toString();
                        boolean kontrol = blocks.contains(phone2);
                        if (!kontrol) {
                            // myFriendsTitles.add(ds.child("titleDetail").getValue().toString());
                            String text = ds.child("titleDetail").getValue().toString();
                            String name = ds.child("name").getValue().toString();
                            String zaman = ds.child("titleDate").getValue().toString();
                            String day = zaman.substring(8, 10);
                            String month = zaman.substring(5, 7);

                            titleClasses.add(new TitleClass(phone2, name, text, day, month));
                        }
                    }
                }
                try {
                    // TitleListViewAdapter adapter = new TitleListViewAdapter(getActivity(), R.layout.showmytitles, titleClasses);

                    // lv.setAdapter(adapter);

                    // ar = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, myFriendsTitles);
                    Collections.reverse(titleClasses);

                    // TitleListViewAdapter adapter = new TitleListViewAdapter(getActivity(), R.layout.showmytitles, titleClasses);
                    // lv.setAdapter(adapter);
                    adapter = new TitlesAdapter(titleClasses);

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
                        editor.putString("profilPhone", titleClasses.get(position).getPhone());

                        editor.apply();

                        if (yer == 0) {
                            editor.putBoolean("profile", true);
                            editor.apply();
                            Profil c = new Profil();
                            getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_container, c, "cm").addToBackStack(null).commit();
                            //    Intent intent = new Intent(getActivity(), Profil.class);
                            //  startActivity(intent);
                        } else if (yer == 1) {
                            //  comments2v c = new comments2v();
                            //     getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_container, c, "cm").addToBackStack(null).commit();
                            Intent intent = new Intent(getActivity(), comments2v.class);
                            startActivity(intent);
                        }

                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


      /*  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences shared = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("titleID", titleID.get(position));
                editor.commit();
            //    comments2v c = new comments2v();
             //   getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_container, c, "cm").addToBackStack(null).commit();
                Intent intent = new Intent(getActivity(), comments2v.class);
                startActivity(intent);
            }
        });
*/
        return rootView;
    }



    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClickListener) {
            onItemClickListener = (OnItemClickListener) context;
        } else {

        }
    }



    public interface OnItemClickListener{
        void onItemClicked(int index);
    }
    public int blocksTitles()
    {
        Firebase firebase=new Firebase(MyFireBaseClass.url+"/users/"+myPhone+"/engelle/takip");
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
