package com.example.khatabook2.Model

class TransModel {


    var id = 0
    var amount = 0
    var category = ""
    var note = ""
    var isexpense = 0


    constructor(id: Int, amount: Int, category: String, note: String ,isexpense:Int) {
        this.id = id
        this.amount = amount
        this.category = category
        this.note = note
        this.isexpense = isexpense

    }
}