import android.Manifest
import android.content.Context
import android.util.Log
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class PermissionManager {

    private var permissionCallback: ((Boolean) -> Unit)? = null
    private var tokens: PermissionToken? = null

    fun isGranted(context: Context, callback: (Boolean) -> Unit) {
        permissionCallback = callback

        Dexter.withContext(context).withPermissions(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(permission: MultiplePermissionsReport?) {
                val granted = permission?.areAllPermissionsGranted() == true
                permissionCallback?.invoke(granted)
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                tokens = p1
            }
        }
        ).withErrorListener {
            Log.d("TAG", "isGranted:${it.name} ")
        }.check()
    }

     fun requestPermission() {
        tokens?.continuePermissionRequest()
    }
}
