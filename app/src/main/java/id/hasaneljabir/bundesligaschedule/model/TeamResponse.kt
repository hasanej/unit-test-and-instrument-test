package id.hasaneljabir.bundesligaschedule.model

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("teams")
    var teams: List<TeamData>
)