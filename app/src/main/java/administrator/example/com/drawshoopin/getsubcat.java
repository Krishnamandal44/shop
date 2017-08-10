package administrator.example.com.drawshoopin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class getsubcat extends AppCompatActivity {
    String url="";
    String pcatid="";
    catmodel catmodel;
    JSONParser jp;
    ProgressDialog pd;
    ListView lv;
    String c_id="",c_name="",c_image="";
    Button back,show;
    ArrayList<catmodel> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getsubcat);
        back=(Button)findViewById(R.id.back);
        show=(Button)findViewById(R.id.show);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getsubcat.this,Maindrawer.class));
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getsubcat.this,List_View_Show.class));
            }
        });
        pcatid=getIntent().getExtras().getString("catid");
        url="http://220.225.80.177/onlineshoppingapp/show.asmx/getsubcat?catide="+pcatid;
        lv=(ListView)findViewById(R.id.lv);
        jp=new JSONParser();

        pd=new ProgressDialog(getsubcat.this);
        pd.setMessage("Please Wait...");
        pd.setTitle("Loading");
        pd.setCancelable(false);
        new load_getsubcat().execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getsubcat.this,""+arrayList.get(position).getCid(),Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getsubcat.this,Getproduct.class).putExtra("proid",arrayList.get(position).getCid()));
            }
        });


    }

    public class load_getsubcat extends AsyncTask<String,Integer,String>
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
                Toast.makeText(getsubcat.this,"No Data Found",Toast.LENGTH_SHORT).show();
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
