package administrator.example.com.drawshoopin;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Add_To_Cart extends AppCompatActivity {
    TextView market_price,web_price,avai,item_name;
    ImageView pro_img;
    Button cart;
    Button buyonline;
    private SQLiteDatabase db;
    String spid="",scid="",siname="",smprice="",swprice="",savai="",simg="";
    Button back,show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__to__cart);
        createdatabase();
        back=(Button)findViewById(R.id.back);
        show=(Button)findViewById(R.id.show);
        cart=(Button)findViewById(R.id.cart);
        buyonline=(Button)findViewById(R.id.buyonline);
        buyonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(Add_To_Cart.this,Buy_Now.class);
                startActivity(i);

            }
        });


        item_name=(TextView) findViewById(R.id.item_name);
        market_price=(TextView)findViewById(R.id.market_price);
        web_price=(TextView)findViewById(R.id.web_price);
        avai=(TextView)findViewById(R.id.avai);
        pro_img=(ImageView)findViewById(R.id.pro_img);
        spid=getIntent().getExtras().getString("pid");
        scid=getIntent().getExtras().getString("cid");
        siname=getIntent().getExtras().getString("piname");
        smprice=getIntent().getExtras().getString("pmprice");
        swprice=getIntent().getExtras().getString("pwprice");
        savai=getIntent().getExtras().getString("pavai");
        simg=getIntent().getExtras().getString("pimage");
        Picasso.with(getApplicationContext()).load(simg).into(pro_img);
        item_name.setText(siname);
        market_price.setText(smprice);
        web_price.setText(swprice);
        avai.setText(savai);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Add_To_Cart.this,List_View_Show.class));
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String ins="insert into customer(Product_Id,Category_Id,Item_Name,Market_Price,Web_Price,Availability,Product_Image)values('"+spid+"','"+scid+"','"+siname+"','"+smprice+"','"+swprice+"','"+savai+"','"+simg+"');";
                db.execSQL(ins);
                //add cart logic i++,add.setText(String.value(i))
                /*j++;
                add.setText(String.valueOf(j));*/
                Toast.makeText(Add_To_Cart.this, "Add To Cart sucessfully", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Add_To_Cart.this,List_View_Show.class);
               // i.putExtra("addcart",j);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                //startActivity(new Intent(Add_To_Cart.this,Getproduct.class));
            }
        });


    }
    protected void createdatabase()
    {
        db=openOrCreateDatabase("shopping", Context.MODE_PRIVATE,null);
        db.execSQL("create table if not exists customer (id integer primary key autoincrement not null,Product_Id varchar,Category_Id varchar,Item_Name varchar,Market_Price varchar,Web_Price varchar,Availability varchar,Product_Image varchar);");
    }
}
