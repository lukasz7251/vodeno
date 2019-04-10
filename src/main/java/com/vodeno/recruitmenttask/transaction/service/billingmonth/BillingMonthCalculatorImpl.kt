package com.vodeno.recruitmenttask.transaction.service.billingmonth

import org.springframework.stereotype.Component
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Component
class BillingMonthCalculatorImpl : BillingMonthCalculator {

    override fun calculateStartOfCurrent(userCreateDate: Date, currentTime: Date, zoneId: ZoneId): Date {
        val nowZonedDateTime = currentTime.toInstant().atZone(zoneId)
        val userCreateZonedDateTime = userCreateDate.toInstant().atZone(zoneId)
        if (nowZonedDateTime.year == userCreateZonedDateTime.year && nowZonedDateTime.dayOfYear == userCreateZonedDateTime.dayOfYear) {
            return userCreateDate
        }
        return userCreateZonedDateTime
                .withYear(nowZonedDateTime.year)
                .withMonth(nowZonedDateTime.monthValue)
                .let { if (it.isAfter(nowZonedDateTime)) it.minusMonths(1) else it }
                .midnight()
                .toDate()
    }

    override fun calculateEndOfCurrent(userCreateDate: Date, currentTime: Date, zoneId: ZoneId): Date {
        val nowZonedDateTime = currentTime.toInstant().atZone(zoneId)
        val userCreateZonedDateTime = userCreateDate.toInstant().atZone(zoneId)
        return userCreateZonedDateTime
                .withYear(nowZonedDateTime.year)
                .withMonth(nowZonedDateTime.monthValue)
                .let { if (it.isBefore(nowZonedDateTime)) it.plusMonths(1) else it }
                .nextDay()
                .midnight()
                .toDate()
    }

}

private fun ZonedDateTime.toDate(): Date = Date.from(this.toInstant())

private fun ZonedDateTime.midnight(): ZonedDateTime = this.withHour(0)
        .withMinute(0)
        .withSecond(0)
        .nextDay()

private fun ZonedDateTime.nextDay() = this.plusDays(1)