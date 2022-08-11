package pe.edu.idat.appfirebasekotlinidat

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pe.edu.idat.appfirebasekotlinidat.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth




        binding.btnlogin.setOnClickListener(this)
    }

    override fun onClick(vista: View) {
        when(vista.id){
            R.id.btnlogin -> autenticacionFirebase()

        }
    }

    private fun autenticacionFirebase() {
        if (binding.etemail.text?.isNotEmpty()!! &&
            binding.etpassword.text?.isNotEmpty()!!){

            auth.signInWithEmailAndPassword(
                binding.etemail.text.toString(), binding.etpassword.text.toString()
            ).addOnCompleteListener {
                if(it.isSuccessful){
                    ingresarAlApp(it.result?.user?.email ?: "",
                        "Firebase",
                        "","")
                }else{
                    alertaError()
                }
            }
        }
    }

    private fun ingresarAlApp(email: String, tipo: String,
                              nombre: String, url: String){
        val intent = Intent(this, HomeActivity::class.java)
            .apply {
                putExtra("email", email)
                putExtra("tipo", tipo)
                putExtra("nombre", nombre)
                putExtra("urlimg", url)
            }
        startActivity(intent)
        //pbautenticacion.visibility = View.GONE
    }
    private fun alertaError(){
        //pbautenticacion.visibility = View.GONE
        Toast.makeText(applicationContext,
            "Error en la autenticación.", Toast.LENGTH_LONG).show()
    }
}