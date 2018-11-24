package id.hasaneljabir.bundesligaschedule.view

import id.hasaneljabir.bundesligaschedule.databaseHelper.FavoriteMatch
import id.hasaneljabir.bundesligaschedule.model.TeamData

interface DetailMatchView {

    interface View {
        fun showLogoHome(team: TeamData)
        fun showLogoAway(team: TeamData)
        fun setFavoriteState(favList: List<FavoriteMatch>)
    }

    interface Presenter {
        fun getLogoHome(id: String)
        fun getLogoAway(id: String)
        fun deleteMatch(id: String)
        fun checkMatch(id: String)
        fun insertMatch(eventId: String, homeId: String, awayId: String)
        fun onDestroyPresenter()
    }
}