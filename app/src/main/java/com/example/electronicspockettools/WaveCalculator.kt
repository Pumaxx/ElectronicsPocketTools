package com.example.electronicspockettools

import android.icu.number.Precision
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isNotEmpty
import java.math.BigDecimal
import kotlin.math.pow

class WaveCalculator : Fragment() {

    private lateinit var EtLfeLengthInput: EditText
    private lateinit var EtLfeFrequencyInput: EditText
    private lateinit var EtLfeEnergyInput: EditText
    private lateinit var EtSpeedLengthInput: EditText
    private lateinit var EtSpeedFrequencyInput: EditText
    private lateinit var EtSpeedResultInput: EditText
    private lateinit var EtPeriodFrequencyInput: EditText
    private lateinit var EtPeriodResultInput: EditText

    private lateinit var SpLfeLength: Spinner
    private lateinit var SpLfeFrequency: Spinner
    private lateinit var SpLfeEnergy: Spinner
    private lateinit var SpSpeedLength: Spinner
    private lateinit var SpSpeedFrequency: Spinner
    private lateinit var SpSpeedResult: Spinner
    private lateinit var SpPeriodFrequency: Spinner
    private lateinit var SpPeriodResult: Spinner

    val nano : Double = 10.0.pow(-9)
    val micro : Double = 10.0.pow(-6)
    val milli : Double = 10.0.pow(-3)
    val cent : Double =  10.0.pow(-2)
    val normal :Double = 1.0
    val kilo : Double =  10.0.pow(3)
    val mega : Double =  10.0.pow(6)
    val giga : Double =  10.0.pow(9)
    val tera : Double =  10.0.pow(12)
    val msTokmh : Double = 3.6
    val msTocs : Double = 1/cent
    val speedOfLight : Double = 299792458.0
    val planckConstant : Double = 6.6261 * 10.0.pow(-34)
    val toElectronvolt : Double = 6.241509 * 10.0.pow(18)

    // default multipliers
    var LfeLenghtMultiplier :Double = normal
    var LfeFrequencyMultiplier:Double = normal
    var LfeEnergyMultiplier:Double = normal
    var SpeedLengthMultiplier:Double = normal
    var SpeedFrequencyMultiplier:Double = normal
    var SpeedResultMultiplier:Double = normal
    var PeriodFrequencyMultiplier:Double = normal
    var PeriodResultMultiplier:Double = normal

    fun setValueAfterSpinnerChange(editText: EditText, newMultiplierValue : Double, lastMultiplierValue : Double)
    {
        var newValue : Double = (editText.text.toString().toDouble() / newMultiplierValue) *  lastMultiplierValue
        editText.setText(newValue.toString())
    }

    fun setSpeedValueAfterSpinnerChange(editText: EditText, newMultiplierValue : Double, lastMultiplierValue : Double)
    {
        var newValue : Double = (editText.text.toString().toDouble() * newMultiplierValue) /  lastMultiplierValue
        editText.setText(newValue.toString())
    }

    fun setFrequencySpinnerLogic(multiplier : Double, editText: EditText,
                                 adapterView: AdapterView<*>?, position: Int) : Double
    {
        var lastMultiplierValue : Double = multiplier

        var newMultiplier = when(adapterView?.getItemAtPosition(position).toString()) {
            "nHz" -> nano
            "μHz" -> micro
            "mHz" -> milli
            "Hz" -> normal
            "kHz" -> kilo
            "MHz" -> mega
            "GHz" -> giga
            "THz" -> tera
            else -> { normal }
        }

        if(editText.text.isNotEmpty())
        {
            setValueAfterSpinnerChange(editText, newMultiplier, lastMultiplierValue)
        }

        return newMultiplier
    }

    fun setLengthSpinnerLogic(multiplier : Double, editText: EditText,
                                 adapterView: AdapterView<*>?, position: Int) : Double
    {
        var lastMultiplierValue : Double = multiplier

        var newMultiplier = when(adapterView?.getItemAtPosition(position).toString()) {
            "nm" -> nano
            "μm" -> micro
            "mm" -> milli
            "cm" -> cent
            "m" -> normal
            "km" -> kilo
            else -> { normal }
        }

        if(editText.text.isNotEmpty())
        {
            setValueAfterSpinnerChange(editText, newMultiplier, lastMultiplierValue)
        }

        return newMultiplier
    }

    fun correctValues(editText: EditText) : Boolean
    {
        return editText.text.isNotEmpty() && editText.text.toString().last().isDigit() && editText.text.toString().toDouble() != 0.0
    }

    fun speedResult(editTextLength: EditText, editTextFrequency: EditText)
    {
        var result : Double = EtSpeedLengthInput.text.toString().toDouble() * SpeedLengthMultiplier *
                              EtSpeedFrequencyInput.text.toString().toDouble() *
                              SpeedFrequencyMultiplier * SpeedResultMultiplier

        EtSpeedResultInput.setText(result.toString())
    }

    fun LfeCleanOthers()
    {
        EtSpeedLengthInput.setText("")
        EtSpeedFrequencyInput.setText("")
        EtSpeedResultInput.setText("")

        EtPeriodFrequencyInput.setText("")
        EtPeriodResultInput.setText("")
    }

