package com.example.lallu.piccassaassg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.BitSet;

public class MainActivity extends AppCompatActivity {
ListView list;
Button mybtn;
    //AsyncHttpClient client;
    AsyncHttpClient client;
    RequestParams params;
    JSONObject jobject;
    JSONArray jarray;
    TextView details;
    ImageView vehicle;
    public String url="http://sicsglobal.co.in/agroapp/Json/Machinerys.aspx?";
    ArrayList<String>idd;
    ArrayList<String>nam;
    ArrayList<String> typp;
    ArrayList<String>dese;
    ArrayList<String>pric;
    ArrayList<String>vdeo;
    ArrayList<String>imag;
    ArrayList<String>cont;
    ArrayList<String>dataitms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=(ListView)findViewById(R.id.listitem);
        mybtn=(Button)findViewById(R.id.fetchbtn);
        idd=new ArrayList<String>();
        nam=new ArrayList<String>();
        typp=new ArrayList<String>();
        dese=new ArrayList<String>();
        cont=new ArrayList<String>();
        pric=new ArrayList<String>();
        vdeo=new ArrayList<String>();
        imag=new ArrayList<String>();
        client=new AsyncHttpClient();
        params=new RequestParams();
        dataitms=new ArrayList<String>();
        mybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Outside Async",url);
                client.get(url,params,new AsyncHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                        Log.e("Content",content);
                        try {
                            Log.e("Inside Asunc", String.valueOf(params));
                            jobject=new JSONObject(content);
                            String m=jobject.optString("Machinerys");
                            jarray=new JSONArray(m);
                            for (int i=0;i<jarray.length();i++){
                                JSONObject obj=jarray.getJSONObject(i);
                                String id=obj.getString("MachineId");
                                System.out.println(id);
                                idd.add("ID:"+id);

                                String name=obj.getString("Name");
                                System.out.println(name);
                                nam.add(name);

                                String type=obj.getString("Type");
                                System.out.println(type);
                                typp.add("Type :"+type);

                                String description=obj.getString("Description");
                                System.out.println(description);
                                dese.add("Description :"+description);

                                String usage=obj.getString("UsageProcedure");
                                System.out.println("Usage"+usage);
                                cont.add("Usage Procedure : "+usage);

                                String price=obj.getString("Price");
                                System.out.println(price);
                                pric.add("Price :"+price);

                                String video=obj.getString("Video");
                                System.out.println(video);
                                vdeo.add("Video :"+video);

                                String image=obj.getString("Image");
                                System.out.println(image);
                                imag.add(image);
                                Log.e("Image===","Image :http://sicsglobal.co.in/agroapp/Admin/VideoFiles/"+image);
                                dataitms.add("ID:"+id+"\n"+"Vehicle Name :"+name+"\n"+"Type :"+type+"\n"+"Description :"+description+"\n"+"Usage Procedure : "+usage+"\n"+"Price :"+price+"\n"+"Video :"+video+"\n");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Picasso.with()
                        adapt ad=new adapt();
                        list.setAdapter(ad);
                    }

                });
            }
        });



        // Adapter


    }
    class adapt extends BaseAdapter{
        LayoutInflater inflater;

        @Override
        public int getCount() {
            return idd.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           // Picasso.with(MainActivity.this).load("http://sicsglobal.co.in/agroapp/Admin/VideoFiles/"+offer_)
            inflater=(LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_items,null);
            TextView tv=(TextView)convertView.findViewById(R.id.vehiclehead);
            tv.setText(nam.get(position));

            details=(TextView)convertView.findViewById(R.id.txtview);
            ImageView immagev= (ImageView) convertView.findViewById(R.id.myimg);
            details.setText(dataitms.get(position));
            Picasso.with(MainActivity.this).load("http://sicsglobal.co.in/agroapp/Admin/VideoFiles/"+imag.get(position))
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(immagev);


            return convertView;

        }
    }
}
