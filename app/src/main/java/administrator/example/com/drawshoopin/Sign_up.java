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
public class Sign_up extends AppCompatActivity {

    EditText nam,pass,phone,mail;
    Button register;
    String url="";
    String email;
    String succ;
    LinearLayout login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singnup);
        nam=(EditText)findViewById(R.id.nam);
        pass=(EditText)findViewById(R.id.pass);
        phone=(EditText)findViewById(R.id.phone);
        mail=(EditText)findViewById(R.id.mail);
        register=(Button)findViewById(R.id.register);
        login=(LinearLayout) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Sign_up.this,Log_in.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nam.getText().toString();
                String password=pass.getText().toString();
                String phon=phone.getText().toString();
                email=mail.getText().toString();


                if (name.equals("")||name.length()<3){
                    nam.setError("Enter valid name");
                    return;
                }
                if (password.equals("")||password.length()<6){

                    return;
                }
                if (phon.equals("")||phon.length()<10){
                    return;
                }
                if (email.equals("")){
                    mail.setError("Enter valid EmailId ");
                    return;
                }

                    url="http://220.225.80.177/onlineshoppingapp/show.asmx/Registration?Name="+name+"&Password="+password+"&PhNo="+phon+"&EmailId="+email;
                   if (Util.isConnected(Sign_up.this)) {
                       new signup().execute("");
                   }
                else {
                       Toast.makeText(Sign_up.this, "Please on mobile data or wifi", Toast.LENGTH_LONG).show();
                   }
                }

        });

    }
    public class signup extends AsyncTask<String,String,String>
    {
        ProgressDialog pd= null;
        @Override
        protected String doInBackground(String... params) {

         JSONParser jp= new JSONParser();

            try {
                JSONObject jobj= jp.getJsonFromURL(url);
                JSONArray jrr=jobj.getJSONArray("Response");
                JSONObject jo= jrr.getJSONObject(0);
                succ = jo.getString("Messagetext");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(Sign_up.this);
            pd.setMessage("Please Wait.....");
            pd.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            if (succ.equals("Sucess"))
            {
                //Toast.makeText(Sign_up.this, ""+succ, Toast.LENGTH_SHORT).show();

                finish();
                Intent i=new Intent(Sign_up.this,Delivery_Address.class);
                i.putExtra("Email_id",email);
                startActivity(i);
            }
            else
            {
                Toast.makeText(Sign_up.this, "EmailId already registered put different Id", Toast.LENGTH_SHORT).show();
            }
        }
    }




}
