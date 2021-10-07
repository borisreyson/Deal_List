package com.misit.deallist.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Cart :RealmObject(){
    @PrimaryKey
    var id= 0
    var productId :String?  =   null
    var productName :String? =  null
    var photo : String? = null
    var price =0.0
}