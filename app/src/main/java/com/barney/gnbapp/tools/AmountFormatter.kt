package com.barney.gnbapp.tools

import java.math.BigDecimal
import java.text.DecimalFormat

object AmountFormatter {

    fun formatAmountToString(amount: BigDecimal)
    : String = DecimalFormat("#,###.00").let { it.format(amount)}

}