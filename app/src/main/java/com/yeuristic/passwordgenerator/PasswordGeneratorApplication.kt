package com.yeuristic.passwordgenerator

import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.yeuristic.dynamicmodulelib.toastAndLog

class PasswordGeneratorApplication : SplitCompatApplication() {
    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
        context?.toastAndLog("attachBaseContext")
    }
}