package com.david.hackro.store

import app.cash.turbine.test
import com.david.hackro.products.data.ProductsRepositoryImpl
import com.david.hackro.products.data.local.ProductsDao
import com.david.hackro.products.data.remote.ProductsApi
import com.david.hackro.products.domain.ProductsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AdviceRepositoryTest {

    @RelaxedMockK
    private lateinit var remoteSource: ProductsApi

    @RelaxedMockK
    private lateinit var localSource: ProductsDao

    private lateinit var objectUnderTest: ProductsRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setUpRepository()
    }
    /*
          val localResponse = listOf(
              CategoryEntity().apply { category = "electronics" },
              CategoryEntity().apply { category = "men" }
          )
           */


    @Test
    fun `should refresh category from remote source when local response is empty`() = runTest {
        //Given
        coEvery { localSource.getCategoriesx() } returns flowOf(emptyList())

        //When
        objectUnderTest.getFlashProductsx().test {
            val result = awaitItem()

            assertEquals(
                expected = true,
                actual = true
            )
            awaitComplete()
        }

        //Then
        coVerifyOrder {
            remoteSource.getCategories()
            localSource.insertCategories(listOf())
        }
    }

    private fun setUpRepository() {
        objectUnderTest = ProductsRepositoryImpl(remoteSource, localSource)
    }


}