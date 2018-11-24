package id.hasaneljabir.bundesligaschedule

import id.hasaneljabir.bundesligaschedule.model.EventData
import id.hasaneljabir.bundesligaschedule.model.EventResponse
import id.hasaneljabir.bundesligaschedule.presenter.NextMatchPresenter
import id.hasaneljabir.bundesligaschedule.repository.MatchRepositoryImplementation
import id.hasaneljabir.bundesligaschedule.utils.SchedulerProvider
import id.hasaneljabir.bundesligaschedule.utils.TestSchedulerProvider
import id.hasaneljabir.bundesligaschedule.view.MatchView
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class NextMatchPresenterTest {

    @Mock
    lateinit var view: MatchView.View

    @Mock
    lateinit var matchRepositoryImplementation: MatchRepositoryImplementation

    lateinit var scheduler: SchedulerProvider

    lateinit var presenter: NextMatchPresenter

    lateinit var match: EventResponse

    lateinit var footballMatch: Flowable<EventResponse>

    private val event = mutableListOf<EventData>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = TestSchedulerProvider()
        match = EventResponse(event)
        footballMatch = Flowable.just(match)
        presenter = NextMatchPresenter(view, matchRepositoryImplementation, scheduler)
        `when`(matchRepositoryImplementation.getNextMatch("4331")).thenReturn(footballMatch)
    }

    @Test
    fun getFootballMatchData() {
        presenter.getMatchList()
        verify(view).showLoading()
        verify(view).showMatchList(event)
        verify(view).hideLoading()
    }
}