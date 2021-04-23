package edu.itesm.proyecto_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


data class Ingreso (val name: String?, val type: String?){
    constructor() : this(" ", "")

}

class AgregarIngresoActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_ingreso)
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("ingresos")
    }

    public fun addIngreso(view: View){
        val nombre = findViewById<EditText>(R.id.nombre).text
        val tipo = findViewById<EditText>(R.id.tipo).text
        if(nombre.isNotEmpty() && nombre.isNotBlank() && tipo.isNotEmpty() && tipo.isNotBlank()){
            val ingreso = Ingreso(nombre.toString(), tipo.toString())
            val id = reference.push().key
            reference.child(id!!).setValue(ingreso)
        }else{
            Toast.makeText(applicationContext, "error en nombre o cantidad!", Toast.LENGTH_LONG).show()
        }
    }

    fun cancelar(view: View) {
        val intento = Intent(this, MainActivity::class.java)
        startActivity(intento)
        finish()
    }
}