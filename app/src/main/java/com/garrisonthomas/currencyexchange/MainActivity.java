package com.garrisonthomas.currencyexchange;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.URI;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void onConvert(View view) {

        new RetrieveRate().execute("");

        EditText et = (EditText) findViewById(R.id.et_amount_to_convert);
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);

        et.setText("");

    }

    String fromCurrency = null;
    String toCurrency = null;

    private class RetrieveRate extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            String result;

            try {
                URI website = new URI("http://rate-exchange.appspot.com/currency?from="+fromCurrency+"&to="+toCurrency);

            HttpGet request = new HttpGet();
            request.setURI(website);
            HttpResponse response = client.execute(request);
            result = EntityUtils.toString(response.getEntity());
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject currencyObject = new JSONObject(result);
                String rate = currencyObject.getString("rate");
                EditText et = (EditText) findViewById(R.id.et_amount_to_convert);
                String fromAmount = et.getText().toString();
                Double total = Double.parseDouble(rate)*Double.parseDouble(fromAmount);
                TextView tv_return = (TextView) findViewById(R.id.tv_return);
                tv_return.setText(total+"");
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
