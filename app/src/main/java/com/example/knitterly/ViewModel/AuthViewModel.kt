package com.example.knitterly.ViewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.knitterly.Repository.AuthRepo
import com.example.knitterly.model.Usermodel
import com.google.firebase.auth.FirebaseUser

class AuthViewModel( var repo: AuthRepo) : ViewModel()  {

    fun login (email:String, password:String,callback:(Boolean,String?)-> Unit){
        repo.login(email,password,callback)
    }
    fun signUp (email:String, password:String,callback:(Boolean,String?,String?)-> Unit){
        repo.signUp(email, password, callback)
    }

    fun addUserToDatabase(userId:String, userModel: Usermodel, callback:(Boolean, String?)-> Unit){
        repo.addUserToDatabase(userId,userModel, callback)
    }
    fun getCurrentUser () : FirebaseUser?{
        return repo.getCurrentUser()
    }
        fun forgetPassword(email:String,callback: (Boolean, String?) -> Unit){
        repo.forgetPassword(email,callback)
    }
    fun logout(callback:(Boolean,String?)-> Unit){
        repo.logout(callback)
    }

    private var _userData = MutableLiveData<Usermodel?>()
    var userData = MutableLiveData<Usermodel?>()
        get() = _userData

    fun fetchUserData(userId:String){
        repo.getUserFromFirebase(userId){
                userModel, success, message ->
            if(success){
                _userData.value = userModel
            }
        }
    }

    fun uploadImages(imageName: String, imageUri: Uri, callback: (Boolean, String?, String?) -> Unit){
        repo.uploadImages(imageName,imageUri,callback)
    }
    fun updateUser(userID:String,data: MutableMap<String,Any?>,callback: (Boolean, String?) -> Unit){
        repo.updateUser(userID,data,callback)
    }

}