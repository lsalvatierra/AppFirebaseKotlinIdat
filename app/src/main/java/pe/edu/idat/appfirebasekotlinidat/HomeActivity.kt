package pe.edu.idat.appfirebasekotlinidat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.edu.idat.appfirebasekotlinidat.databinding.ActivityHomeBinding
import pe.edu.idat.appfirebasekotlinidat.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mostrarInformacionCuenta()
    }

    fun mostrarInformacionCuenta(){
        val bundle: Bundle? = intent.extras
        val email : String? = bundle?.getString("email")
        val nombre : String? = bundle?.getString("nombre")
        binding.tvemail.setText(email)
        binding.tvnombres.setText(nombre)

    }
}