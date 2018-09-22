package com.edwardharker.aircraftrecognition.repository.realm.mapper

import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import io.realm.RealmList
import io.realm.RealmObject
import org.junit.Assert.assertThat
import org.junit.Test

class RealmMapperTest {
    @Test
    fun mapToRealmListWithMap() {
        val expected = RealmList<TestPair>(TestPair(one, two))
        val actual = mapOf(Pair(one, two)).mapToRealmList { TestPair(it.first, it.second) }
        assertThat(expected, sameBeanAs(actual))
    }

    @Test
    fun mapToRealmListWithList() {
        val expected = RealmList<TestPair>(TestPair(one, two))
        val actual = listOf(Pair(one, two)).mapToRealmList { TestPair(it.first, it.second) }
        assertThat(expected, sameBeanAs(actual))
    }

    @Suppress("unused")
    open class TestPair(
        open var first: String = "",
        open var second: String = ""
    ) : RealmObject()

    private companion object {
        private const val one = "one"
        private const val two = "two"
    }
}

