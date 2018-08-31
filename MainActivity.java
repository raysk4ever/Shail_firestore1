package one.firestore.shail.shail_firestore1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText e1, e2, e3, e4;
    Button btn, showbtn;
    TextView show;

    FirebaseFirestore mFire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //firebase
        mFire = FirebaseFirestore.getInstance();


        show = (TextView) findViewById(R.id.show);

        e1 = (EditText) findViewById(R.id.editText);

        e2 = (EditText) findViewById(R.id.editText2);

        e3 = (EditText) findViewById(R.id.editText3);

        e4 = (EditText) findViewById(R.id.name);

        btn = (Button) findViewById(R.id.btn2);
        showbtn = (Button) findViewById(R.id.showbtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("name1", e1.getText().toString());
                data.put("name2", e2.getText().toString());
                data.put("name3", e3.getText().toString());

                mFire.collection("crush")
                        .document(e4.getText().toString())
                        .set(data)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "data saved", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showbrnclick();
            }
        });


    }

    public void showbrnclick() {

        mFire.collection("crush")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            for (DocumentSnapshot doc : task.getResult().getDocuments()){

                                if (doc.getId().equals(e4.getText().toString())){

                                    Toast.makeText(MainActivity.this, "found", Toast.LENGTH_SHORT).show();
                                    show.setText(
                                            doc.getString("name1")+
                                                    doc.getString("name2")+
                                                    doc.getString("name3")


                                    );

                                }


                            }



                        }


                    }
                });


    }
}




