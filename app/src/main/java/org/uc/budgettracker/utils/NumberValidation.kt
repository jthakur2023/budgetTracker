/** this can be removed based on sukbirsekhon's code review comments about changing the input type */

package org.uc.budgettracker.utils

class NumberValidation {
    companion object {
        fun isNumber(s: String?): Boolean {
            return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) || it == '.'}
        }
    }
}