package com.example.bbmr_project.VO

import android.os.Parcel
import android.os.Parcelable

class Senior_TakeOutVO (val simg : String = "", val  sname : String = "", val sprice : Int = 0, ) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(simg)
        parcel.writeString(sname)
        parcel.writeInt(sprice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Senior_TakeOutVO> {
        override fun createFromParcel(parcel: Parcel): Senior_TakeOutVO {
            return Senior_TakeOutVO(parcel)
        }

        override fun newArray(size: Int): Array<Senior_TakeOutVO?> {
            return arrayOfNulls(size)
        }
    }
}