package com.yeuristic.dynamicmodulelib

import android.os.Parcel
import android.os.Parcelable

class DownloadModuleProperties(val moduleName: String, val moduleDisplay: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(moduleName)
        dest?.writeString(moduleDisplay)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DownloadModuleProperties> {
        override fun createFromParcel(parcel: Parcel): DownloadModuleProperties {
            return DownloadModuleProperties(parcel)
        }

        override fun newArray(size: Int): Array<DownloadModuleProperties?> {
            return arrayOfNulls(size)
        }
    }
}