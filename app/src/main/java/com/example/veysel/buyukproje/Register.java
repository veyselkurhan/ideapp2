package com.example.veysel.buyukproje;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class Register extends Activity {
    EditText name,phone;
    Button btn;
    ImageView imgview;
    ProgressDialog pDialog;
    Context context= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {


            int sdk = Build.VERSION.SDK_INT;
            if (sdk >= 23) {
                int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.READ_CONTACTS);
                if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                            1);

                }

            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        // imgview = (ImageView) findViewById(R.id.imgLogo);
        name= (EditText) findViewById(R.id.etName);
        phone= (EditText) findViewById(R.id.etphoneNumber);
        btn= (Button) findViewById(R.id.btnRegister);
        btn.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View v) {
                   pDialog = ProgressDialog.show(context, "Lütfen Bekleyin", "Üyeliğiniz oluşturuluyor...", true);
                   try {

                       SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                       SharedPreferences.Editor editor = sharedPreferences.edit();
                       editor.putString("MyPhone", phone.getText().toString());
                       editor.putString("MyName", name.getText().toString());
                       editor.putBoolean("login", true);
                       editor.commit();

                       ContentResolver cr = getContentResolver();

                       final Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

                       Firebase.setAndroidContext(Register.this);
                       Firebase fir = new Firebase(MyFireBaseClass2.url + "/users/" + phone.getText().toString());
                       fir.child("Name").setValue(name.getText().toString());
                       fir.child("MailAddress").setValue("");
                       fir.child("Gender").setValue("");
                       fir.child("BirthDate").setValue("");
                       fir.child("stats").child("Posts").setValue(0);
                       fir.child("stats").child("Comments").setValue(0);



                       fir.addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                               while (phones.moveToNext()) {

                                   //final   String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                   String phone2 = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                   phone2=phone2.replace("+90", "0");
                                   phone2=phone2.replace(" ", "");
                                   Firebase fir3 = new Firebase(MyFireBaseClass2.url + "users/" + phone.getText().toString() + "/friends");
                                   fir3.child(phone2).setValue(false);
                               }

                           }

                           @Override
                           public void onCancelled(FirebaseError firebaseError) {

                           }
                       });


                   } catch (Exception e) {
                       Toast.makeText(getApplicationContext(), "Beklenmedik bir hata oldu...", Toast.LENGTH_LONG).show();
                   }
                       pDialog.dismiss();
                       finish();
               }
                               }
        );
    }

}
