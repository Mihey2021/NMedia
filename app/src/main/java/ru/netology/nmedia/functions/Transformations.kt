package ru.netology.nmedia.functions

import java.math.RoundingMode
import java.text.DecimalFormat

fun prepareCountToDisplay(count: Int): String {

    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.CEILING

    if (count in 1000..9_999) return "${df.format(((count / 100) % 10000) * 0.1)}K"
    if (count in 10000..999_999) return "${df.format((count / 1000) % 1000)}K"
    if (count in 1_000_000..99_999_999) return "${df.format(((count / 100000) % 1000) * 0.1)}M"
    if (count in 100_000_000..999_999_999) return "${df.format(((count / 100000) % 10000) * 0.1)}M"
    if (count > 999_999_999) return "> 1B"
    return count.toString()
}
