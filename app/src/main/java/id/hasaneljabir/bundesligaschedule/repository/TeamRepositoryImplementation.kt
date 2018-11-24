package id.hasaneljabir.bundesligaschedule.repository

import id.hasaneljabir.bundesligaschedule.api.ApiServices
import id.hasaneljabir.bundesligaschedule.model.TeamResponse
import io.reactivex.Flowable

class TeamRepositoryImplementation(val apiServices: ApiServices) : TeamRepository {
    override fun getTeams(id: String): Flowable<TeamResponse> = apiServices.getAllTeam(id)
    override fun getTeamsDetail(id: String): Flowable<TeamResponse> = apiServices.getTeam(id)
}