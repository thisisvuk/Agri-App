package my.uk.agriapp

class Constants {

    private val baseUrl = "https://178t6lqtci.execute-api.us-east-2.amazonaws.com/live/" //Admin panel url

    val checkUser = baseUrl + "checkUser"
    val getProfile = baseUrl + "profile"
    val getChats = baseUrl + "getChats"
    val getCrops = baseUrl + "getCrops"
    val updateProfile = baseUrl + "updateProfile"

    private val numericString = "123456789"

    fun randomNumeric(count: Int = 6): String {
        var count1 = count
        val builder = StringBuilder()
        while (count1-- != 0) {
            val character = (Math.random() * numericString.length).toInt()
            builder.append(numericString[character])
        }
        return builder.toString()
    }

}