package com.example.hungryhub

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hungryhub.databinding.ActivityLoginBinding
import com.example.hungryhub.databinding.ActivitySignBinding
import com.example.hungryhub.fragment.HomeFragment
import com.example.hungryhub.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import java.util.zip.Inflater

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private  val userName: String?= null
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient
    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        database = Firebase.database.reference

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.loginbutton.setOnClickListener {
            email = binding.etxtEmail.text.toString().trim()
            password = binding.etxtPassword.text.toString().trim()

            if (email.isEmpty() || password.isBlank()) {
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show()
            } else {
                createAccount()
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
            }
        }
        binding.dontbutton.setOnClickListener {
            val intent = Intent(this,SignActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoogle.setOnClickListener {
            val signInIntent: Intent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->

        if (result.resultCode == Activity.RESULT_OK) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful) {
                val account: GoogleSignInAccount? = task.result
                val credentials: AuthCredential =
                    GoogleAuthProvider.getCredential(account?.idToken, null)

                auth.signInWithCredential(credentials).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createAccount() {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            task ->

            if(task.isSuccessful){
                val user = auth.currentUser
                updateUI(user)
            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    task ->

                    if(task.isSuccessful){
                        saveUserData()
                        val  user = auth.currentUser
                        updateUI(user)
                    }else{
                        Toast.makeText(this,"Sign In Failed", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

    }

    private fun saveUserData() {
        email = binding.etxtEmail.text.toString().trim()
        password = binding.etxtPassword.text.toString().trim()

        val user = UserModel(userName, email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("user").child(userId).setValue(user)
    }

    override fun onStart() {
        super.onStart()

        val currentUser= auth.currentUser
        if(currentUser!=null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this,HomeFragment::class.java)
        startActivity(intent)
        finish()
    }
}