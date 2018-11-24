package id.hasaneljabir.bundesligaschedule.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun formatDateToMatch(date: Date): String {
        return SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(date)
    }
}