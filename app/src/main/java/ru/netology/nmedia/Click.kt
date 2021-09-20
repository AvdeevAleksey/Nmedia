package ru.netology.nmedia

open class Click {

    fun dischargesReduction(click: Int, thousand: Int = 1000): String {
        return when (click) {
            in thousand until thousand*thousand -> "k"
            else -> "M"
        }
    }

    fun countMyClick(click:Int, thousand:Int = 1000): String {
        return when (click) {
            in 1 until thousand -> click.toString()
            in thousand until thousand+99 -> "1${dischargesReduction(click)}"
            in thousand until thousand*10 -> "${click/thousand%10},${(click/thousand-click/thousand%10)*10%10}${dischargesReduction(click)}"
            in thousand*10 until thousand*thousand -> "${click/thousand%10}${dischargesReduction(click)}"
            else -> click.toString()
        }
    }
}