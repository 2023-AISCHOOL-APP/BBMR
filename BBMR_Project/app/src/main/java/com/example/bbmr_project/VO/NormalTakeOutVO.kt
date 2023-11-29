package com.example.bbmr_project.VO

import android.os.Parcel
import android.os.Parcelable

class NormalTakeOutVO(val img: String = "", val name: String = "", val price: Int = 0) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(img)
        parcel.writeString(name)
        parcel.writeInt(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NormalTakeOutVO> {
        override fun createFromParcel(parcel: Parcel): NormalTakeOutVO {
            return NormalTakeOutVO(parcel)
        }

        override fun newArray(size: Int): Array<NormalTakeOutVO?> {
            return arrayOfNulls(size)
        }
    }
}