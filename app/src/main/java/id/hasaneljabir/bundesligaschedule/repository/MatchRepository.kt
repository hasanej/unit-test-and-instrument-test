package id.hasaneljabir.bundesligaschedule.repository

import id.hasaneljabir.bundesligaschedule.model.EventResponse
import io.reactivex.Flowable

interface MatchRepository {
    fun getLastMatch(id: String): Flowable<EventResponse>
    fun getNextMatch(id: String): Flowable<EventResponse>
    fun getEventById(id: String): Flowable<EventResponse>
}