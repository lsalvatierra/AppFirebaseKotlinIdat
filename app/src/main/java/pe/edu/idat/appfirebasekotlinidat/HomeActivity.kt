package pe.edu.idat.appfirebasekotlinidat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import pe.edu.idat.appfirebasekotlinidat.databinding.ActivityHomeBinding
import pe.edu.idat.appfirebasekotlinidat.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mostrarInformacionCuenta()
        binding.btnverdatos.setOnClickListener(this);
    }

    fun mostrarInformacionCuenta(){
        val bundle: Bundle? = intent.extras
        val email : String? = bundle?.getString("email")
        binding.tvemail.setText(email)
    }

    override fun onClick(p0: View?) {
        startActivity(Intent(applicationContext, PersonasActivity::class.java))
    }
}