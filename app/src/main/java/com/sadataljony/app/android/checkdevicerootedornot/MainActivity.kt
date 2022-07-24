package com.sadataljony.app.android.checkdevicerootedornot

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sadataljony.app.android.checkdevicerootedornot.databinding.ActivityMainBinding
import com.scottyab.rootbeer.RootBeer

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheckRootStatusUsingNativeCode.setOnClickListener {
            checkRootStatusUsingNativeCode("su")
        }

        binding.btnCheckRootStatusUsingLibrary.setOnClickListener {
            checkRootStatusUsingLibrary()
        }
    }

    // Check device rooted or not using native code
    private fun checkRootStatusUsingNativeCode(command: String) {
        var process: Process? = null
        try {
            process = Runtime.getRuntime().exec(command)
            showAlert("Rooted Device(using native code).")
        } catch (e: Exception) {
            showAlert("Non Rooted Device(using native code).")
        } finally {
            if (process != null) {
                try {
                    process.destroy()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    // Check device rooted or not using library
    private fun checkRootStatusUsingLibrary() {
        val rootBeer = RootBeer(this)//Initialized from dependency
        if (rootBeer.isRooted) {
            showAlert("Rooted Device(using library).")
        } else {
            showAlert("Non Rooted Device(using library).")
        }
    }

    // Show Alert Dialog
    private fun showAlert(msg: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Info")
        builder.setMessage(msg)
        builder.setNegativeButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}