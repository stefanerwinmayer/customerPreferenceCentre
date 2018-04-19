import java.time.LocalDate


data class DaySubscriptions(
    val date: LocalDate,
    val subscribers: List<Customer>
)

fun dateRangeSubscriptions(
    customers: List<Customer>,
    startDate: LocalDate = LocalDate.now(),
    endDate: LocalDate = startDate.plusDays(90),
    result: List<DaySubscriptions> = emptyList()
): List<DaySubscriptions> =

    if (startDate == endDate) result
    else dateRangeSubscriptions(
        customers, startDate.plusDays(1), endDate, result.plus(
            singleDateSuscriptions(startDate.plusDays(1), customers)
        )
    )

fun singleDateSuscriptions(
    date: LocalDate,
    customers: List<Customer>
): DaySubscriptions =

    DaySubscriptions(
        date,
        customers.mapNotNull { customer ->

            when (customer.preferences) {

                is Preference.DayOfMonth ->
                    if (date.dayOfMonth == customer.preferences.day) customer
                    else null

                is Preference.Weekdays -> {
                    if (date.dayOfWeek in customer.preferences.weekdays) customer
                    else null
                }
                is Preference.Daily -> customer
                is Preference.Never -> null
                is Preference.Unknown -> null
            }
        }
    )