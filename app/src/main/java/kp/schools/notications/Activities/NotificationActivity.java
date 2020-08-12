package kp.schools.notications.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import kp.schools.notications.Adapters.CartListAdapter;
import kp.schools.notications.Helpers.MyApplication;
import kp.schools.notications.Helpers.PrefManager;
import kp.schools.notications.Helpers.RecyclerItemTouchHelper;
import kp.schools.notications.Model.NotificationModel;
import kp.schools.notications.R;
import kp.schools.notications.services.DatabaseHandler;

public class NotificationActivity extends AppCompatActivity {

    private static final String TAG = NotificationActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<NotificationModel> cartList;
    private CartListAdapter mAdapter;
    private CoordinatorLayout coordinatorLayout;

    // url to fetch menu json
    private NotificationModel notificationModel1;
    private String title;
    private String file_url;
    private String description;
    ImageView nothingfound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getSupportActionBar().hide();

        cartList=new ArrayList<>();
        cartList=new DatabaseHandler(this).getAllContacts();

        NotificationModel notificationModel1=new NotificationModel();
        Log.d("cartList","size"+cartList.size());

        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new CartListAdapter(this, cartList);

        nothingfound=findViewById(R.id.nothingfound);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
      /*  FirebaseMessaging.getInstance().subscribeToTopic("all").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(NotificationActivity.this, "subscribed", Toast.LENGTH_SHORT).show();
            }
        });*/

        if (cartList.size()<=0){

            nothingfound.setVisibility(View.VISIBLE);
        }
        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param



        // making http call and fetching menu json
        /*prepareCart();*/

        Bundle extras = getIntent().getExtras();


        if(extras != null) {


            title = extras.getString("title");
            file_url = extras.getString("file_url");

            description = getIntent().getStringExtra("description");


            Log.d("TEMP", "Video description: " + description);
            Log.d("TEMP", "file_url: " + file_url);
            Log.d("TEMP", "title: " + title);


        } else {
            Log.d("TEMP", "Extras are NULL");

        }
    }




    @Override
    protected void onNewIntent (Intent intent) {
        super .onNewIntent(intent) ;
        Bundle extras = intent.getExtras() ;
        if (extras != null ) {
            if (extras.containsKey( "title" )) {
                String msg = extras.getString( "title" ) ;
                Log.d("titlenotification",extras.toString());
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

      /*  cartList=new DatabaseHandler(this).getAllContacts();
         notificationModel1=new NotificationModel();
        notificationModel1=cartList.get(cartList.size()-1);
        Log.d("onResume",cartList.size()+""+notificationModel1.getTitle());*/





    }

    @Override
    public void setIntent(Intent newIntent) {
        Bundle extras = getIntent().getExtras();


        if(extras != null) {


            title = getIntent().getStringExtra("title");
            file_url = extras.getString("file_url");

            description = getIntent().getStringExtra("description");


            Log.d("TEMP", "Video description: " + description);
            Log.d("TEMP", "file_url: " + file_url);
            Log.d("TEMP", "title: " + title);


        } else {
            Log.d("TEMP", "Extras are NULL");

        }    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,WebApp.class));
        finish();
    }
}
