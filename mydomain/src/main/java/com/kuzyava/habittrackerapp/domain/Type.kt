package com.kuzyava.habittrackerapp.domain

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