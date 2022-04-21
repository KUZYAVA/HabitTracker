package com.kuzyava.habittrackerapp.db

class HabitRepository(private val habitDao: HabitDao) {
    fun getHabits(type: IType) =
        when (type) {
            is SortType -> getSortedHabits(type)
            is FilterType -> filterHabits(type)
        }

    private fun getSortedHabits(type: SortType) = when (type) {
        SortType.SortDefault -> habitDao.getALL()
        SortType.SortByAmount -> habitDao.getHabitsSortByAmount()
        SortType.SortByAmountDesc -> habitDao.getHabitsSortByAmountDesc()
        SortType.SortByPeriod -> habitDao.getHabitsSortByPeriod()
        SortType.SortByPeriodDesc -> habitDao.getHabitsSortByPeriodDesc()
    }

    private fun filterHabits(filterType: FilterType) = habitDao.filterHabitsByTitle(filterType.text)

    fun findById(id: Int) = habitDao.findById(id)

    suspend fun insert(habit: Habit) = habitDao.insert(habit)

    suspend fun update(habit: Habit) = habitDao.update(habit)
}
