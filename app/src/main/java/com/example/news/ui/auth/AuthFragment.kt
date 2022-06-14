package com.example.news.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.news.MainActivity
import com.example.news.Prefs
import com.example.news.R
import com.example.news.databinding.FragmentAuthBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }


    private lateinit var mPhoneNumber:String
    private lateinit var mCallback:PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onStart() {
        super.onStart()
        (activity as AuthFragment).title = phoneNumber
        register_input_code.addTextChangedListener(AppTextWatcher {
            val string = register_input_code.text.toString()
            if (string.length == 6) {
                enterCode()
            }
            mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            showToast("Добро пожаловать")
                            (activity as RegisterActivity).replaceActivity(MainActivity())
                        } else showToast(task.exception?.message.toString())
                    }
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    showToast(p0.message.toString())
                }

                override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                    replaceFragment(EnterCodeFragment(mPhoneNumber,id))
                }
            }
            register_btn_next.setOnClickListener { sendCode() }
        }

                private fun sendCode() {
            if (register_input_phone_number.text.toString().isEmpty()){
                showToast(getString(R.string.register_toast_enter_phone))
            } else {
                authUser()
            }
        }

                private fun authUser() {
            mPhoneNumber = register_input_phone_number.text.toString()
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumber,
                60,
                TimeUnit.SECONDS,
                activity as RegisterActivity,
                mCallback
            )

        }


                private fun enterCode() {
            val code = register_input_code.text.toString()
            val credential = PhoneAuthProvider.getCredential(id, code)
            AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    showToast("Добро пожаловать")
                    (activity as RegisterActivity).replaceActivity(MainActivity())
                } else showToast(task.exception?.message.toString())
            }
        }
    }
}

}