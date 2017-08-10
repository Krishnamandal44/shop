package administrator.example.com.drawshoopin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Maindrawer extends AppCompatActivity {

    final Context context=this;
    catmodel catmodel;
    JSONParser jp;
    ProgressDialog pd;
    ListView lv;
    TextView num;
    String username;
    String c_id="",c_name="",c_image="";
    String url="http://220.225.80.177/onlineshoppingapp/show.asmx/getcatagory?";
    ArrayList<catmodel> arrayList=new ArrayList<>();
String s_val;
    DrawerLayout mDrawerLayout;
    FragmentManager fm = getSupportFragmentManager();
    mNavigationFragment fragment;
    LinearLayout ln_menue;

    @Override

    public void onBackPressed() {



        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(context);
        alertdialogbuilder.setTitle("Confirm close");
        alertdialogbuilder.setMessage("Do you want to close?");
        alertdialogbuilder.setCancelable(false);//if click on the outside of alert then no out alert
        //alertdialogbuilder.setCancelable(true); // if click on the outside of alert then out alert
        alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                //Toast.makeText(MainActivity.this, "Chandan(1370)", 50000).show();
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });
        alertdialogbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                dialog.cancel();


            }
        });

        AlertDialog alertdia=alertdialogbuilder.create();
        alertdia.show();
        //finish();
        //android.os.Process.killProcess(android.os.Process.myPid());



        //super.onBackPressed();

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maindrawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fragment = (mNavigationFragment) fm.findFragmentById(R.id.fragmentitem);
        ln_menue = (LinearLayout) findViewById(R.id.ln_menue);
        lv=(ListView)findViewById(R.id.lv);
        jp=new JSONParser();
        pd=new ProgressDialog(Maindrawer.this);
        pd.setMessage("Please Wait...");
        pd.setTitle("Loading");
        pd.setCancelable(false);

        num=(TextView)findViewById(R.id.nam);
        username=(appsdata.uname);
        num.setText(username);
        /*ViewFlipper  simpleViewFlipper = (ViewFlipper) findViewById(R.id.viewflipper); // get the reference of ViewFlipper
        simpleViewFlipper.setFlipInterval(1000);*/


        new load().execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Maindrawer.this,""+arrayList.get(position).getCid(),Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Maindrawer.this,getsubcat.class).putExtra("catid",arrayList.get(position).getCid()));
            }
        });
        ln_menue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }
    public class load extends AsyncTask<String,Integer,String>
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
            if(arrayList.size()>0)
            {
                lv.setAdapter(new myadapter());
            }
            else
            {
                Toast.makeText(Maindrawer.this,"Server Down or no internet connection",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                JSONObject jobj=jp.getJsonFromURL(url);
                JSONArray jrr=jobj.getJSONArray("Category");
                for(int i=0;i<jrr.length();i++)
                {
                    JSONObject jobjj=jrr.getJSONObject(i);
                    c_id=jobjj.getString("Cat_Id");
                    c_name=jobjj.getString("Cat_Name");
                    c_image=jobjj.getString("Cat_Image");
                    catmodel =new catmodel();
                    catmodel.setCid(c_id);
                    catmodel.setCname(c_name);
                    catmodel.setCimage(c_image);
                    arrayList.add(catmodel);
                }

            }catch (Exception e){e.printStackTrace();}


            return null;
        }
    }



    public class myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {

            v=getLayoutInflater().inflate(R.layout.row,parent,false);
            TextView tv_cid=(TextView)v.findViewById(R.id.id);
            TextView tv_cname=(TextView)v.findViewById(R.id.name);
            ImageView tv_image=(ImageView)v.findViewById(R.id.image);
            tv_cid.setText(arrayList.get(position).getCid());
            tv_cname.setText(arrayList.get(position).getCname());
            Picasso.with(getApplicationContext()).load(arrayList.get(position).getCimage()).into(tv_image);
            return v;
        }
    }



}
