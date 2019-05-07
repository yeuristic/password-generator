package com.yeuristic.dynamicmodulelib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

const val MODULE_REQUEST_KEY = "MODULE_REQUEST_KEY"
class DownloadModuleConfirmationActivity : AppCompatActivity() {
    // Creates an instance of SplitInstallManager.
    private val splitInstallManager: SplitInstallManager by lazy {
        SplitInstallManagerFactory.create(this)
    }
    lateinit var moduleProperties: DownloadModuleProperties
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_module_confirmation)
        moduleProperties = intent.getParcelableExtra(MODULE_REQUEST_KEY)


        val blockStateUpdated = { state: SplitInstallSessionState ->
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    val totalBytes = state.totalBytesToDownload()
                    val progress = state.bytesDownloaded()
                    // Update progress bar.
                }
//                SplitInstallSessionStatus.INSTALLED -> {
//                    launchActivity(moduleData.landingPageActivityName)
//                }
                else -> toastAndLog("Status ${state.status()}")
            }
        }
        splitInstallManager.downloadModule(this, moduleProperties.moduleName, blockStateUpdated)
    }
}
