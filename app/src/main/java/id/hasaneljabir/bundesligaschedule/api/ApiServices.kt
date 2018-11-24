package id.hasaneljabir.bundesligaschedule.api

import id.hasaneljabir.bundesligaschedule.model.EventResponse
import id.hasaneljabir.bundesligaschedule.model.TeamResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("eventspastleague.php")
    fun getLastmatch(@Query("id") id: String): Flowable<EventResponse>

    @GET("eventsnextleague.php")
    fun getNextMatch(@Query("id") id: String): Flowable<EventResponse>

    @GET("lookup_all_teams.php")
    fun getAllTeam(@Query("id") id: String): Flowable<TeamResponse>

    @GET("lookupteam.php")
    fun getTeam(@Query("id") id: String): Flowable<TeamResponse>

    @GET("lookupevent.php")
    fun getEventById(@Query("id") id: String): Flowable<EventResponse>
}