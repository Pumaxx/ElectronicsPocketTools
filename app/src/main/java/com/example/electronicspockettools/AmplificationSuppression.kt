package com.example.electronicspockettools

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kotlin.math.log10

class AmplificationSuppression : Fragment() {

    private lateinit var EtVoltCurrInput : EditText
    private lateinit var EtVoltCurrOutput : EditText
    private lateinit var EtVoltCurrDimLess : EditText
    private lateinit var EtVoltCurrDb : EditText
    private lateinit var EtPowerInput : EditText
    private lateinit var EtPowerOutput : EditText
    private lateinit var EtPowerDimLess : EditText
    private lateinit var EtPowerDb : EditText

    private fun correctValues(editText: EditText) : Boolean
    {
        return editText.text.isNotEmpty() && editText.text.toString().last().isDigit() && editText.text.toString().toDouble() != 0.0
    }

    fun countResults(input: EditText, output: EditText, dimLess: EditText, dB: EditText, type: String = "VoltCurr")
    {
        var type : Double = if(type == "VoltCurr")  20.0 else 10.0
        if(correctValues(input) && correctValues(output))
        {
            var dimLessResult : Double = output.text.toString().toDouble() / input.text.toString().toDouble()
            var dbResult : Double =  type * log10(dimLessResult)

            dimLess.setText(dimLessResult.toString())
            dB.setText(dbResult.toString())
        }
        else
        {
            dimLess.setText("")
            dB.setText("")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_amplification_suppression, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // EditTexts
        EtVoltCurrInput = view.findViewById(R.id.EtVoltCurrInput)
        EtVoltCurrOutput = view.findViewById(R.id.EtVoltCurrOutput)
        EtVoltCurrDimLess = view.findViewById(R.id.EtVoltCurrDimLess)
        EtVoltCurrDb = view.findViewById(R.id.EtVoltCurrDb)
        EtPowerInput = view.findViewById(R.id.EtPowerInput)
        EtPowerOutput = view.findViewById(R.id.EtPowerOutput)
        EtPowerDimLess = view.findViewById(R.id.EtPowerDimLess)
        EtPowerDb = view.findViewById(R.id.EtPowerDb)

        EtVoltCurrInput.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                countResults(EtVoltCurrInput, EtVoltCurrOutput, EtVoltCurrDimLess, EtVoltCurrDb)
            }
        })

        EtVoltCurrOutput.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                countResults(EtVoltCurrInput, EtVoltCurrOutput, EtVoltCurrDimLess, EtVoltCurrDb)
            }
        })

        EtPowerInput.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                countResults(EtPowerInput, EtPowerOutput, EtPowerDimLess, EtPowerDb, "Power")
            }
        })

        EtPowerOutput.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                countResults(EtPowerInput, EtPowerOutput, EtPowerDimLess, EtPowerDb, "Power")
            }
        })
    }

}