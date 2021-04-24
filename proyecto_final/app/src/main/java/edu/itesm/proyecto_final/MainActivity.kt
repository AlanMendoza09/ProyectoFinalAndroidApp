package edu.itesm.proyecto_final

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.itesm.proyecto_final.fragments.ActividadesFragment
import edu.itesm.proyecto_final.fragments.AhorrosFragment
import edu.itesm.proyecto_final.fragments.AjustesFragment
import edu.itesm.proyecto_final.fragments.GastosFragment


class MainActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    private lateinit var analytics: FirebaseAnalytics
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gastosFragment = GastosFragment()
        val ahorrosFragment = AhorrosFragment()
        val actividadesFragment = ActividadesFragment()
        val ajustesFragment = AjustesFragment()

        makeCurrentFragment(gastosFragment)
        val bottom_navigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

       bottom_navigation.setOnNavigationItemSelectedListener{
           when (it.itemId){
               R.id.ic_gastos -> makeCurrentFragment(gastosFragment)
               R.id.ic_ahorros -> makeCurrentFragment(ahorrosFragment)
               R.id.ic_actividad -> makeCurrentFragment(actividadesFragment)
               R.id.ic_ajustes -> makeCurrentFragment(ajustesFragment)
           }
           true
       }
        // Write a message to the database
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("ahorros")

        analytics = FirebaseAnalytics.getInstance(this)
        bundle = Bundle()
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

    fun agregarGasto(view: View) {
        val intento = Intent(this, AgregarIngresoActivity::class.java)
        startActivity(intento)
        finish()
    }

    fun agregarAhorro(view: View){
        val intento = Intent(this, AgregarAhorro::class.java)
        startActivity(intento)
        finish()
    }


}