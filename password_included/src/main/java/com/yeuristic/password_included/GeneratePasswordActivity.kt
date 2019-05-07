package com.yeuristic.password_included

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import com.google.android.play.core.splitcompat.SplitCompat
import com.yeuristic.dynamicmodulelib.toastAndLog
import com.yeuristic.passwordgenerator.copyToClipboard
import kotlinx.android.synthetic.main.activity_generate_password.*

class GeneratePasswordActivity : AppCompatActivity(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private val passwordGenerator = RandomPasswordGenerator()

    private val alphabetCapital = ('A'..'Z').toSet()
    private val alphabetNormal = ('a'..'z').toSet()
    private val numberChar = ('0'..'9').toSet()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_password)
        textViewLength.text = getString(R.string.password_length, getPasswordLength())
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewLength.text = getString(R.string.password_length, getPasswordLength())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        checkboxCapital.setOnCheckedChangeListener(this)
        checkboxNormal.setOnCheckedChangeListener(this)
        checkboxNumber.setOnCheckedChangeListener(this)

        buttonGenerate.setOnClickListener(this)
        buttonCopy.setOnClickListener(this)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.install(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (!isChecked && !checkboxCapital.isChecked && !checkboxNormal.isChecked && !checkboxNumber.isChecked) {
            if (buttonView == checkboxCapital)
                checkboxNormal.isChecked = true
            else
                checkboxCapital.isChecked = true
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            buttonGenerate -> generatePassword()
            buttonCopy -> copyPasswordToClipboard()
        }
    }

    private fun getPasswordLength() = seekBar.progress + 6

    private fun generatePassword() {
        val charSetList = emptyList<Set<Char>>().toMutableList()

        if (checkboxNumber.isChecked) {
            charSetList.add(numberChar)
        }
        if (checkboxNormal.isChecked) {
            charSetList.add(alphabetNormal)
        }
        if (checkboxCapital.isChecked || charSetList.isEmpty()) {
            charSetList.add(alphabetCapital)
        }
        textViewGeneratedPassword.text = passwordGenerator.generatePassword(getPasswordLength(), *charSetList.toTypedArray())
    }

    private fun copyPasswordToClipboard() {
        copyToClipboard("password", textViewGeneratedPassword.text)
        toastAndLog(textViewGeneratedPassword.text.toString())
    }
}
