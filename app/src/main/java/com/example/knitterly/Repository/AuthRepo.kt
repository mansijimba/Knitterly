package com.example.knitterly.Repository

import android.net.Uri
import com.example.knitterly.model.Usermodel
import com.google.firebase.auth.FirebaseUser

interface AuthRepo {
    fun login (email:String, password:String,callback:(Boolean,String?)-> Unit)
    fun signUp (email:String, password:String,callback:(Boolean,String?,String?)-> Unit)
    fun forgetPassword(email:String,callback: (Boolean, String?) -> Unit)

    fun addUserToDatabase(userId:String, userModel: Usermodel, callback:(Boolean, String?)-> Unit)

    fun getUserFromFirebase(userId:String,callback: (Usermodel?,Boolean, String?) -> Unit)
    fun getCurrentUser () : FirebaseUser?
    fun logout(callback:(Boolean,String?)-> Unit)
    fun updateUser(userID:String,data: MutableMap<String,Any?>,callback: (Boolean, String?) -> Unit)

    fun uploadImages(imageName: String, imageUri: Uri, callback: (Boolean, String?, String?) -> Unit)
}