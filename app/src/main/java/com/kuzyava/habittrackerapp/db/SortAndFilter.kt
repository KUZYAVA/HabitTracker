package com.kuzyava.habittrackerapp.db

sealed interface IType
enum class SortType : IType {
    SortDefault,
    SortByAmount,
    SortByAmountDesc,
    SortByPeriod,
    SortByPeriodDesc
}

class FilterType(val text: String) : IType