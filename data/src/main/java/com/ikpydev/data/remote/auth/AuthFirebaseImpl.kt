package com.ikpydev.data.remote.auth

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.ikpydev.domain.model.ActivityHolder
import com.ikpydev.domain.model.InvalidCredentialsException
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class AuthFirebaseImpl(
    private val activityHolder: ActivityHolder
) : AuthFirebase {

    private val auth = FirebaseAuth.getInstance()
    lateinit var verificationId: String
    lateinit var token: ForceResendingToken


    override fun sendSmsCode(phone: String) = Completable.create {


        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {
                it.onError(e)
            }

            override fun onCodeSent(
                verifierId: String,
                token: ForceResendingToken
            ) {
                this@AuthFirebaseImpl.token = token
                this@AuthFirebaseImpl.verificationId = verifierId
                it.onComplete()
            }
        }
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setCallbacks(callbacks)
            .setActivity(activityHolder.activity)// OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun verify(code: String): Single<FirebaseUser> = Single.create {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful && isLoggedIn) {
                    it.onSuccess(auth.currentUser!!)

                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        it.onError(InvalidCredentialsException())
                    }
                    // Update UI
                }
            }
    }

    override val isLoggedIn: Boolean
        get() = auth.currentUser != null
    override val userId: String?
        get() = auth.currentUser?.uid
}