package id.hasaneljabir.bundesligaschedule.repository

import id.hasaneljabir.bundesligaschedule.api.ApiServices
import id.hasaneljabir.bundesligaschedule.model.EventResponse
import io.reactivex.Flowable

class MatchRepositoryImplementation(private val apiServices: ApiServices) : MatchRepository {
    override fun getLastMatch(id: String): Flowable<EventResponse> = apiServices.getLastmatch(id)
    override fun getNextMatch(id: String): Flowable<EventResponse> = apiServices.getNextMatch(id)
    override fun getEventById(id: String): Flowable<EventResponse> = apiServices.getEventById(id)
}