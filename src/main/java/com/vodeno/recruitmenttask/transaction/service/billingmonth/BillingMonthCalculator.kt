package com.vodeno.recruitmenttask.transaction.service.billingmonth

import java.time.ZoneId
import java.util.*

interface BillingMonthCalculator {
    fun calculateStartOfCurrent(userCreateDate: Date, currentTime: Date = Date(), zoneId: ZoneId = ZoneId.systemDefault()): Date
    fun calculateEndOfCurrent(userCreateDate: Date, currentTime: Date = Date(), zoneId: ZoneId = ZoneId.systemDefault()): Date
}