package co.com.gdgcali.labfirebase.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.com.gdgcali.labfirebase.R;
import co.com.gdgcali.labfirebase.model.Mensaje;
import co.com.gdgcali.labfirebase.view.adapter.RecentMenssageAdapter;


public class MainActivity extends Activity {


    public static Firebase objFirebase = new Firebase("https://radiant-heat-5907.firebaseio.com/");
    public ListView listvw;
    private List<Mensaje> listaMensajes = new ArrayList<Mensaje>();
    RecentMenssageAdapter adapter;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        listvw = (ListView) findViewById(R.id.lstMensajes);
        objFirebase.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println(dataSnapshot.getValue());
                Map<String, String> mapMensaje = (Map<String, String>) dataSnapshot.getValue();
                String autor = "", fecha = "", mensaje = "";
                if (mapMensaje.size() > 0) {
                    if (!mapMensaje.isEmpty()) {

                        for (Map.Entry<String, String> entry : mapMensaje.entrySet()) {

                            if (entry.getKey().equals("autor")) {
                                autor = entry.getValue();
                            }
                            if (entry.getKey().equals("mensaje")) {
                                mensaje = entry.getValue();
                            }
                            if (entry.getKey().equals("fecha")) {
                                fecha = entry.getValue();
                            }

                        }
                        listaMensajes.add(new Mensaje(autor, mensaje, fecha));

                    }

                }
                adapter = new RecentMenssageAdapter(listaMensajes, R.layout.item_recent_message, context);
                listvw.setAdapter(adapter);
                listvw.refreshDrawableState();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
    }

    private void llenarLista(String[] mensajes) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mensajes);
        listvw.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
