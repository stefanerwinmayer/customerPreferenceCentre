import java.time.format.DateTimeFormatter


fun main(args: Array<String>) {

    println(greetings)

    val inputs: List<String> = readFromConsole()

    val preferences: List<Preference> = inputs.map { each -> convertToPreferences(each) }

    val customers: List<Customer> = makeCustomers(preferences)

    val subscriptions: List<DaySubscriptions> = dateRangeSubscriptions(customers)

    val report: String = compileReport(subscriptions)

    println(report)

}


val greetings: String =
    """
        |
        |Welcome to Customer Preference Centre
        |
        |Please enter a series of subscription preferences - one for each customer.
        |
        |Each customer's input will be submitted by pressing [Enter].
        |Inputs not following conventions will be discarded.
        |The conventions for entering preferences for a single customer are:
        |
        |   1. One of [01-28] to subscribe for a given day each month (Mind the preceding '0' for single digits)
        |   2. One or more of [MON-SUN] to subscribe for one or more week days (separated by a space character)
        |   3. 'Daily' to subscribe for every day
        |   4. 'Never' to not subscribe at all
        |
        |
        |Press [Enter] without any input to produce the report.
        |
    """.trimMargin()


fun compileReport(
    subscriptions: List<DaySubscriptions>,
    formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("E dd-MMMM-yyyy")
): String {

    val stringBuilder = StringBuilder()

    for (daySubscriptions in subscriptions) {
        val date = daySubscriptions.date.format(formatter)
        val subscriberIds = daySubscriptions.subscribers
            .map { subscriber -> subscriber.id }
            .toString()
            .trim('[', ']')

        stringBuilder.appendln("$date   $subscriberIds")
    }

    return stringBuilder.toString()
}


fun readFromConsole(
    result: List<String?> = emptyList()
): List<String> {

    print("Enter customer preference > ")

    return readLine().let { input: String? ->
        if (input == "") result.filterNotNull()
        else readFromConsole(result.plus(input))
    }
}