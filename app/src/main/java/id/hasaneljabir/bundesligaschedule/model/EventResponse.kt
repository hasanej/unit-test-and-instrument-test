package id.hasaneljabir.bundesligaschedule.model

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("events") var events: List<EventData>
)