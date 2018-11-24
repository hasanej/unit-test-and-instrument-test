package id.hasaneljabir.bundesligaschedule.presenter

import id.hasaneljabir.bundesligaschedule.model.EventResponse
import id.hasaneljabir.bundesligaschedule.repository.MatchRepositoryImplementation
import id.hasaneljabir.bundesligaschedule.utils.SchedulerProvider
import id.hasaneljabir.bundesligaschedule.view.MatchView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class LastMatchPresenter(
    val view: MatchView.View,
    val matchRepositoryImplementation: MatchRepositoryImplementation,
    val scheduler: SchedulerProvider
) : MatchView.Presenter {
    val compositeDisposable = CompositeDisposable()

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }

    override fun getMatchList(leagueName: String) {
        view.showLoading()
        compositeDisposable.add(
            matchRepositoryImplementation.getLastMatch(leagueName)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<EventResponse>() {
                    override fun onComplete() {
                        view.hideLoading()
                    }

                    override fun onNext(t: EventResponse) {
                        view.showMatchList(t.events)
                    }

                    override fun onError(t: Throwable?) {
                        view.hideLoading()
                        view.showMatchList(Collections.emptyList())
                    }
                })
        )
    }
}