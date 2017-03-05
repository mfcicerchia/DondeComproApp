package dondecompro.frsf.utn.dondecomproapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_one);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* dao = new DondeComproDAO(this);
        dao.open();*/
        //cursor  = dao.getAllProductos();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_one, menu);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gen_pedido) {

            Intent i= new Intent(this,GestionPedidoActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_ver_pedido) {
            Intent i= new Intent(this,ListarPedidosActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_buscar_super) {
            // Busqueda de supermercdos mas cercanos a esta posicion
            //-31.6191597,-60.704008,15z
            // -31.6348961,-60.7007596,15z /// rivadavia y bulevar con 15 de zoom
            //Uri gmmIntentUri = Uri.parse("geo:-31.6348961,-60.7007596?q=supermercados");
            //Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            //mapIntent.setPackage("com.google.android.apps.maps");
            //startActivity(mapIntent);
            // TODO: instanciar la clase UbicacionActivity para manejar las ubicaciones de los super
            Intent buscarSuperIntent= new Intent(this, UbicacionSupersActivity.class);
            startActivity(buscarSuperIntent);

            /// implementar el codigo acá

        }else if(id == R.id.nav_nuevo_producto){
            Intent i= new Intent(this,NuevoProductoActivity.class);
            startActivity(i);
        }

        else if (id == R.id.nav_enviar_pedido) {

        } else if (id == R.id.nav_ver_productos) {
            Intent i= new Intent(this,ProductosActivity.class);
            startActivity(i);
        }

        /*else if (id == R.id.nav_share) { //lo dejo comentado por si hay que agregar mas opciones de menú

        } else if (id == R.id.nav_send) {

        }*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /// metodos que hay que implementar de la interfaz  LocationListener
    @Override
    public void onLocationChanged(Location location) {
        // Este mŽtodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
        // debido a la detecci—n de un cambio de ubicacion

      /*  location.getLatitude();
        location.getLongitude();
        String Text = "Mi ubicaci—n actual es: " + "\n Lat = "
                + location.getLatitude() + "\n Long = " + location.getLongitude();
        //messageTextView.setText(Text);
        this.setLocation(location);*/
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
