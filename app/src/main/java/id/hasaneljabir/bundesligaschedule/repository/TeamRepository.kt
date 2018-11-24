package id.hasaneljabir.bundesligaschedule.repository

import id.hasaneljabir.bundesligaschedule.model.TeamResponse
import io.reactivex.Flowable

interface TeamRepository {
    fun getTeams(id: String = "0"): Flowable<TeamResponse>
    fun getTeamsDetail(id: String = "0"): Flowable<TeamResponse>
}