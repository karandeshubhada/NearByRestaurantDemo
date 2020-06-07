package com.demo.nearbyrestaratantlistukotlin.model

data class NearByRestaurantResponse(var name:String?= null, var neighborhood:String?=null,
                                    var distance:String?=null, var address:String?=null, var cuisine_type:String?=null,var rating:Int=0){

}