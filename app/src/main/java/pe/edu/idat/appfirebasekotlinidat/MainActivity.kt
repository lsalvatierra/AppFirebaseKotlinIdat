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

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                .setSupported(true)
                .build())
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.default_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(true)
            .build()


        binding.btnlogin.setOnClickListener(this)
    }

    override fun onClick(vista: View) {
        when(vista.id){
            R.id.btnlogin -> autenticacionFirebase()
            R.id.btnlogingoogle -> autenticacionFirebaseGoole()
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

    private fun autenticacionFirebaseGoole() {
        //pbautenticacion.visibility = View.VISIBLE

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender, REQ_ONE_TAP,
                        null, 0, 0, 0, null)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("LoginGoogle", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(this) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d("LoginGoogle", e.localizedMessage)
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 2){
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(data)
                val idToken = credential.googleIdToken
                when {
                    idToken != null -> {
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
                        Log.d("LoginGoogle", "Got ID token.")
                    }
                    else -> {
                        // Shouldn't happen.
                        Log.d("LoginGoogle", "No ID token!")
                    }
                }
            } catch (e: ApiException) {

            }
    }

    }


}