package com.yeuristic.passwordgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.yeuristic.dynamicmodulelib.alreadyDownload
import com.yeuristic.dynamicmodulelib.launchActivity
import com.yeuristic.dynamicmodulelib.toastAndLog
import kotlinx.android.synthetic.main.activity_main.*

fun AppCompatActivity.copyToClipboard(label: String = "default", text: CharSequence) {
    if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard?.setPrimaryClip(clip)
    }
}

class MainActivity : AppCompatActivity(), View.OnClickListener {
    // Creates an instance of SplitInstallManager.
    private val splitInstallManager: SplitInstallManager by lazy {
        SplitInstallManagerFactory.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("dfm", "app main")

        buttonGeneratePassword.setOnClickListener(this)
        buttonGeneratePin.setOnClickListener(this)
        buttonToastString.setOnClickListener(this)
        buttonToastNumber.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            buttonGeneratePassword -> loadAndLaunch(PasswordModule)
            buttonGeneratePin -> loadAndLaunch(PinModule)
            buttonToastString -> {
                toastAndLog(Class.forName("com.yeuristic.password_included.RandomString").getConstructor().newInstance().toString())
            }
            buttonToastNumber -> {
                toastAndLog(Class.forName("com.yeuristic.pin_ondemand.RandomNumber").getConstructor().newInstance().toString())
            }
        }
    }

    private fun loadAndLaunch(moduleData: DynamicModuleData) {
        if (splitInstallManager alreadyDownload moduleData.moduleName)
            launchActivity(moduleData.landingPageActivityName)
        else {
        }
    }
}
