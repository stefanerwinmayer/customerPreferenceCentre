data class Customer(
    val id: Char,
    val preferences: Preference
)

fun makeCustomers(
    preferences: List<Preference>,
    customerIds: CharIterator = ('A'..'Z').iterator()
): List<Customer> =

    preferences
        .filterNot { preference -> preference === Preference.Unknown }
        .map { preference -> Customer(customerIds.nextChar(), preference) }