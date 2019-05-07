package com.yeuristic.dynamicmodulelib

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import java.lang.Exception


fun Activity.launchActivity(classFullName: String) {
    startActivity(Intent().setClassName(BuildConfig.APPLICATION_ID, classFullName))
}

fun Context.toastAndLog(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    Log.d(javaClass.simpleName, text)
}

fun SplitInstallManager.downloadModule(
    activity: Activity,
    moduleName: String,
    blockStateUpdated: (SplitInstallSessionState) -> Unit
) {
    try {
        // Creates a request to install a module.
        val request =
            SplitInstallRequest
                .newBuilder()
                // You can download multiple on demand modules per
                // request by invoking the following method for each
                // module you want to install.
                .addModule(moduleName)
                .build()

        // Initializes a variable to later track the session ID for a given request.
        var mySessionId = 0

        // Creates a listener for request status updates.
        val listener = SplitInstallStateUpdatedListener { state ->
            if (state.sessionId() == mySessionId) {
                // Read the status of the request to handle the state update.

                if (state.status() == SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION){
                    /*
                      This may occur when attempting to download a sufficiently large module.
                      In order to see this, the application has to be uploaded to the Play Store.
                      Then features can be requested until the confirmation path is triggered.
                     */
                    val CONFIRMATION_REQUEST_CODE = 0
                    startConfirmationDialogForResult(state, activity, CONFIRMATION_REQUEST_CODE)
                }
                blockStateUpdated(state)
            }
        }

        // Registers the listener.
        registerListener(listener)

        this
            // Submits the request to install the module through the
            // asynchronous startInstall() task. Your app needs to be
            // in the foreground to submit the request.
            .startInstall(request)
            // You should also be able to gracefully handle
            // request state changes and errors. To learn more, go to
            // the section about how to Monitor the request state.
            .addOnSuccessListener { sessionId -> mySessionId = sessionId }
            .addOnFailureListener { exception ->
                val splitInstallException = exception as SplitInstallException
                val message = (splitInstallException.message ?: "") + " " + splitInstallException.errorCode
                activity.toastAndLog(message)
            }.addOnCompleteListener { unregisterListener(listener) }
    } catch (ex : Exception) {
        activity.toastAndLog(ex.message ?: "Error")
    }
}

infix fun SplitInstallManager.alreadyDownload(moduleName: String): Boolean {
    return moduleName in installedModules
}