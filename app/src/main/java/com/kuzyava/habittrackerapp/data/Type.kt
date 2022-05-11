package com.kuzyava.habittrackerapp.data

sealed interface IType

object Refresh : IType

enum class SortType : IType {
    SortDefault,
    SortByAmount,
    SortByAmountDesc,
    SortByPeriod,
    SortByPeriodDesc
}

class FilterType(val text: String) : IType