    fun SpeedCleanOthers()
    {
        EtLfeLengthInput.setText("")
        EtLfeFrequencyInput.setText("")
        EtLfeEnergyInput.setText("")

        EtPeriodFrequencyInput.setText("")
        EtPeriodResultInput.setText("")
    }

    fun PeriodCleanOthers()
    {
        EtLfeLengthInput.setText("")
        EtLfeFrequencyInput.setText("")
        EtLfeEnergyInput.setText("")

        EtSpeedLengthInput.setText("")
        EtSpeedFrequencyInput.setText("")
        EtSpeedResultInput.setText("")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wave_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // EditTexts
        EtLfeLengthInput = view.findViewById(R.id.EtLfeLengthInput)
        EtLfeFrequencyInput = view.findViewById(R.id.EtLfeFrequencyInput)
        EtLfeEnergyInput = view.findViewById(R.id.EtLfeEnergyInput)
        EtSpeedLengthInput = view.findViewById(R.id.EtSpeedLengthInput)
        EtSpeedFrequencyInput = view.findViewById(R.id.EtSpeedFrequencyInput)
        EtSpeedResultInput = view.findViewById(R.id.EtSpeedResultInput)
        EtPeriodFrequencyInput = view.findViewById(R.id.EtPeriodFrequencyInput)
        EtPeriodResultInput = view.findViewById(R.id.EtPeriodResultInput)

        // Spinners
        SpLfeLength = view.findViewById(R.id.SpLfeLength)
            SpLfeLength.setSelection(4) // default set to meters
        SpLfeFrequency = view.findViewById(R.id.SpLfeFrequency)
            SpLfeFrequency.setSelection(3) // default set to hertz
        SpLfeEnergy = view.findViewById(R.id.SpLfeEnergy)
            SpLfeEnergy.setSelection(3) // default set to electron_volt
        SpSpeedLength = view.findViewById(R.id.SpSpeedLength)
            SpSpeedLength.setSelection(4) // default set to meters
        SpSpeedFrequency = view.findViewById(R.id.SpSpeedFrequency)
            SpSpeedFrequency.setSelection(3) // default set to hertz
        SpSpeedResult = view.findViewById(R.id.SpSpeedResult)
            SpSpeedResult.setSelection(1) // meters per second
        SpPeriodFrequency = view.findViewById(R.id.SpPeriodFrequency)
            SpPeriodFrequency.setSelection(3) // default set to hertz
        SpPeriodResult = view.findViewById(R.id.SpPeriodResult)
            SpPeriodResult.setSelection(3) // default set to seconds

        // EditTexts TextWatchers
        EtLfeLengthInput.addTextChangedListener(object : TextWatcher
        {
             var frequency : Double = 0.0
             var energy : Double = 0.0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if(EtLfeLengthInput.hasFocus())
                {
                    if(correctValues(EtLfeLengthInput))
                    {
                        frequency = (speedOfLight /
                                (EtLfeLengthInput.text.toString().toDouble() * LfeLenghtMultiplier)) /
                                LfeFrequencyMultiplier

                        energy = ((planckConstant * speedOfLight * toElectronvolt) /
                                (EtLfeLengthInput.text.toString().toDouble() * LfeLenghtMultiplier)) /
                                LfeEnergyMultiplier

                        EtLfeFrequencyInput.setText(frequency.toString())
                        EtLfeEnergyInput.setText(energy.toString())
                    }
                    else
                    {
                        EtLfeFrequencyInput.setText("")
                        EtLfeEnergyInput.setText("")
                    }
                    LfeCleanOthers()
                }
            }
        })

