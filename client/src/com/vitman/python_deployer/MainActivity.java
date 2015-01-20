package com.vitman.python_deployer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;


public class MainActivity extends Activity implements TextWatcher {

    // URL to get result from pythons script
    private static final String GOOGLE_SERVER = "http://server-python-deployer.appspot.com/";

    private static final String ERROR = "Server Error";
    private static final String DEFAULT = "";

    private ProgressDialog pDialog;
    private EditText mEditor;
    private String mScript;
    private String mResult;

    private JSONObject mJsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditor = (EditText) findViewById(R.id.editor);
        mEditor.addTextChangedListener(MainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case (R.id.action_submit):
                //Saving pythons script to file
                saveScriptToFile();
                // Calling async task to get json
                new GetResultFromServer().execute();
                return true;
            case (R.id.action_new):
                //Cleaning edit text
                mEditor.setText(DEFAULT);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        setColorWord(s);
    }

    //Saving text to file
    public File saveScriptToFile() {
        mScript = mEditor.getText().toString();
        File file = new File(Environment.getExternalStorageDirectory(), "script.py");

        try {
            if (file.exists()) {
                file.delete();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(mScript);
            writer.newLine();
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "File has not been saved",
                    Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(MainActivity.this, "File's been saved",
                Toast.LENGTH_SHORT).show();

        return file;
    }

    private class GetResultFromServer extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            // Creating server handler class instance
            ServerHandler serverHandler = new ServerHandler();

            // Making a request to url and getting response
            String jsonString = serverHandler.makeServerRequest(GOOGLE_SERVER, mScript);

            if (jsonString != null) {
                try {
                    mJsonObject = new JSONObject(jsonString);
                    mResult = serverHandler.parseJSONObject(mJsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    mResult = ERROR;
                }
            } else {
                Toast.makeText(MainActivity.this,
                        "Couldn't get any result from the server",
                        Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            //Showing stats and result
            mEditor.setText(mResult);
        }
    }

    //set up color to reserved word in text editor
    public Spannable setColorWord(Editable text) {

        for (int index = 0; index < text.length(); index++) {

            for (Map.Entry<String, Integer> entry :
                    ReservedWords.RESERVED_WORDS.entrySet()) {
                int reservedWordLength = entry.getKey().length();
                if (index + reservedWordLength > text.length()) {
                    continue;
                }

                String string = text.subSequence(index,
                        index + reservedWordLength).toString();
                string = string.trim();

                if (string.equals(entry.getKey().trim())) {
                    text.setSpan(new ForegroundColorSpan(entry.getValue()),
                            index, index + reservedWordLength,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
                }
            }
        }
        return text;
    }
}
