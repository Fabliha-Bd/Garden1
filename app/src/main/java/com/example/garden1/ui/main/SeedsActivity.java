package com.example.garden1.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.garden1.ui.main.Model.CartItem;
import com.example.garden1.ui.main.Model.Upload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.garden1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeedsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SeedsImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRefCart;
    private FirebaseAuth firebaseAuth;
    private List<Upload> mUploads;
    private HashMap<String,CartItem> itemsMap;
    private FloatingActionButton btnGoToCart;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeds);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("Seeds");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent= new Intent(SeedsActivity.this,AdminUploadActivity.class);
                startActivity(intent);
            }
        });
        mDatabaseRefCart = FirebaseDatabase.getInstance().getReference("cartitem");
        Log.v("Cart",mDatabaseRefCart.getRepo().toString());
        firebaseAuth= FirebaseAuth.getInstance();
        String uploadIdParent = firebaseAuth.getCurrentUser().getUid();


        btnGoToCart= (FloatingActionButton) findViewById(R.id.btnGoToCart);
        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CartText="";
                for (Map.Entry<String, CartItem> entry : itemsMap.entrySet()) {
                    CartText+=(entry.getKey() + " = " + entry.getValue()+"\n");
                    CartItem cartItem = new CartItem(entry.getValue().getName(),
                    entry.getValue().getPrice(), entry.getValue().getType(),entry.getValue().getQuantity());
                    //time
                    LocalDateTime myDateObj = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String presentDateTime = myDateObj.format(myFormatObj);
                    Log.v("Cart","Present Date Time "+presentDateTime);
                    //key
                    String uploadId= mDatabaseRefCart.push().getKey();
                    mDatabaseRefCart.child(uploadIdParent).child(presentDateTime).child(uploadId).setValue(cartItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Log.v("Cart","Cart Data Added");

                            }
                            else{
                                String error= task.getException().getMessage();
                                Toast.makeText(SeedsActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                Intent intent= new Intent(SeedsActivity.this,CartTab.class);
                finish();
                Log.v("Cart",CartText);
            }
        });

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
       // items= new ArrayList<>();
        itemsMap=new HashMap<String, CartItem>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("seeds/");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter = new SeedsImageAdapter(SeedsActivity.this, mUploads, itemsMap);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SeedsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }


}