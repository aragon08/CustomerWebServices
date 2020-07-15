package com.example.orion.customerswebservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lvCustomers)
    ListView lvCustomers;
    String URL_CUSTOMERS="http://ws.itcelaya.edu.mx:8080/curso/apirest/customers/listado";

    @OnClick(R.id.btnLoadCustomers)
    public void loadCustomers(){
        CustomersTask task= new CustomersTask(this, new AsyncResponse() {
            @Override
            public void finishProccess(String output) {
                //tvMessage.setText(output);
                //(Verifica que regrese algo)Toast.makeText(MainActivity.this,output,Toast.LENGTH_SHORT).show();
                generateCustomersList(output);
            }
        });
        task.execute(URL_CUSTOMERS);
    }

    private void generateCustomersList(String StrJson){
        List<String> listCustomers=new ArrayList<String>();
        try{
            JSONObject jsonResult= new JSONObject(StrJson);
            JSONArray jsonMainNode= jsonResult.getJSONArray("customers");
            for (int i=0; i<jsonMainNode.length();i++){
                JSONObject jsonChildNode= jsonMainNode.getJSONObject(i);
                String first_name=jsonChildNode.getString("first_name");
                String last_name=jsonChildNode.getString("last_name");
                listCustomers.add(first_name + " " + last_name);
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listCustomers);
            lvCustomers.setAdapter(adapter);
        }catch (JSONException e1){
            e1.printStackTrace();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
