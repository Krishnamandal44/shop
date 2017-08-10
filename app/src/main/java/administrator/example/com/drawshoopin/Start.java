package administrator.example.com.drawshoopin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

/**
 * Created by raja on 26-04-2017.
 */
public class Start extends AppCompatActivity {
   ImageView img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        img2=(ImageView)findViewById(R.id.img2);
        GlideDrawableImageViewTarget imageViewTarget= new GlideDrawableImageViewTarget(img2);
        Glide.with(this).load(R.drawable.b).into(imageViewTarget);
      Thread mythread= new Thread()
      {
          @Override

          public void run() {
              try {
                  sleep(5000);
                  Intent intent = new Intent(getApplicationContext(),Maindrawer.class);
                  startActivity(intent);
                  finish();

              } catch (InterruptedException e) {
                  e.printStackTrace();
              }

          }
      };
        mythread.start();

    }
}

