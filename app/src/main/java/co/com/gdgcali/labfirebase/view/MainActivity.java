package co.com.gdgcali.labfirebase.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.com.gdgcali.labfirebase.R;
import co.com.gdgcali.labfirebase.model.Mensaje;
import co.com.gdgcali.labfirebase.view.adapter.RecentMenssageAdapter;


public class MainActivity extends Activity implements ChildEventListener {

    // Variables Privadas
    private final String URL_FIREBASE = "https://radiant-heat-5907.firebaseio.com/";
    private RecentMenssageAdapter adapter;
    private static final String TAG = MainActivity.class.getSimpleName();
    private Firebase objFirebase;
    private final String NOMBRE = "autor";
    private final String MENSAJE = "mensaje";
    private LayoutInflater mLayoutInflater;
    private ViewGroup chatMensajesLayout;
    private ScrollView mScrollView;
    private TextView sendButton;
    private TextView txtMensaje;
    private int colorActual = 1;

    // Publicas
    public ListView listvw;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mLayoutInflater = LayoutInflater.from(this);

        chatMensajesLayout = (ViewGroup) findViewById(R.id.activity_chat_message_layout);
        mScrollView = (ScrollView) findViewById(R.id.activity_chat_scroll_view);
        sendButton = (TextView) findViewById(R.id.chat_sdk_btn_chat_send_message);
        txtMensaje = (TextView) findViewById(R.id.message_to_send);

        InicializarFirebase();

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EnviarMensaje();
            }
        });

    }

    private void InicializarFirebase(){
        try {

           objFirebase = new Firebase(URL_FIREBASE);
           objFirebase.addChildEventListener(this);
        }catch (FirebaseException ex){
            Log.e(TAG, ex.getMessage());
        }
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

    private void scrollToBottom() {

        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private void EnviarMensaje(){

        String mensaje = txtMensaje.getText().toString().trim();

        if (mensaje.length() > 0){

            Map<String, Object> firebaseMessage = new HashMap<>();
            firebaseMessage.put(NOMBRE, "GDG Cali");
            firebaseMessage.put(MENSAJE, mensaje);

            try {
                objFirebase.push().setValue(firebaseMessage);
            } catch (FirebaseException e) {
                Log.e(TAG, "Error enviando mensajes", e);
                Toast.makeText(this, "Error enviando mensajes", Toast.LENGTH_SHORT).show();
            }

            txtMensaje.setText("");
        }

    }


    @SuppressWarnings("unchecked")
    @Override
    public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
        Log.d(TAG, "onChildAdded()");

        Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
        String autor = map.get(NOMBRE).toString();
        String mensaje = map.get(MENSAJE).toString();

        SpannableString span = new SpannableString(autor.toUpperCase());
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.username)) {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.username));
            }
        }, 0, span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView chatView = (TextView) mLayoutInflater.inflate(R.layout.item_recent_message, null);
        chatView.setText(span + " -->  " + mensaje);

        if(colorActual == 1){
            chatView.setBackgroundColor(R.color.line_mensaje);
            colorActual = 2;
        }
        else{
            chatView.setBackgroundColor(Color.LTGRAY);
            colorActual = 1;
        }


        chatMensajesLayout.addView(chatView);

        scrollToBottom();
    }

    @Override
    public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
        Log.d(TAG, "onChildChanged()");
    }

    @Override
    public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
        Log.d(TAG, "onChildMoved()");
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }

    @Override
    public void onChildRemoved(DataSnapshot snapshot) {
        Log.d(TAG, "onChildRemoved()");
    }
}
