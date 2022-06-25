package com.androidpositive.ghubstore.data.interactors

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.core.content.FileProvider
import java.io.File

interface InstallApkInteractor {
    fun installAPK(file: File, context: Context)

    companion object {
        fun create(): InstallApkInteractor = InstallApkInteractorImpl()
    }
}

private class InstallApkInteractorImpl : InstallApkInteractor {

    override fun installAPK(file: File, context: Context) {
        val intent: Intent = createIntent(file, context)
        intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
        intent.putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME, "com.android.vending")
        context.startActivity(intent)
    }

    private fun createIntent(file: File, context: Context): Intent {
        return if (VERSION.SDK_INT >= VERSION_CODES.N) {
            Intent(Intent.ACTION_INSTALL_PACKAGE).apply {
                data = getUri(file, context)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        } else {
            Intent(Intent.ACTION_VIEW).apply {
                setDataAndTypeAndNormalize(
                    Uri.fromFile(file), "application/vnd.android.package-archive"
                )
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
    }

    private fun getUri(file: File, context: Context): Uri {
        return FileProvider.getUriForFile(context, "sksa.aa.customapps.fileProvider", file)
    }
}
