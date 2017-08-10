package administrator.example.com.drawshoopin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by raja on 07-05-2017.
 */
public class Log_in extends AppCompatActivity {
    String username,Pass;
    EditText et1,et2;
    Button login;
    String url="";
    String success,pw;
    LinearLayout lnsignup;
    String s_val,p_val,u_val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        lnsignup=(LinearLayout) findViewById(R.id.lnsignup);
        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        login=(Button)findViewById(R.id.login);

        try {
            s_val = getPreference("username", "");
            p_val=getPreference("pass","");
            u_val=getPreference("user","");

            if (!s_val.equals("")||!p_val.equals("")) {
                //Strore in AppData
                appsdata.UN=s_val;
                appsdata.uname=u_val;
                finish();
                Intent i=new Intent(Log_in.this,Show_delivery_address.class);
                i.putExtra("emailid",s_val);
                startActivity(i);
                //Toast.makeText(getApplicationContext(),s_val,Toast.LENGTH_LONG).show();


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        lnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent= new Intent(Log_in.this,Sign_up.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et1.getText().toString();
                String pass = et2.getText().toString();

               if(email.equals(""))
               {
                   et1.setError("Email id required");
                   return;
               }
                if (pass.equals(""))
                {
                    et2.setError("Password required");
                    return;
                }
                url = "http://220.225.80.177/onlineshoppingapp/show.asmx/LogIn?EmailId=" + email + "&Pwd=" + pass;
                if(Util.isConnected(Log_in.this)) {
                    new Login().execute("");
                }
                else
                {
                    Toast.makeText(Log_in.this, "Please on internet connection", Toast.LENGTH_SHORT).show();
                }

            }


        });

    }
    public class Login extends AsyncTask<String,String,String>
    {
      ProgressDialog pd= null;
        @Override
        protected String doInBackground(String... params) {

         JSONParser jp=new JSONParser();

            try {
                JSONObject jobj=jp.getJsonFromURL(url);
                success= jobj.getString("success");
                JSONArray jarr = jobj.getJSONArray("User_Reg");
                JSONObject jo= jarr.getJSONObject(0);
                 pw=jo.getString("Email");
                username=jo.getString("Name");
                Pass=jo.getString("Pwd");

                //For SharePreference
                savedPreference("username", pw);
                savedPreference("pass", Pass);
                savedPreference("user", username);


                //Strore in AppData
                appsdata.UN=pw;
                appsdata.uname=username;
                finish();
                startActivity(new Intent(Log_in.this,Maindrawer.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(Log_in.this);
            pd.setMessage("Please Wait.....");
            pd.show();

        }

        @Override
        protected void onPostExecute(String s) {
              pd.dismiss();
            if (success.equals("1"))
            {
                Toast.makeText(Log_in.this, ""+username, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(Log_in.this, "Enter Correct UserName or Password", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public String getPreference(String key,String value)
    {
        String pref_value="";
        SharedPreferences sp1=getSharedPreferences("PREF",Context.MODE_PRIVATE);
        pref_value=sp1.getString(key, value);
        return  pref_value;
    } public void savedPreference(String key,String value)
    {
        SharedPreferences sp=getSharedPreferences("PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,value);
        editor.commit();
    }
}
