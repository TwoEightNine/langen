package global.msnthrp.langen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import global.msnthrp.langen.data.datasource.LanguagesDataSource
import global.msnthrp.langen.models.MINIMAL
import global.msnthrp.langen.platform.datasource.DbLanguageDataSource
import global.msnthrp.langen.platform.db.AppDatabase
import global.msnthrp.langen.ui.create.CreateLanguageViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.internal.util.reflection.FieldSetter
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CreateLanguageViewModelTest {

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CreateLanguageViewModel
    private lateinit var testLanguageDataSource: LanguagesDataSource

    private lateinit var languageName: TestObserver<String>
    private lateinit var languageSample: TestObserver<String>
    private lateinit var saved: TestObserver<Unit>


    @Before
    fun setup() {
        viewModel = CreateLanguageViewModel()

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val testDb = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
        testLanguageDataSource = DbLanguageDataSource()
        FieldSetter.setField(
            testLanguageDataSource,
            testLanguageDataSource.javaClass.getDeclaredField("dao"),
            testDb.languageDao()
        )
        FieldSetter.setField(
            viewModel,
            viewModel.javaClass.getDeclaredField("languageDataSource"),
            testLanguageDataSource
        )

        languageName = viewModel.languageName.testObserver()
        languageSample = viewModel.languageSample.testObserver()
        saved = viewModel.saved.testObserver()
    }

    @Test
    fun createLanguage_updateNameAndSample() {
        languageName.observedValues.clear()
        languageSample.observedValues.clear()

        Truth.assertThat(languageName.observedValues)
            .isEmpty()
        Truth.assertThat(languageSample.observedValues)
            .isEmpty()

        viewModel.generateNewLanguage(longWords = true, alphabet = MINIMAL.toList())

        Truth.assertThat(languageName.observedValues)
            .hasSize(1)
        Truth.assertThat(languageSample.observedValues)
            .hasSize(1)

        viewModel.generateNewLanguage(longWords = false, alphabet = MINIMAL.toList())

        Truth.assertThat(languageName.observedValues)
            .hasSize(2)
        Truth.assertThat(languageSample.observedValues)
            .hasSize(2)

        Truth.assertThat(languageName.observedValues)
            .containsNoDuplicates()
        Truth.assertThat(languageSample.observedValues)
            .containsNoDuplicates()
    }

    @Test
    fun createLanguage_save() {
        languageName.observedValues.clear()
        languageSample.observedValues.clear()
        saved.observedValues.clear()

        Truth.assertThat(languageName.observedValues)
            .isEmpty()
        Truth.assertThat(languageSample.observedValues)
            .isEmpty()
        Truth.assertThat(saved.observedValues)
            .isEmpty()

        viewModel.generateNewLanguage(longWords = false, alphabet = MINIMAL.toList())

        Truth.assertThat(languageName.observedValues)
            .hasSize(1)
        Truth.assertThat(languageSample.observedValues)
            .hasSize(1)

        val languageName = languageName.observedValues[0]
        viewModel.saveCurrentLanguage()

        Truth.assertThat(saved.observedValues)
            .hasSize(1)

        val languages = testLanguageDataSource.getAllLanguages()
            .blockingGet()

        Truth.assertThat(languages.map { it.name })
            .contains(languageName)
    }


}