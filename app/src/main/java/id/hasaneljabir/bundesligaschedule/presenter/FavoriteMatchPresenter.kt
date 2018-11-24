package id.hasaneljabir.bundesligaschedule.presenter

import id.hasaneljabir.bundesligaschedule.model.EventData
import id.hasaneljabir.bundesligaschedule.model.EventResponse
import id.hasaneljabir.bundesligaschedule.repository.LocalRepositoryImplementation
import id.hasaneljabir.bundesligaschedule.repository.MatchRepositoryImplementation
import id.hasaneljabir.bundesligaschedule.utils.SchedulerProvider
import id.hasaneljabir.bundesligaschedule.view.FavoriteMatchView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class FavoriteMatchPresenter(
    val view: FavoriteMatchView.View,
    val matchRepositoryImplementaion: MatchRepositoryImplementation,
    val localRepositoryImpl: LocalRepositoryImplementation,
    val scheduler: SchedulerProvider
) : FavoriteMatchView.Presenter {

    val compositeDisposable = CompositeDisposable()

    override fun getFootballMatchData() {
        view.showLoading()
        val favList = localRepositoryImpl.getMatchFromDb()
        var eventList: MutableList<EventData> = mutableListOf()
        for (fav in favList) {
            compositeDisposable.add(
                matchRepositoryImplementaion.getEventById(fav.idEvent)
                    .observeOn(scheduler.ui())
                    .subscribeOn(scheduler.io())
                    .subscribeWith(object : ResourceSubscriber<EventResponse>() {
                        override fun onComplete() {
                            view.hideLoading()
                            view.hideSwipeRefresh()
                        }

                        override fun onNext(t: EventResponse) {
                            eventList.add(t.events[0])
                            view.displayFootballMatch(eventList)
                        }

                        override fun onError(t: Throwable?) {
                            view.displayFootballMatch(Collections.emptyList())
                            view.hideLoading()
                            view.hideSwipeRefresh()
                        }

                    })
            )
        }

        if (favList.isEmpty()) {
            view.hideLoading()
            view.hideSwipeRefresh()
            view.displayFootballMatch(eventList)
        }
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }
}