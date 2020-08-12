package kp.schools.notications.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import kp.schools.notications.Activities.MainActivity;
import kp.schools.notications.Model.LoginResponseModel;

public class PrefManager {

    private Context context;

    public static final String MyPREFERENCES = "polioShareReferences";
    public static SharedPreferences.Editor editor;
    private SharedPreferences sharedpreferences;

    public PrefManager(Context context) {
        this.context = context;
    }

    public void keepLogedIn(String email,String password) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("keepLogedIn", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }
    public void logoutUser(){

        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("LoggedInUser");


        editor.apply();
    }

    public boolean isLoggedIn(){

        SharedPreferences sharedPreferences = context.getSharedPreferences("keepLogedIn", Context.MODE_PRIVATE);

        if(sharedPreferences.contains("email") && sharedPreferences.contains("password") )
        {
            return true;
        }
        else {
            return false;
        }

    }

    public void saveLoggedInUser(LoginResponseModel model) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(model);
        editor.putString("LoggedInUser", serializedObject);
        editor.apply();
    }


    /*save Login Status*/

    

    public void saveLoginStatus(Context context,boolean status){

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        editor.putBoolean("loginstatus", status);
        editor.commit();

    }

    public Boolean getLoginStatus(Context ctx){

        SharedPreferences prfs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        Boolean link=prfs.getBoolean("loginstatus",false);
        return link;
    }
}
