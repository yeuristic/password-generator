package com.yeuristic.pin_ondemand

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.yeuristic.dynamicmodulelib.toastAndLog
import com.yeuristic.passwordgenerator.copyToClipboard
import kotlinx.android.synthetic.main.activity_generate_pin.*


class GeneratePinActivity : AppCompatActivity(), View.OnClickListener {

    private val pinGenerator : PinGenerator = RandomNumberPinGenerator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_pin)
        textViewLength.text = getString(R.string.pin_length, seekBar.progress + 1)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewLength.text = getString(R.string.pin_length, progress + 1)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        buttonGenerate.setOnClickListener(this)
        buttonCopy.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            buttonGenerate -> generatePin()
            buttonCopy -> copyPinToClipboard()
        }
    }

    private fun generatePin() {
        textViewGeneratedPassword.text = pinGenerator.generatePin(seekBar.progress + 1)
    }

    private fun copyPinToClipboard() {
        copyToClipboard("pin", textViewGeneratedPassword.text)
        toastAndLog(textViewGeneratedPassword.text.toString())
    }
}
