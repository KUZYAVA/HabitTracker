package com.kuzyava.habittrackerapp.domain

public class HabitModel(
    var uid: String,
    val title: String,
    val description: String,
    val priority: Int,
    val type: Int,
    var amount: Int,
    val periodicity: Int,
    val color: Int,
    var date: Int
)