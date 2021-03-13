package edu.itesm.proyecto_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.itesm.proyecto_final.fragments.ActividadesFragment
import edu.itesm.proyecto_final.fragments.AhorrosFragment
import edu.itesm.proyecto_final.fragments.AjustesFragment
import edu.itesm.proyecto_final.fragments.GastosFragment

class MainActivity : AppCompatActivity() {
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
               R.id.ic_gastos->makeCurrentFragment(gastosFragment)
               R.id.ic_ahorros->makeCurrentFragment(ahorrosFragment)
               R.id.ic_actividad->makeCurrentFragment(actividadesFragment)
               R.id.ic_ajustes->makeCurrentFragment(ajustesFragment)
           }
           true
       }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

}