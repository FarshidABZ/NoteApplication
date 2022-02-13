package com.task.noteapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.task.noteapp.FakeDataUtil.fakeTimeStamp
import com.task.noteapp.FakeDataUtil.fakeTimeStampSimpleString
import com.task.noteapp.FakeDataUtil.fakeTimeStampString
import com.task.noteapp.util.toDD_MMM
import com.task.noteapp.util.toDD_MM_YYYY
import org.junit.Rule
import org.junit.Test

class DateExtKtTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun give_time_stamp_call_toDD_MM_YYYY_return_correct_format_like_01_12_2020() {
        val input = fakeTimeStamp
        val outPut = input.toDD_MM_YYYY()
        Truth.assertThat(outPut).isEqualTo(fakeTimeStampString)
    }

    @Test
    fun give_time_stamp_call_toDD_MMM_return_correct_format_like_17_jun() {
        val input = fakeTimeStamp
        val outPut = input.toDD_MMM()
        Truth.assertThat(outPut).isEqualTo(fakeTimeStampSimpleString)
    }
}