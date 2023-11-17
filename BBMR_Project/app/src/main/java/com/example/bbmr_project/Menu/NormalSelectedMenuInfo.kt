package com.example.bbmr_project.Menu

class NormalSelectedMenuInfo(
    val menuImg: Int,
    val name: String?,
    val price: String?,
    val temperature: String,
    val tvCount: Int,
    val tvCount1: Int,
    val tvCount2: Int,
    val tvCount3: Int,
    val tvCount4: Int
){
    override fun toString(): String {
        return "NormalSelectedMenuInfo(menuImg=$menuImg, name=$name, price=$price, " +
                "temperature=$temperature, tvCount=$tvCount, tvCount1=$tvCount1, " +
                "tvCount2=$tvCount2, tvCount3=$tvCount3, tvCount4=$tvCount4)"
    }
}