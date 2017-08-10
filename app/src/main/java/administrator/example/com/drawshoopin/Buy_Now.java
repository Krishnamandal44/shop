package administrator.example.com.drawshoopin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class Buy_Now extends AppCompatActivity {
    EditText cardno,cvv,exdate;
    LinearLayout confirm;
    String s_val,p_val;
    String url="";
    TextView money;
    JSONParser jp;
    ProgressDialog pd;
    String mamount;
    String order_sucess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd=new ProgressDialog(Buy_Now.this);
        pd.setMessage("Please wait");
        pd.setTitle("Loading");
        jp=new JSONParser();
        setContentView(R.layout.activity_buy__now);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        cardno=(EditText)findViewById(R.id.cardno);
        cvv=(EditText)findViewById(R.id.cvv);
        exdate=(EditText)findViewById(R.id.exdate);
        money=(TextView)findViewById(R.id.money);
        confirm=(LinearLayout)findViewById(R.id.confirm);
        mamount=getIntent().getExtras().getString("pamount");
        money.setText(mamount);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String card_no=cardno.getText().toString();
                String cvv_no=cvv.getText().toString();
                String date=exdate.getText().toString();
                String id_mail=(appsdata.UN);
                if(card_no.equals("")||cvv_no.equals("")||date.equals(""))
                {
                    Toast.makeText(Buy_Now.this, "Please Enter Filed", Toast.LENGTH_SHORT).show();
                }
                else if(card_no.length()<5)
                {
                    cardno.setError("Enter valid card number");
                }
                else if(cvv_no.length()<3)
                {
                    cvv.setError("Enter valid cvv number");
                }
               else if(date.length()<4)
                {
                    exdate.setError("Enter valid expiry date");
                }
                else
                {
                    url="http://220.225.80.177/apptransaction/WebService.asmx/Transaction?cardNo="+card_no+"&cvvCode="+cvv_no+"&expdate="+date+"&amount="+mamount+"&emailid="+id_mail;
                    new load().execute();
                }
            }
        });

        try {
            s_val = getPreference("username", "");
            p_val=getPreference("pass","");
            if (s_val.equals("")||p_val.equals("")) {
                //Strore in AppData
                //appsdata.UN=username;
                finish();
                startActivity(new Intent(Buy_Now.this,Log_in.class));
                //Toast.makeText(getApplicationContext(),s_val,Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private class load extends AsyncTask<String,Integer,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            Toast.makeText(Buy_Now.this, ""+order_sucess, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected String doInBackground(String... params) {
            try{
                JSONObject jsonObject=jp.getJsonFromURL(url);
                JSONObject mess=jsonObject.getJSONObject("Response");
                order_sucess=mess.getString("Messagetext");



            }
            catch (Exception e){e.printStackTrace();}

            return null;
        }
    }
    public String getPreference(String key,String value)
    {
        String pref_value="";
        SharedPreferences sp1=getSharedPreferences("PREF", Context.MODE_PRIVATE);
        pref_value=sp1.getString(key, value);
        return  pref_value;
    }
}
