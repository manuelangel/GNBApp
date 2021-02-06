package com.barney.gnbapp.tools

import java.math.BigDecimal

object AmountRounder {

    fun roundHalfToEven(amount: BigDecimal): BigDecimal{
        return amount.setScale(2, BigDecimal.ROUND_HALF_EVEN)
    }

}