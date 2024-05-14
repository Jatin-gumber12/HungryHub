package com.example.hungryhub.model

class CartItems {
    var foodNames: String? = null
    var foodPrice: String? = null
    var foodDescription: String? = null
    var foodImages: String? = null
    var foodquantity: Int? = null


    constructor()


    constructor(foodNames: String, foodPrice : String , foodDescription: String , foodImages : String , foodquantity : Int)
    {
        this.foodNames = foodNames
        this.foodPrice = foodPrice
        this.foodDescription = foodDescription
        this.foodImages = foodImages
        this.foodquantity = foodquantity
    }



}