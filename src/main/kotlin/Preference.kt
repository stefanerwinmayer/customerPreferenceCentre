import java.time.DayOfWeek

sealed class Preference {
    class DayOfMonth(val day: Int) : Preference()
    class Weekdays(val weekdays: List<DayOfWeek>) : Preference()
    object Daily : Preference()
    object Never : Preference()
    object Unknown : Preference()
}

fun convertToPreferences(
    input: String,
    dayOfMonthPattern: Regex = "0[0-9]|1[0-9]|2[0-8]".toRegex(),
    weekDayPattern: Regex = "MON|TUE|WED|THU|FRI|SAT|SUN".toRegex(),
    dailyPattern: Regex = "Daily".toRegex(),
    neverPattern: Regex = "Never".toRegex()
): Preference =

    when {
        dayOfMonthPattern in input -> Preference.DayOfMonth(input.toInt())
        weekDayPattern in input -> input
            .split(delimiters = " ")
            .mapNotNull { day: String -> stringToDayOfWeek[day] }
            .let { days: List<DayOfWeek> -> Preference.Weekdays(days) }
        dailyPattern in input -> Preference.Daily
        neverPattern in input -> Preference.Never
        else -> Preference.Unknown
    }

val stringToDayOfWeek: Map<String, DayOfWeek> = mapOf(
    "MON" to DayOfWeek.MONDAY,
    "TUE" to DayOfWeek.TUESDAY,
    "WED" to DayOfWeek.WEDNESDAY,
    "THU" to DayOfWeek.THURSDAY,
    "FRI" to DayOfWeek.FRIDAY,
    "SAT" to DayOfWeek.SATURDAY,
    "SUN" to DayOfWeek.SUNDAY
)