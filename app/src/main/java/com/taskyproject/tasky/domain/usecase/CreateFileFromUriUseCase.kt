package com.taskyproject.tasky.domain.usecase

import android.content.Context
import android.net.Uri
import com.taskyproject.tasky.domain.util.generateRandomString
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class CreateFileFromUriUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(uri: Uri): File {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val fileName = "${generateRandomString()}.png"
        val outputDir = context.cacheDir
        val outputFile = File(outputDir, fileName)

        inputStream.use { input ->
            FileOutputStream(outputFile).use { output ->
                input?.copyTo(output)
            }
        }
        return outputFile
    }
}