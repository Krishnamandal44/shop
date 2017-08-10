package administrator.example.com.drawshoopin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class mNavigationFragment extends Fragment {

    LinearLayout lnfashion,lnmy;
    LinearLayout lncart;
    LinearLayout lnlogout;
    public mNavigationFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mnavigation_fragment, container, false);
        lnfashion=(LinearLayout)rootView.findViewById(R.id.lnfashion);
        lncart=(LinearLayout)rootView.findViewById(R.id.lncart);
        lnmy=(LinearLayout)rootView.findViewById(R.id.lnmy);
        lnlogout=(LinearLayout)rootView.findViewById(R.id.lnlogout);

        lnlogout.setOnClickListener(new View.OnClickListener() {
            Boolean f=false;
            @Override
            public void onClick(View v) {
                String nameget=getPreference("username","");
                String passget=getPreference("pass","");
                if(nameget.equals("")||passget.equals(""))
                {
                    Toast.makeText(getActivity(),"Please first Login", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        f = true;
                        Toast.makeText(getActivity(), "Account was sucessfully Logout", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
lncart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent myIntent = new Intent(getActivity(), List_View_Show.class);
        getActivity().startActivity(myIntent);

    }
});
        lnmy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(getActivity(), Log_in.class);
                getActivity().startActivity(myIntent);
            }
        });
        lnfashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                Intent myIntent = new Intent(getActivity(), Maindrawer.class);
                getActivity().startActivity(myIntent);
            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public String getPreference(String key,String value)
    {
        String pref_value="";
        SharedPreferences sp1=this.getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);
        pref_value=sp1.getString(key, value);
        return  pref_value;
    }
}