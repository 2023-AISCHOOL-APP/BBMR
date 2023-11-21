import android.os.Parcel
import android.os.Parcelable

data class NormalSelectedMenuInfo(
    val menuImg: Int,
    val name: String?,
    val price: String?,
    val temperature: String?,
    var tvCount: Int,
    val tvCount1: Int,
    val tvCount2: Int,
    val tvCount3: Int,
    val tvCount4: Int,
    val optionTvCount: Int,
    val totalCost: Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        0,
        0
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(menuImg)
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(temperature)
        parcel.writeInt(tvCount)
        parcel.writeInt(tvCount1)
        parcel.writeInt(tvCount2)
        parcel.writeInt(tvCount3)
        parcel.writeInt(tvCount4)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NormalSelectedMenuInfo> {
        override fun createFromParcel(parcel: Parcel): NormalSelectedMenuInfo {
            return NormalSelectedMenuInfo(parcel)
        }

        override fun newArray(size: Int): Array<NormalSelectedMenuInfo?> {
            return arrayOfNulls(size)
        }
    }
}
