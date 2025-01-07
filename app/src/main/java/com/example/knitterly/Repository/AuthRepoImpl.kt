package com.example.knitterly.Repository

import android.net.Uri
import com.example.knitterly.model.Usermodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class AuthRepoImpl: AuthRepo {
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var reference : DatabaseReference = database.reference.child("users")

//    var storage : FirebaseStorage = FirebaseStorage.getInstance()
//    var storageRef : StorageReference = storage.reference.child("users")


    override fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { res ->
                if (res.isSuccessful) {
                    callback(true,"Login Successful")

                } else {
                    callback(true,"Unable to login")
                }
            }
    }

    override fun signUp(email: String, password: String, callback: (Boolean, String?,String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ res ->
                if (res.isSuccessful) {
                    callback(true,"Registration Successful",auth.currentUser?.uid)
                } else {
                    callback(true,"Unable to register","")
                }
            }
    }

    override fun addUserToDatabase(userId:String,userModel: Usermodel, callback: (Boolean, String?) -> Unit) {
        userModel.id =userId
        reference.child(userId).setValue(userModel).addOnCompleteListener {res ->
            if (res.isSuccessful){
                callback(true, "User Registered")
            }
            else{
                callback(false,"unable to register user")
            }
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun getUserFromFirebase(
        userId: String,
        callback: (Usermodel?, Boolean, String?) -> Unit
    ) {
        reference.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val userModel = snapshot.getValue(Usermodel::class.java)
                    callback(userModel,true,"Fetch success")

                }
            }
            override fun onCancelled(error: DatabaseError) {
                callback(null,false,"${error.message}")
            }

        })
    }
    override fun forgetPassword(email: String, callback: (Boolean, String?) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { res ->
            if (res.isSuccessful) {
                callback(true, "Reset mail sent to $email")
            } else {
                callback(false, "Unable to reset password",)
            }
        }
    }


    override fun logout(callback: (Boolean, String?) -> Unit) {
        try {
            auth.signOut()
            callback(true,"Logout Success")

        }catch(e : Exception){
            callback(false,e.message)
        }
    }

    override fun updateUser(
        userID: String,
        data: MutableMap<String, Any?>,
        callback: (Boolean, String?) -> Unit
    ) {
        data.let { it->
            reference.child(userID).updateChildren(it).addOnCompleteListener {
                if(it.isSuccessful){
                    callback(true,"Successfully updated")
                }else{
                    callback(false,"Unable to update data")

                }
            }
        }

    }

    override fun uploadImages(
        imageName: String,
        imageUri: Uri,
        callback: (Boolean, String?, String?) -> Unit
    ) {
//        var imageReference = storageRef.child(imageName)
//        imageUri.let {url->
//            imageReference.putFile(url).addOnSuccessListener {
//                imageReference.downloadUrl.addOnSuccessListener {it->
//                    var imageUrl = it.toString()
//                    callback(true,imageUrl,"Upload success")
//                }
//            }.addOnFailureListener{
//                callback(false,"","unable to upload")
//            }
//        }
    }
}