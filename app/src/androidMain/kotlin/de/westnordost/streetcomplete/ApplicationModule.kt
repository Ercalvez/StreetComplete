package de.westnordost.streetcomplete

import android.content.res.AssetManager
import android.content.res.Resources
import de.westnordost.streetcomplete.resources.Res
import de.westnordost.streetcomplete.util.CrashReportExceptionHandler
import de.westnordost.streetcomplete.util.DefaultResourceProvider
import de.westnordost.streetcomplete.util.ResourceProvider
import de.westnordost.streetcomplete.util.SoundFx
import de.westnordost.streetcomplete.util.logs.DatabaseLogger
import io.ktor.client.HttpClient
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.userAgent
import kotlinx.io.files.FileSystem
import kotlinx.io.files.SystemFileSystem
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    factory<AssetManager> { androidContext().assets }
    factory<Resources> { androidContext().resources }

    single { CrashReportExceptionHandler(androidContext(), get(), "crashreport.txt") }
    single { DatabaseLogger(get()) }
    single { SoundFx(androidContext()) }
    single { HttpClient {
        defaultRequest {
            userAgent(ApplicationConstantsAndroid.USER_AGENT)
        }
        install(ContentEncoding) {
            gzip()
            deflate()
            identity()
        }
    } }
    single<Res> { Res }
    single<FileSystem> { SystemFileSystem }
    single<ResourceProvider> { DefaultResourceProvider(androidContext(), get()) }
}
