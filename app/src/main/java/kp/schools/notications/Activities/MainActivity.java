package kp.schools.notications.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kp.schools.notications.Helpers.PrefManager;
import kp.schools.notications.Model.LoginResponseModel;
import kp.schools.notications.R;
import kp.schools.notications.services.VolleyService;

public class MainActivity extends AppCompatActivity {
    public static String api_url="https://psra.gkp.pk/schoolReg/login_api";
    VolleyService volleyService;
    RotateLoading rotateLoading;
    EditText username,password;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        rotateLoading=findViewById(R.id.rotation);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        volleyService=new VolleyService(this);




        PrefManager prefManager=new PrefManager(this);
        boolean checkLoginIn=prefManager.getLoginStatus(this);
        if (checkLoginIn){
            startActivity(new Intent(this, WebApp.class));
            finish();
        }


    }

    public void login(View view) {


        if (username.getText().toString().length()>3 && password.getText().toString().length()>3){

            rotateLoading.start();

            volleyService.longinFun(api_url, username.getText().toString().trim(),
                    password.getText().toString().trim(),
                    new VolleyService.VolleyResponseListener() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d("responce",response);
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String requestCode=jsonObject.getString("success");
                                Log.d("success code",requestCode);
                                if (requestCode.matches("200")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("userData");
                                    LoginResponseModel loginResponseModel=new LoginResponseModel();
                                    JSONObject userDataObject = jsonArray.getJSONObject(0);
                                    loginResponseModel.setRole_id(userDataObject.getString("role_id"));
                                    loginResponseModel.setUserTitle(userDataObject.getString("userTitle"));
                                    loginResponseModel.setUserName(userDataObject.getString("userName"));
                                    loginResponseModel.setUserEmail(userDataObject.getString("userEmail"));
                                    loginResponseModel.setGender(userDataObject.getString("gender"));
                                    loginResponseModel.setContactNumber(userDataObject.getString("contactNumber"));
                                    loginResponseModel.setDistrict_id(userDataObject.getString("district_id"));
                                    loginResponseModel.setCreatedDate(userDataObject.getString("createdDate"));
                                    loginResponseModel.setUser_id(userDataObject.getString("user_id"));

                                    new PrefManager(MainActivity.this).keepLogedIn(username.getText().toString().trim(),
                                            password.getText().toString().trim());
                                    new PrefManager(MainActivity.this).saveLoggedInUser(loginResponseModel);
                                    new PrefManager(MainActivity.this).saveLoginStatus(MainActivity.this,true);
                                    updateToken(loginResponseModel.getUser_id());




                                }
                                else if (requestCode.matches("0")){
                                    Toast.makeText(MainActivity.this, "Invalid Username Or Password", Toast.LENGTH_SHORT).show();

                                }
                                else if (requestCode.matches("204")){
                                    Toast.makeText(MainActivity.this, "Your Account Is blocked Contact Admin", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            rotateLoading.stop();

                        }

                        @Override
                        public void onError(VolleyError error) {
                            Log.d("VolleyError",error.getMessage().toString());
                            rotateLoading.stop();
                        }
                    });
        }
        else {
            Toast.makeText(this, "fill the credentials..", Toast.LENGTH_SHORT).show();
        }



    }

    private void updateToken(final String userid) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token=instanceIdResult.getToken();
                Log.d("token",token);
                volleyService.UpdateToken("https://psra.gkp.pk/schoolReg/updateAppKey_api",
                        userid, token, new VolleyService.VolleyResponseListener() {
                            @Override
                            public void onSuccess(String response) {

                                Log.d("updateToken",response);
                                startActivity(new Intent(MainActivity.this,WebApp.class));
                                finish();
                            }

                            @Override
                            public void onError(VolleyError error) {

                            }
                        });



            }



        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}