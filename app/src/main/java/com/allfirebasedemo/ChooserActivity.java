package com.allfirebasedemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.allfirebasedemo.analyticpackage.AnalticActivity;
import com.allfirebasedemo.cloudmessagingpackage.MessagingActivity;
import com.allfirebasedemo.realdbpackage.RealDatabaseActivity;
import com.allfirebasedemo.storagepackage.StorageActivity;

/**
 * Created by Sanwal Singh on 26/12/16.
 */

public class ChooserActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private static final Class[] CLASSES = new Class[]{
            GoogleSignInActivity.class,
            EmailSignINActivity.class,
            AnonymousAuthActivity.class,
            RealDatabaseActivity.class,
            StorageActivity.class,
            MessagingActivity.class,
            AnalticActivity.class
    };

    private static final int[] DESCRIPTION_IDS = new int[]{
            R.string.desc_google_sign_in,
            R.string.desc_emailpassword,
            R.string.desc_anonymous_auth,
            R.string.desc_realtime_db,
            R.string.desc_storage,
            R.string.desc_message,
            R.string.desc_analytic
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        // Set up ListView and Adapter
        ListView listView = (ListView) findViewById(R.id.list_view);

        MyArrayAdapter adapter = new MyArrayAdapter(this, android.R.layout.simple_list_item_2, CLASSES);
        adapter.setDescriptionIds(DESCRIPTION_IDS);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Class clicked = CLASSES[position];
        startActivity(new Intent(this, clicked));
    }

    public static class MyArrayAdapter extends ArrayAdapter<Class> {

        private Context mContext;
        private Class[] mClasses;
        private int[] mDescriptionIds;

        public MyArrayAdapter(Context context, int resource, Class[] objects) {
            super(context, resource, objects);

            mContext = context;
            mClasses = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(android.R.layout.simple_list_item_2, null);
            }

            ((TextView) view.findViewById(android.R.id.text1)).setText(mClasses[position].getSimpleName());
            ((TextView) view.findViewById(android.R.id.text2)).setText(mDescriptionIds[position]);

            return view;
        }

        public void setDescriptionIds(int[] descriptionIds) {
            mDescriptionIds = descriptionIds;
        }
    }
}
