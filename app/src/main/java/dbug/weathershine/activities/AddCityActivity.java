package dbug.weathershine.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import dbug.weathershine.R;

import static dbug.weathershine.providers.CitySuggestionProvider.CONTENT_URI;

public class AddCityActivity extends AppCompatActivity {

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        handleIntent(getIntent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_city_menu, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.add_city_action).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_city_action:
                //onSearchRequested();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Log.e("HandleIntent", intent + " " + intent.getAction() + " Intent.action>VIEW " + Intent.ACTION_VIEW);
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Log.e("ActionView", "Action: " + intent.getStringExtra(SearchManager.QUERY) + intent.getData());
            Uri uri = intent.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
        }
        else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            cursor = getContentResolver().query(CONTENT_URI, null, null, new String[]{query}, null);
            showResults();
        }
    }

    private void showResults() {
        if (cursor == null) return;

    }

    @Override
    protected void onDestroy() {
        if (cursor != null) cursor.close();
        super.onDestroy();
    }
}