        EtLfeFrequencyInput.addTextChangedListener(object : TextWatcher
        {
            var length : Double = 0.0
            var energy : Double = 0.0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if(EtLfeFrequencyInput.hasFocus())
                {
                    if(correctValues(EtLfeFrequencyInput))
                    {
                        length = (speedOfLight /
                                (EtLfeFrequencyInput.text.toString().toDouble() * LfeFrequencyMultiplier)) /
                                LfeLenghtMultiplier

                        energy = (planckConstant * toElectronvolt * EtLfeFrequencyInput.text.toString().toDouble() * LfeFrequencyMultiplier) /
                                LfeEnergyMultiplier

                        EtLfeLengthInput.setText(length.toString())
                        EtLfeEnergyInput.setText(energy.toString())
                    }
                    else
                    {
                        EtLfeLengthInput.setText("")
                        EtLfeEnergyInput.setText("")
                    }
                    LfeCleanOthers()
                }
            }
        })

        EtLfeEnergyInput.addTextChangedListener(object : TextWatcher
        {
            var length : Double = 0.0
            var frequency : Double = 0.0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if(EtLfeEnergyInput.hasFocus())
                {
                    if (correctValues(EtLfeEnergyInput))
                    {
                        length = ((planckConstant * speedOfLight * toElectronvolt) /
                                (EtLfeEnergyInput.text.toString().toDouble() * LfeLenghtMultiplier)) /
                                LfeEnergyMultiplier

                        frequency = (EtLfeEnergyInput.text.toString().toDouble() * LfeEnergyMultiplier) /
                                (planckConstant * toElectronvolt * LfeFrequencyMultiplier)

                        EtLfeLengthInput.setText(length.toString())
                        EtLfeFrequencyInput.setText(frequency.toString())
                    }
                    else
                    {
                        EtLfeLengthInput.setText("")
                        EtLfeFrequencyInput.setText("")
                    }
                    LfeCleanOthers()
                }
            }
        })

        EtSpeedLengthInput.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if(EtSpeedLengthInput.hasFocus())
                {
                    if(EtSpeedFrequencyInput.text.isNotEmpty())
                    {
                        if(correctValues(EtSpeedLengthInput) && correctValues(EtSpeedFrequencyInput))
                        {
                            speedResult(EtSpeedLengthInput, EtSpeedFrequencyInput)
                        }
                        else
                        {
                            EtSpeedResultInput.setText("")
                        }
                    }
                    SpeedCleanOthers()
                }
            }
        })

        EtSpeedFrequencyInput.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if(EtSpeedFrequencyInput.hasFocus())
                {
                    if(EtSpeedLengthInput.text.isNotEmpty())
                    {
                        if(correctValues(EtSpeedFrequencyInput) && correctValues(EtSpeedLengthInput))
                        {
                            speedResult(EtSpeedLengthInput, EtSpeedFrequencyInput)
                        }
                        else
                        {
                            EtSpeedResultInput.setText("")
                        }
                    }
                    SpeedCleanOthers()
                }
            }
        })

        EtPeriodFrequencyInput.addTextChangedListener(object : TextWatcher
        {
            var result : Double = 0.0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if(EtPeriodFrequencyInput.hasFocus())
                {
                    if(correctValues(EtPeriodFrequencyInput))
                    {
                        result = (1 / (EtPeriodFrequencyInput.text.toString().toDouble() * PeriodFrequencyMultiplier)) /
                                PeriodResultMultiplier

                        EtPeriodResultInput.setText(result.toString())
                    }
                    else
                    {
                        EtPeriodResultInput.setText("")
                    }
                    PeriodCleanOthers()
                }
            }
        })

        // Spinners onItemSelectedListeners
        SpLfeLength.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {

                LfeLenghtMultiplier = setLengthSpinnerLogic(LfeLenghtMultiplier, EtLfeLengthInput,
                                                            adapterView, position)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }

        SpLfeFrequency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                SpLfeLength.requestFocus()
                LfeFrequencyMultiplier = setFrequencySpinnerLogic(LfeFrequencyMultiplier, EtLfeFrequencyInput,
                                                                  adapterView, position)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }

        SpLfeEnergy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                SpLfeLength.requestFocus()
                var lastMultiplierValue : Double = LfeEnergyMultiplier

                LfeEnergyMultiplier = when(adapterView?.getItemAtPosition(position).toString()) {
                    "neV" -> nano
                    "μeV" -> micro
                    "meV" -> milli
                    "eV" -> normal
                    "keV" -> kilo
                    "MeV" -> mega
                    else -> { normal }
                }

                if(EtLfeEnergyInput.text.isNotEmpty())
                {
                    setValueAfterSpinnerChange(EtLfeEnergyInput, LfeEnergyMultiplier, lastMultiplierValue)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }

        SpSpeedLength.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                SpeedLengthMultiplier = setLengthSpinnerLogic(SpeedLengthMultiplier, EtSpeedLengthInput,
                                                              adapterView, position)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }

        SpSpeedFrequency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                SpeedFrequencyMultiplier = setFrequencySpinnerLogic(SpeedFrequencyMultiplier, EtSpeedFrequencyInput,
                                                                    adapterView, position)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }

        SpSpeedResult.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                var lastMultiplierValue : Double = SpeedResultMultiplier

                SpeedResultMultiplier = when(adapterView?.getItemAtPosition(position).toString()) {
                    "cm/s" -> msTocs
                    "m/s" -> normal
                    "km/h" -> msTokmh
                    else -> { normal }
                }

                if(EtSpeedResultInput.text.isNotEmpty())
                {
                    setSpeedValueAfterSpinnerChange(EtSpeedResultInput, SpeedResultMultiplier, lastMultiplierValue)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }

        SpPeriodFrequency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                PeriodFrequencyMultiplier = setFrequencySpinnerLogic(PeriodFrequencyMultiplier, EtPeriodFrequencyInput,
                                                                     adapterView, position)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }

        SpPeriodResult.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                var lastMultiplierValue : Double = PeriodResultMultiplier
                
                PeriodResultMultiplier = when(adapterView?.getItemAtPosition(position).toString()) {
                    "ns" -> nano
                    "μs" -> micro
                    "ms" -> milli
                    "s" -> normal
                    else -> { normal }
                }
                
                if(EtPeriodResultInput.text.isNotEmpty())
                {
                    setValueAfterSpinnerChange(EtPeriodResultInput, PeriodResultMultiplier, lastMultiplierValue)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }
    }
}