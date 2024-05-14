package com.example.hungryhub.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.ServerValue

class OrderDetails() : Parcelable {
    var useruid: String? = null
    var username: String? = null
    var foodnames: MutableList<String>? = null
    var foodimages: MutableList<String>? = null
    var foodprices: MutableList<String>? = null
    var foodquantitys: MutableList<Int>? = null
    var address: String? = null
    var totalprice: String? = null
    var phonenumber: String? = null
    var orderisAccepted: Boolean = false
    var paymentisReceived: Boolean = false
    var itempushkey: String? = null
    var currentTime: Long = 0


    constructor(
        useruid: String?,
        username: String?,
        foodnames: MutableList<String>?,
        foodprices: MutableList<String>?,
        foodimages: MutableList<String>?,
        foodquantitys: MutableList<Int>?,
        address: String?,
        totalprice: String?,
        phonenumber: String?,
        currentTime : Long,
        itempushkey : String?,
        orderisAccepted: Boolean ,
        paymentisReceived: Boolean
    ) : this() {
        this.useruid = useruid
        this.username = username
        this.foodnames = foodnames
        this.foodimages = foodimages
        this.foodprices = foodprices
        this.foodquantitys = foodquantitys
        this.address = address
        this.totalprice = totalprice
        this.phonenumber = phonenumber
        this.orderisAccepted = orderisAccepted
        this.currentTime = currentTime
        this.paymentisReceived =  paymentisReceived
        this.itempushkey = itempushkey
    }

    // Implementing Parcelable interface methods
    constructor(parcel: Parcel) : this() {
        useruid = parcel.readString()
        username = parcel.readString()
        foodnames = parcel.createStringArrayList()
        foodimages = parcel.createStringArrayList()
        foodprices = parcel.createStringArrayList()
        foodquantitys = parcel.createIntArray()?.toMutableList()
        address = parcel.readString()
        totalprice = parcel.readString()
        phonenumber = parcel.readString()
        orderisAccepted = parcel.readByte() != 0.toByte()
        currentTime = parcel.readLong()
        paymentisReceived = parcel.readByte() != 0.toByte()
        itempushkey = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(useruid)
        parcel.writeString(username)
        parcel.writeStringList(foodnames)
        parcel.writeStringList(foodimages)
        parcel.writeStringList(foodprices)
        parcel.writeIntArray(foodquantitys?.toIntArray())
        parcel.writeString(address)
        parcel.writeString(totalprice)
        parcel.writeString(phonenumber)
        parcel.writeByte(if (orderisAccepted) 1 else 0)
        parcel.writeLong(currentTime)
        parcel.writeByte(if (paymentisReceived) 1 else 0)
        parcel.writeString(itempushkey)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}

