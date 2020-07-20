package org.uc.budgettracker.utils

class Util {
    companion object {
        internal fun isNumber(s: String?): Boolean {
            return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) || it == '.'}
        }
    }
}