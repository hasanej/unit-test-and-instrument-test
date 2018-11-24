package id.hasaneljabir.bundesligaschedule.view

import id.hasaneljabir.bundesligaschedule.model.EventData

interface FavoriteMatchView {
    interface View {
        fun hideLoading()
        fun showLoading()
        fun displayFootballMatch(matchList: List<EventData>)
        fun hideSwipeRefresh()
    }

    interface Presenter {
        fun getFootballMatchData()
        fun onDestroyPresenter()
    }
}