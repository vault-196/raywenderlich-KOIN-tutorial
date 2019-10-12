package com.raywenderlich.markme


import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.raywenderlich.markme.di.applicationModule
import com.raywenderlich.markme.feature.FeatureContract
import com.raywenderlich.markme.model.Student
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mockito

class FeaturePresenterTest : KoinTest {

    private val view: FeatureContract.View<Student> = mock()
    private val repository: FeatureContract.Model<Student> by inject()
    private val presenter: FeatureContract.Presenter<Student> by inject { parametersOf(view) }

    @Before
    fun before() {
        startKoin {
            koinApplication { }
            listOf(applicationModule)
        }

// Test not working becouse of this
        declareMock<FeatureContract.Model<Student>>()
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `check that onSave2DbClick invokes a repository callback`() {
        val studentList = listOf(
                Student(0, "Pablo", true, 8),
                Student(1, "Irene", false, 10))
        val dummyCallback = argumentCaptor<(String) -> Unit>()

        presenter.onSave2DbClick(studentList)
        Mockito.verify(repository).add2Db(data = eq(studentList), callback = dummyCallback.capture())
    }

}