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

public class Getproduct extends AppCompatActivity {
    String getproductid="";
    String url="";
    ListView lv;
    ProgressDialog pd;
    JSONParser jpp;
    getproductmodel getproduct;
    Button back,show;
    String p_id="",c_id="",i_name="",i_desc="",m_price="",w_price="",a_avai="",p_image="";
    ArrayList<getproductmodel>arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getproductlistview);
        back=(Button)findViewById(R.id.back);
        show=(Button)findViewById(R.id.show);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Getproduct.this,getsubcat.class));
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Getproduct.this,List_View_Show.class));
            }
        });
        jpp=new JSONParser();
        pd=new ProgressDialog(Getproduct.this);
        lv=(ListView)findViewById(R.id.lv_get_product);
        pd.setMessage("Please Wait...");
        pd.setTitle("Loading");
        pd.setCancelable(false);
        getproductid=getIntent().getExtras().getString("proid");
        url="http://220.225.80.177/onlineshoppingapp/show.asmx/GetProduct?catId="+getproductid;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Getproduct.this, "hiiiiii", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Getproduct.this,Add_To_Cart.class);
                i.putExtra("pimage",arrayList.get(position).getPimage());
                i.putExtra("cid",arrayList.get(position).getCid());
                i.putExtra("pid",arrayList.get(position).getPid());
                i.putExtra("piname",arrayList.get(position).getIname());
                i.putExtra("pidesc",arrayList.get(position).getIdesc());
                i.putExtra("pmprice",arrayList.get(position).getMprice());
                i.putExtra("pwprice",arrayList.get(position).getWprice());
                i.putExtra("pavai",arrayList.get(position).getAvai());
                startActivity(i);

            }
        });
        new loadgetproduct().execute();
    }
    public class loadgetproduct extends AsyncTask<String,Integer,String>{
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
                Toast.makeText(Getproduct.this,"No Product Found",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                JSONObject jsonObject=jpp.getJsonFromURL(url);
                JSONArray jrrr=jsonObject.getJSONArray("Products");
                for(int j=0;j<jrrr.length();j++)
                {

                    JSONObject jobjj=jrrr.getJSONObject(j);
                    p_id=jobjj.getString("Product_Id");
                    c_id=jobjj.getString("Category_Id");
                    i_name=jobjj.getString("Item_Name");
                    i_desc=jobjj.getString("Item_Desc");
                    m_price=jobjj.getString("Market_Price");
                    w_price=jobjj.getString("Web_Price");
                    a_avai=jobjj.getString("Availability");
                    p_image=jobjj.getString("Product_Image");
                    getproduct =new getproductmodel();
                    getproduct.setPid(p_id);
                    getproduct.setCid(c_id);
                    getproduct.setIname(i_name);
                    getproduct.setIdesc(i_desc);
                    getproduct.setMprice(m_price);
                    getproduct.setWprice(w_price);
                    getproduct.setAvai(a_avai);
                    getproduct.setPimage(p_image);
                    arrayList.add(getproduct);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public class myadapter extends BaseAdapter{

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

            v=getLayoutInflater().inflate(R.layout.getproduct,parent,false);
            ImageView tv_image=(ImageView)v.findViewById(R.id.image);

            TextView tv_iname=(TextView)v.findViewById(R.id.iname);

            TextView tv_mprice=(TextView)v.findViewById(R.id.mprice);
            TextView tv_wprice=(TextView)v.findViewById(R.id.wprice);
            TextView tv_avail=(TextView)v.findViewById(R.id.avail);

            tv_iname.setText(arrayList.get(position).getIname());

            tv_mprice.setText(arrayList.get(position).getMprice());
            tv_wprice.setText(arrayList.get(position).getWprice());
            tv_avail.setText(arrayList.get(position).getAvai());
            Picasso.with(getApplicationContext()).load(arrayList.get(position).getPimage()).into(tv_image);
            return v;
        }
    }

}
