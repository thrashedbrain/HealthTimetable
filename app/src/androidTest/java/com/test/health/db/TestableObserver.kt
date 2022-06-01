package com.test.health.db

import androidx.lifecycle.Observer
import junit.framework.Assert.assertEquals


class TestableObserver<T> : Observer<T> {
    private val history: MutableList<T> = mutableListOf()

    override fun onChanged(value: T) {
        history.add(value)
    }

    fun assertAllEmitted(values: List<T>) {
        assertEquals(values.count(), history.count())

        history.forEachIndexed { index, t ->
            assertEquals(values[index], t)
        }
    }
}