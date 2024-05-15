package com.example.hungryhub.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.ServerValue
import java.io.Serializable

class OrderDetails() : Serializable {
    var userUid: String? = null
    var userName: String? = null
    var foodNames: MutableList<String>? = null
    var foodImages: MutableList<String>? = null
    var foodPrices: MutableList<String>? = null
    var foodQuantities: MutableList<Int>? = null
    var address: String? = null
    var totalPrice: String? = null
    var phoneNumber: String? = null
    var orderAccepted: Boolean = true
    var paymentReceived: Boolean = false
    var itemPushKey: String? = null
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
        orderAccepted: Boolean ,
        paymentReceived: Boolean
    ) : this() {
        this.userUid = useruid
        this.userName = username
        this.foodNames = foodnames
        this.foodImages = foodimages
        this.foodPrices = foodprices
        this.foodQuantities = foodquantitys
        this.address = address
        this.totalPrice = totalprice
        this.phoneNumber = phonenumber
        this.orderAccepted = orderAccepted
        this.currentTime = currentTime
        this.paymentReceived =  paymentReceived
        this.itemPushKey = itempushkey
    }

    // Implementing Parcelable interface methods
    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        foodNames = parcel.createStringArrayList()
        foodImages = parcel.createStringArrayList()
        foodPrices = parcel.createStringArrayList()
        foodQuantities = parcel.createIntArray()?.toMutableList()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        currentTime = parcel.readLong()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
    }

    fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUid)
        parcel.writeString(userName)
        parcel.writeStringList(foodNames)
        parcel.writeStringList(foodImages)
        parcel.writeStringList(foodPrices)
        parcel.writeIntArray(foodQuantities?.toIntArray())
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeLong(currentTime)
        parcel.writeByte(if (paymentReceived) 1 else 0)
        parcel.writeString(itemPushKey)
    }

     fun describeContents(): Int {
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

