package edu.itesm.proyecto_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.*


data class Ingreso (val name: String?, val type: String?){
    constructor() : this(" ", "")

}

class AgregarIngresoActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var bundle: Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_ingreso)
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("ingresos")

        analytics = FirebaseAnalytics.getInstance(this)
        bundle = Bundle()
    }

    public fun addIngreso(view: View){
        val nombre = findViewById<EditText>(R.id.nombre).text
        val tipo = findViewById<EditText>(R.id.tipo).text
        if(nombre.isNotEmpty() && nombre.isNotBlank() && tipo.isNotEmpty() && tipo.isNotBlank()){
            val ingreso = Ingreso(nombre.toString(), tipo.toString())
            val id = reference.push().key
            reference.child(id!!).setValue(ingreso)

            bundle.putString("AgregarIngreso_main", "added_Ingreso")
            analytics.logEvent("Ingreso_main", bundle)
        }else{
            Toast.makeText(applicationContext, "error en nombre o cantidad!", Toast.LENGTH_LONG).show()
        }
    }

    fun cancelar(view: View) {
        val intento = Intent(this, MainActivity::class.java)
        startActivity(intento)
        finish()
    }

    public fun getIngreso(view: View){
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var listaIngresos = ArrayList<Ingreso>()
                for (pokemon in snapshot.children){
                    var objeto = pokemon.getValue(Ingreso::class.java)
                    listaIngresos.add(objeto as Ingreso)
                }
                Log.i("Ingresos", listaIngresos.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}