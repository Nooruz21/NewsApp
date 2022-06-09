package com.example.news.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.news.Prefs
import com.example.news.R
import com.example.news.databinding.FragmentAuthBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class AuthFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentAuthBinding
    private lateinit var callbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var user:FirebaseUser
    private lateinit var prefs: Prefs


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnContinue.setOnClickListener{
            requestSMS()
            init()
            initListener()
        }
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.e("Auth", "onVerificationCompleted")
                signIn(credential)

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.e("Auth", "onVerificationFailed:${p0.message}")
            }

            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                super.onCodeAutoRetrievalTimeOut(p0)
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                binding.next.setOnClickListener {
                    unitcode(s)
                }
            }

            private fun unitcode(s: String) {
                val code: String = binding.editCode.text.toString().trim()
                if (TextUtils.isEmpty(code)) {
                    binding.editCode.error = "Поле пустое"
                } else {
                    val credential = PhoneAuthProvider.getCredential(s, code)
                    requestSMS(credential)
                }
            }
        }
    }

    private fun initListener() {
        binding.btnContinue.setOnClickListener {
            val phone: String = binding.editPhone.text.toString().trim()
            if (TextUtils.isEmpty(phone)) {
                binding.editPhone.error = "Поле пустое"
            } else {
                requestSMS(phone)
                Log.e("Auth", "initListener: $phone")
                binding.btnContinue.visibility = View.GONE
                binding.next.visibility = View.VISIBLE
            }
        }
    }


    private fun init() {
       auth= FirebaseAuth.getInstance()
    }

    private fun signIn(credential: PhoneAuthCredential) {
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    user = task.getResult().getUser()
                    AuthFragment.prefs.saveRegisterFragment()
                    Log.e("TAG", "onComplete: ")
                    findNavController(requireView()).navigate(R.id.boardFragment)
                }else{
                    Toast.makeText(requireContext(),"Ошибка авторизации ${it.exception?.message}",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun requestSMS() {
        val phone = binding.editPhone.text.toString()
        if (phone.isEmpty())return


        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phone)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

}