package com.vodeno.recruitmenttask.transaction.service.billingmonth

import org.junit.Assert
import org.junit.Test
import java.text.DateFormat
import java.text.SimpleDateFormat


class BillingMonthCalculatorImplTest {

    private val impl = BillingMonthCalculatorImpl()

    private val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Test
    fun exampleFromEmail() {
        // GIVEN
        val userCreateDate = formatter.parse("2019-03-01 13:31:12")
        val currentTime = formatter.parse("2019-04-03 12:10:03")
        // WHEN
        val result = impl.calculateStartOfCurrent(userCreateDate = userCreateDate, currentTime = currentTime)
        // THEN
        Assert.assertEquals(formatter.parse("2019-04-02 00:00:00"), result)
    }

    @Test
    fun differentMonthLength() {
        // GIVEN
        val userCreateDate = formatter.parse("2012-07-31 09:13:37")
        val currentTime = formatter.parse("2019-04-15 12:10:03")
        // WHEN
        val result = impl.calculateStartOfCurrent(userCreateDate = userCreateDate, currentTime = currentTime)
        // THEN
        Assert.assertEquals(formatter.parse("2019-03-31 00:00:00"), result)
    }

    @Test
    fun now() {
        // GIVEN
        val userCreateDate = formatter.parse("2019-04-07 22:03:13")
        val currentTime = formatter.parse("2019-04-07 22:03:13")
        // WHEN
        val result = impl.calculateStartOfCurrent(userCreateDate = userCreateDate, currentTime = currentTime)
        // THEN
        Assert.assertEquals(formatter.parse("2019-04-07 22:03:13"), result)
    }

}