package id.hasaneljabir.bundesligaschedule.repository

import id.hasaneljabir.bundesligaschedule.databaseHelper.FavoriteMatch

interface LocalRepository {
    fun getMatchFromDb(): List<FavoriteMatch>
    fun insertData(eventId: String, homeId: String, awayId: String)
    fun deleteData(eventId: String)
    fun checkFavorite(eventId: String): List<FavoriteMatch>
}