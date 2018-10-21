package com.example.devoprakesh.trackingappchild;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class UserDetails extends AppCompatActivity {

    EditText regname,regemailid;
    String strname,stremailid;
    FirebaseDatabase firebaseDatabase;
    int Unicode=0;
    DatabaseReference databaseReference;

    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        regname = findViewById(R.id.regname);
        regemailid = findViewById(R.id.emialreg);
        next = findViewById(R.id.nextbtn);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strname = regname.getText().toString();
                stremailid = regemailid.getText().toString();

                if(!TextUtils.isEmpty(strname) && !TextUtils.isEmpty(stremailid)){

                    Unicode = getCode();
                    final String unicodestr = "C-"+Unicode;



                    UserData data = new UserData(strname,stremailid,unicodestr);
                    SharedPreferences sharedPreferences = getSharedPreferences("ChildLoginDetails",MODE_PRIVATE);

                    final String Usernumber = sharedPreferences.getString("Number",null);
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users");

                    databaseReference.child(Usernumber).setValue(data)
                            .addOnCompleteListener
                                    (new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                LayoutInflater inflator = LayoutInflater.from(UserDetails.this);

                                View unicodeview = inflator.inflate(R.layout.promptunicode,null);
                                AlertDialog.Builder builder = new AlertDialog.Builder
                                        (UserDetails.this);

                                TextView unocode = unicodeview.findViewById(R.id.unicodeval);

                                unocode.setText(unicodestr);
                                builder.setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                            }else{
                                Toast.makeText(UserDetails.this
                                        ,"Data Update Error"
                                        ,Toast.LENGTH_LONG);

                            }
                        }
                    });




                }else{

                    Toast.makeText(UserDetails.this
                            ,"Input Feilds Cannot be Emplty"
                            ,Toast.LENGTH_LONG);
                }
            }
        });
    }


    int getCode(){

        return (int) (10000+new Random().nextFloat()*900000);
    }
}
