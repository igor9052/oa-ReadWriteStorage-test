package ua.com.igorka.oa.android.readwritestorage;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;


public class MainActivity extends ActionBarActivity {

    public static final String CREATED_FOLDER_NAME = "igorka";
    public static final String TEXT_TO_FILE = "Hello everyone!!!";
    private String activityName = getClass().getSimpleName();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!isExternalStorageWritable()){
            return;
        }
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Log.i(activityName, path.toString());
        File file = new File(path, CREATED_FOLDER_NAME);
        file.mkdirs();
        file = new File(file, "readme.txt");
        if (!file.exists()) {
            writeToFile(file, TEXT_TO_FILE);
        }
        String str = readFromFile(file);
        TextView textView = (TextView)findViewById(R.id.greeting_text);
        textView.setText(str);
    }

    private String readFromFile(File file) {
        String result = null;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            result = bufferedReader.readLine();
            bufferedReader.close();
            fileReader.close();
        }
        catch (IOException e) {

        }
        return result;
    }

    private boolean writeToFile(File file, String text) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            bufferedWriter.close();
            fileWriter.close();
            return true;

        } catch (IOException e) {
            // Unable to create file, likely because external storage is
            // not currently mounted.
            Log.w("ExternalStorage", "Error writing " + file, e);
            return false;
        }
    }


    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        Log.i(activityName, state);
        return Environment.MEDIA_MOUNTED.equals(state) ? true : false;
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
