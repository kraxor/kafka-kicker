package ch.sonofabit.kafka.kicker.lib.util

import java.io.File
import java.net.URL

object ResourceUtils {
    fun getResourceFile(resourceName: String): File {
        val resourceUrl: URL = ResourceUtils::class.java.classLoader.getResource(resourceName)
            ?: throw IllegalArgumentException("File not found!")

        return File(resourceUrl.toURI())
    }
}
