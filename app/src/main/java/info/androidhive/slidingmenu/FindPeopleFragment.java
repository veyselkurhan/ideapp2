package info.androidhive.slidingmenu;

/**
 * Created by admin on 25.03.2016.
 */

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.veysel.buyukproje.R;
import com.example.veysel.buyukproje.Register;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class FindPeopleFragment extends Fragment {
    ListView lv;
    ArrayList <PeopleClass> people=new ArrayList<>();
    Button gerigitPeople;
    RecyclerView rc;
    PeopleAdapter adapter;
Button btn;
    public FindPeopleFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_people, container, false);

        Firebase.setAndroidContext(getActivity());

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
//        lv= (ListView) rootView.findViewById(R.id.lvshowfriends);
        btn= (Button) rootView.findViewById(R.id.updateFriend);
        gerigitPeople = (Button) rootView.findViewById(R.id.gerigitPeople);
rc= (RecyclerView) rootView.findViewById(R.id.rcshowPeople);
        final String phone=sharedPreferences.getString("MyPhone", "");
        Firebase fir1=new Firebase(MyFireBaseClass.url);
        fir1.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            people.clear();

                for (DataSnapshot ds : dataSnapshot.child("users").child(phone).child("friends").getChildren()) {

                    if (dataSnapshot.child("users").child(ds.getKey()).exists()) {
                        String name=dataSnapshot.child("users").child(ds.getKey()).child("Name").getValue().toString();
                        String phone2=ds.getKey();
                        boolean takip=true;
                        boolean engelle=(boolean)ds.getValue();
                        if(dataSnapshot.child("users").child(phone).child("engelle").child("takip").child(phone2).exists())
                            takip=false;

                        people.add(new PeopleClass(name,phone2,engelle,takip));
                    }

                }
                try
                {

                    adapter = new PeopleAdapter(people);

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    rc.setLayoutManager(mLayoutManager);
                    rc.setItemAnimator(new DefaultItemAnimator());
                    rc.setAdapter(adapter);

                    adapter.setOnItemClickListener(new PeopleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position,boolean b) {
                           final PeopleClass pc=people.get(position);
                            final Firebase firebase2=new Firebase(MyFireBaseClass.url+"users/"+phone+"/friends");


                            if(pc.isEngelleme()&& b)
                            firebase2.child(pc.getPhone()).setValue(false);
                            else if(!pc.isEngelleme()&& b)
                                firebase2.child(pc.getPhone()).setValue(true);
                            if(!b && pc.isTakip())
                            {
                                Firebase firebase =new Firebase(MyFireBaseClass.url+"users/"+phone+"/engelle/takip");
                                firebase.child(pc.getPhone()).setValue(false);
                            }
                            else if(!b && !pc.isTakip()) {
                                Firebase firebase = new Firebase(MyFireBaseClass.url+"users/"+phone+"/engelle/takip");
                                firebase.child(pc.getPhone()).removeValue();
                            }
                        }
                    });
                }
                catch (Exception e)
                {

                }

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

            public void GeriGit(View v) {

                getActivity().getFragmentManager().popBackStack();

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register rg = new Register();
                final ArrayList<String> phones = deneme();
                Toast.makeText(getActivity(),String.valueOf("phone "+phones.get(4)),Toast.LENGTH_LONG).show();
                final Firebase firebase = new Firebase(MyFireBaseClass.url + "/users/" + phone + "/friends");

                firebase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (int a = 0; a < phones.size(); a++) {
                            if (!dataSnapshot.child(phones.get(a)).exists())
                                firebase.child(phones.get(a)).setValue(false);

                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }
        });

        return rootView;
    }
    public ArrayList<String> deneme(){
        ContentResolver cr = getActivity().getContentResolver();

        final Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        ArrayList <String> phones2=new ArrayList<>();
        while (phones.moveToNext()) {

            //final   String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone2 = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phone2=phone2.replace("+90", "0");
            phone2=phone2.replace(" ", "");
            phones2.add(phone2);
        }
        return phones2;
    }
}