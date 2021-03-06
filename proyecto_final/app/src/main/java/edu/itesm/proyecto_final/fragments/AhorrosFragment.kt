package edu.itesm.proyecto_final.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.firebase.database.*
import edu.itesm.proyecto_final.Ahorro
import edu.itesm.proyecto_final.R
import kotlinx.android.synthetic.main.fragment_ahorros.*
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AhorrosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AhorrosFragment : Fragment(), OnChartValueSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val yEntre = ArrayList<PieEntry>()
    private val xEntre = ArrayList<String>()
    lateinit var pieChart: PieChart

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_ahorros, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pieChart = pieChartAhorros
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("ahorros")
        getAhorro()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AhorrosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AhorrosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    public fun getAhorro(){
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pieChart.invalidate()
                pieChart.clear()
                xEntre.clear()
                yEntre.clear()

                for (pokemon in snapshot.children){
                    var objeto = pokemon.getValue(Ahorro::class.java)
                    if (objeto != null) {

                        var x :Float=  objeto.type.toString().toFloat()
                        xEntre.add(objeto.name.toString())
                        yEntre.add(PieEntry(x, objeto.name.toString()))
                    }

                }
                addDataSet()


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    fun addDataSet(){

        Log.i("Ahorros", xEntre.toString())
        /*
        for (i in firebaseXEntry){
            xEntre.add(i)
        }

        var count = 0

        for (i in firebaseYEntry){

            yEntre.add(PieEntry(23.5f, xEntre[count]))
            count += 1
        }


        */

        //Creates data set
        val pieDataSet = PieDataSet(yEntre, "Datos", )

        pieDataSet.sliceSpace = 2F
        pieDataSet.valueTextSize = 12F

        //Add color to dataset
        val color = ArrayList<Int>()
        color.add(Color.BLUE)
        color.add(Color.RED)
        color.add(Color.YELLOW)
        color.add(Color.CYAN)

        pieDataSet.setColors(color)

        pieChart.setTransparentCircleAlpha(0)
        pieChart.holeRadius = 70f

        //add legend
        //val legend = pieChart.legend
        //legend.form = Legend.LegendForm.CIRCLE

        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.invalidate()

        pieChart.setOnChartValueSelectedListener(this)
    }
    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e != null) {
            pieChart.centerText = xEntre[h?.x?.roundToInt()!!] + "\n" +e.y.toString()
        }


    }

    override fun onNothingSelected() {

    }
}