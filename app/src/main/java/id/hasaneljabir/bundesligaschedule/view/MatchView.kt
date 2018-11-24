package id.hasaneljabir.bundesligaschedule.view

import id.hasaneljabir.bundesligaschedule.model.EventData

interface MatchView {
    interface View {
        fun hideLoading()
        fun showLoading()
        fun showMatchList(matchList: List<EventData>)
    }

    interface Presenter {
        fun getMatchList(leagueName: String = "4331")
        fun onDestroyPresenter()
    }
}