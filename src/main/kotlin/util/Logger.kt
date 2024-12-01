package co.sakurastudios.mcsoundextractor.util


class Logger {
    fun info(message: String) = println(message)
    fun error(message: String) = println("Error: $message")
    fun success(message: String) = println("Success: $message")
}