package com.example.knitterly.model
import android.os.Parcel
import android.os.Parcelable
class Usermodel (
    var id : String = "",
    var name : String = "",
    var email: String = "",
    var number: String = "",
    var imageName: String = "",
    var imageUrl : String = ""
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(number)
        parcel.writeString(imageName)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usermodel> {
        override fun createFromParcel(parcel: Parcel): Usermodel {
            return Usermodel(parcel)
        }

        override fun newArray(size: Int): Array<Usermodel?> {
            return arrayOfNulls(size)
        }
    }
}