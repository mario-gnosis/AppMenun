package br.com.mario.appmenun;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    //Aqui começa o código do MP
    MediaPlayer mp = new MediaPlayer();
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
        if (id == R.id.Sobre) {
            AlertDialog alertDialog;
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Sobre");
            alertDialog.setMessage("Criado para teste da Aula");
            alertDialog.show();
        }
        if (id == R.id.Sair) {

            System.exit(0);
         }

        return super.onOptionsItemSelected(item);
    }

    // Aqui vai o código do botão do som do arquivo x
    public void jiraya(View View){

        try{

            mp.stop(); // Para todos os som Anteriores
            mp = MediaPlayer.create(this, R.raw.jiraya);
            mp.start(); // Iniica o som
            mp.setLooping(false); // Repetir ou não o som

            }
            finally {
        }
    }

    // Aqui vai o código do botão do som do arquivo x
    public void arquivoX(View View){

        try{
            mp.stop(); // Para todos os som Anteriores
            mp = MediaPlayer.create(this, R.raw.arquivo_x);
            mp.start(); // Iniica o som
            mp.setLooping(true); // Repetir ou não o som

        }
        finally {
        }

    }
    // Aqui vai o código do botão do som do arquivo x
    public void parar(View View){

        try{
            mp.stop(); // Para todos os som Anteriores
            }
        finally {
        }

    }
    //Essa classe serve para acionarmos os eventos de clique no menu.
    private class SlideMenuClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, longid){
            // display view for selected nav drawer item
            displayView(position);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
    //responsável por resolver e direcionar para qual fragmento a tela deverá ser redirecionada.
    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position)
        {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new MenuFragment();
                break;
            case 2:
                fragment = new NewsFragment();
                break;
            case 3:
                fragment = new EventsFragment();
                break;
            case 4:
                fragment = new PlacesFragment();
                break;
            case 5:
                fragment = new AdsFragment();
                break;

            default:
                break;
        }

        if (fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.frame_container, fragment).commit();
            }
            else
            {
                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            }

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        else
        {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
    public class MainActivity extends Activity {
        private DrawerLayout mDrawerLayout;
        private ListView mDrawerList;

        // slide menu items
        private String[] navMenuTitles;
        private TypedArray navMenuIcons;

        private ArrayList<NavDrawerItem> navDrawerItems;
        private NavDrawerListAdapter adapter;

        private static boolean alreadyOpen = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // load slide menu items
            navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

            // nav drawer icons from resources
            navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

            navDrawerItems = new ArrayList<NavDrawerItem>();

            // adding nav drawer items to array
            // Home
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
            // Find People
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
            // Photos
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
            // Communities, Will add a counter here
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
            // Pages
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
            // What's hot, We  will add a counter here
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));

            // Recycle the typed array
            navMenuIcons.recycle();

            mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

            // setting the nav drawer list adapter
            adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
            mDrawerList.setAdapter(adapter);

            if (savedInstanceState == null) {
                // on first time display view for first nav item
                displayView(0);
            }
        }

        @Override
        protected void onResume() {
            super.onResume();

            Log.i("Already Open", "" + alreadyOpen);

            if (!alreadyOpen) {
                mDrawerLayout.openDrawer(mDrawerList);
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
                }, 2000);

                alreadyOpen = true;
            }
        }


    }

    private class HomeFragment extends Fragment {
    }

    private class MenuFragment extends Fragment {
    }

    private class NewsFragment extends Fragment {
    }

    private class EventsFragment extends Fragment {
    }

    private class PlacesFragment extends Fragment {
    }

    private class AdsFragment extends Fragment {
    }
}
