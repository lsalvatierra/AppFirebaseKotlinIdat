package pe.edu.idat.appfirebasekotlinidat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import pe.edu.idat.appfirebasekotlinidat.adapter.PersonaAdapter
import pe.edu.idat.appfirebasekotlinidat.databinding.ActivityMainBinding
import pe.edu.idat.appfirebasekotlinidat.databinding.ActivityPersonasBinding
import pe.edu.idat.appfirebasekotlinidat.model.Persona

class PersonasActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPersonasBinding
    private lateinit var firestore : FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val lstpersonas : ArrayList<Persona> = ArrayList()
        binding.rvfirestore.layoutManager = LinearLayoutManager(this)
        firestore = FirebaseFirestore.getInstance()
        firestore.collection("Persona")
            .addSnapshotListener { snapshots, error ->
                if(error != null){
                    Log.e("ErrorListFirestore", error.message.toString())
                }
                for(doc in snapshots!!.documentChanges){
                    if(doc.type == DocumentChange.Type.ADDED){
                        lstpersonas.add(Persona(
                            doc.document.data["nombre"].toString(),
                            doc.document.data["apellido"].toString(),
                            doc.document.data["edad"].toString().toInt(),
                        ))
                    }
                }
                binding.rvfirestore.adapter = PersonaAdapter(lstpersonas)
            }
    }
}