package com.example.electronicspockettools

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.math.BigInteger
import kotlin.math.pow

class ResistorRecognition : Fragment() {

    // Buttons first row
    private lateinit var BtFirstValueZero : Button
    private lateinit var BtFirstValueOne : Button
    private lateinit var BtFirstValueTwo : Button
    private lateinit var BtFirstValueThree : Button
    private lateinit var BtFirstValueFour : Button
    private lateinit var BtFirstValueFive : Button
    private lateinit var BtFirstValueSix : Button
    private lateinit var BtFirstValueSeven : Button
    private lateinit var BtFirstValueEight : Button
    private lateinit var BtFirstValueNine : Button

    // Buttons second row
    private lateinit var BtSecondValueZero : Button
    private lateinit var BtSecondValueOne : Button
    private lateinit var BtSecondValueTwo : Button
    private lateinit var BtSecondValueThree : Button
    private lateinit var BtSecondValueFour : Button
    private lateinit var BtSecondValueFive : Button
    private lateinit var BtSecondValueSix : Button
    private lateinit var BtSecondValueSeven : Button
    private lateinit var BtSecondValueEight : Button
    private lateinit var BtSecondValueNine : Button

    // Buttons multiply row
    private lateinit var BtMultiplyOneOhm : Button
    private lateinit var BtMultiplyTenOhm : Button
    private lateinit var BtMultiplyOneHundredOhm : Button
    private lateinit var BtMultiplyOneKiloOhm : Button
    private lateinit var BtMultiplyTenKiloOhm : Button
    private lateinit var BtMultiplyOneHundredKiloOhm : Button
    private lateinit var BtMultiplyOneMegaOhm : Button
    private lateinit var BtMultiplyTenMegaOhm : Button
    private lateinit var BtMultiplyOneHundredMegaOhm : Button
    private lateinit var BtMultiplyOneGigaOhm : Button

    // Buttons tolerance row
    private lateinit var BtToleranceOnePer : Button
    private lateinit var BtToleranceTwoPer : Button
    private lateinit var BtTolerancePointFiftyPer : Button
    private lateinit var BtTolerancePointTwentyFivePer : Button
    private lateinit var BtTolerancePointTenPer : Button
    private lateinit var BtToleranceFivePer : Button
    private lateinit var BtToleranceTenPer : Button

    // Stripes
    private lateinit var LlFirstStripe : LinearLayout
    private lateinit var LlSecondStripe : LinearLayout
    private lateinit var LlThirdStripe : LinearLayout
    private lateinit var LlFourthStripe : LinearLayout

    // Rest
    private lateinit var TvResistorResult : TextView
    private lateinit var EtResistanceInput : EditText
    private lateinit var SpResistance : Spinner

    private var firstValue : Int = 0
    private var secondValue : Int = 0
    private var multiplyValue : Long = 1
    private var toleranceValue : String = "±1%"

    private fun calculateResult()
    {
        var result : Long = ((firstValue * 10) + secondValue) * multiplyValue
        var resultString : String = when{
            result >= 1000000000 -> (result/1000000000).toString() + "GΩ"
            result >= 1000000 -> (result/1000000).toString() + "MΩ"
            result >= 1000 -> (result/1000).toString() + "kΩ"
            else -> result.toString() + "Ω"
        }

        TvResistorResult.text = "$resultString $toleranceValue"
    }

    private fun setFirstValueAndCalculate(setValue : Int)
    {
        firstValue = setValue
        calculateResult()
        clearInput()
    }

    private fun setSecondValueAndCalculate(setValue : Int)
    {
        secondValue = setValue
        calculateResult()
        clearInput()
    }

    private fun setMultiplyValueAndCalculate(setValue : Long)
    {
        multiplyValue = setValue
        calculateResult()
        clearInput()
    }

    private fun setToleranceValueAndCalculate(setValue : String)
    {
        toleranceValue = setValue
        calculateResult()
    }

    private fun setStripeColor(stripe : LinearLayout, color: Int )
    {
        stripe.setBackgroundResource(color)
    }

    private fun calculateInput()
    {
        val input : Int = EtResistanceInput.text.toString().toInt()
        firstValue = (input / 10) % 10
        secondValue = input % 10
        calculateResult()
        calculateFirstSecondStripeColor()
    }

    private fun calculateColor(number : Int, strip : LinearLayout)
    {
        when (number)
        {
            0 -> strip.setBackgroundResource(R.color.black)
            1 -> strip.setBackgroundResource(R.color.brown)
            2 -> strip.setBackgroundResource(R.color.red)
            3 -> strip.setBackgroundResource(R.color.orange)
            4 -> strip.setBackgroundResource(R.color.yellow)
            5 -> strip.setBackgroundResource(R.color.green)
            6 -> strip.setBackgroundResource(R.color.blue)
            7 -> strip.setBackgroundResource(R.color.purple)
            8 -> strip.setBackgroundResource(R.color.grey)
            9 -> strip.setBackgroundResource(R.color.white)
        }
    }

    private fun calculateFirstSecondStripeColor()
    {
        calculateColor(firstValue, LlFirstStripe)
        calculateColor(secondValue, LlSecondStripe)
    }

    private fun correctInputValues() : Boolean
    {
        return  EtResistanceInput.text.isNotEmpty() &&
                EtResistanceInput.text.toString().toInt() >= 0 &&
                EtResistanceInput.text.toString().toInt() <= 99
    }

    private fun reset()
    {
        setStripeColor(LlFirstStripe, R.color.black)
        setStripeColor(LlSecondStripe, R.color.black)
        setStripeColor(LlThirdStripe, R.color.black)

        firstValue = 0
        secondValue = 0
        multiplyValue = 1L
        calculateResult()
    }

    private fun calculateMultiplier()
    {
        when(SpResistance.selectedItem.toString())
        {
            "Ω" -> {
                multiplyValue = 1
                setStripeColor(LlThirdStripe, R.color.black)
            }
            "kΩ" -> {
                multiplyValue = 1000
                setStripeColor(LlThirdStripe, R.color.orange)
            }
            "MΩ" -> {
                multiplyValue = 1000000
                setStripeColor(LlThirdStripe, R.color.blue)
            }
            "GΩ" -> {
                multiplyValue = 1000000000
                setStripeColor(LlThirdStripe, R.color.white)
            }
        }
    }

    private fun clearInput()
    {
        EtResistanceInput.clearFocus()
        EtResistanceInput.text.clear()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resistor_recognition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Buttons first row
        BtFirstValueZero = view.findViewById(R.id.BtFirstValueZero)
        BtFirstValueOne = view.findViewById(R.id.BtFirstValueOne)
        BtFirstValueTwo = view.findViewById(R.id.BtFirstValueTwo)
        BtFirstValueThree = view.findViewById(R.id.BtFirstValueThree)
        BtFirstValueFour = view.findViewById(R.id.BtFirstValueFour)
        BtFirstValueFive = view.findViewById(R.id.BtFirstValueFive)
        BtFirstValueSix = view.findViewById(R.id.BtFirstValueSix)
        BtFirstValueSeven = view.findViewById(R.id.BtFirstValueSeven)
        BtFirstValueEight = view.findViewById(R.id.BtFirstValueEight)
        BtFirstValueNine = view.findViewById(R.id.BtFirstValueNine)

        // Buttons second row
        BtSecondValueZero = view.findViewById(R.id.BtSecondValueZero)
        BtSecondValueOne = view.findViewById(R.id.BtSecondValueOne)
        BtSecondValueTwo = view.findViewById(R.id.BtSecondValueTwo)
        BtSecondValueThree = view.findViewById(R.id.BtSecondValueThree)
        BtSecondValueFour = view.findViewById(R.id.BtSecondValueFour)
        BtSecondValueFive = view.findViewById(R.id.BtSecondValueFive)
        BtSecondValueSix = view.findViewById(R.id.BtSecondValueSix)
        BtSecondValueSeven = view.findViewById(R.id.BtSecondValueSeven)
        BtSecondValueEight = view.findViewById(R.id.BtSecondValueEight)
        BtSecondValueNine = view.findViewById(R.id.BtSecondValueNine)

        // Buttons multiply row
        BtMultiplyOneOhm = view.findViewById(R.id.BtMultiplyOneOhm)
        BtMultiplyTenOhm = view.findViewById(R.id.BtMultiplyTenOhm)
        BtMultiplyOneHundredOhm = view.findViewById(R.id.BtMultiplyOneHundredOhm)
        BtMultiplyOneKiloOhm = view.findViewById(R.id.BtMultiplyOneKiloOhm)
        BtMultiplyTenKiloOhm = view.findViewById(R.id.BtMultiplyTenKiloOhm)
        BtMultiplyOneHundredKiloOhm = view.findViewById(R.id.BtMultiplyOneHundredKiloOhm)
        BtMultiplyOneMegaOhm = view.findViewById(R.id.BtMultiplyOneMegaOhm)
        BtMultiplyTenMegaOhm = view.findViewById(R.id.BtMultiplyTenMegaOhm)
        BtMultiplyOneHundredMegaOhm = view.findViewById(R.id.BtMultiplyOneHundredMegaOhm)
        BtMultiplyOneGigaOhm = view.findViewById(R.id.BtMultiplyOneGigaOhm)

        // Buttons tolerance row
        BtToleranceOnePer = view.findViewById(R.id.BtToleranceOnePer)
        BtToleranceTwoPer = view.findViewById(R.id.BtToleranceTwoPer)
        BtTolerancePointFiftyPer = view.findViewById(R.id.BtTolerancePointFiftyPer)
        BtTolerancePointTwentyFivePer = view.findViewById(R.id.BtTolerancePointTwentyFivePer)
        BtTolerancePointTenPer = view.findViewById(R.id.BtTolerancePointTenPer)
        BtToleranceFivePer = view.findViewById(R.id.BtToleranceFivePer)
        BtToleranceTenPer = view.findViewById(R.id.BtToleranceTenPer)

        // Stripes
        LlFirstStripe = view.findViewById(R.id.LlFirstStripe)
        LlSecondStripe = view.findViewById(R.id.LlSecondStripe)
        LlThirdStripe = view.findViewById(R.id.LlThirdStripe)
        LlFourthStripe = view.findViewById(R.id.LlFourthStripe)

        // Rest
        TvResistorResult = view.findViewById(R.id.TvResistorResult)
        EtResistanceInput = view.findViewById(R.id.EtResistanceInput)
        SpResistance = view.findViewById(R.id.SpResistance)

        // First row onClickListeners
        BtFirstValueZero.setOnClickListener {
            setStripeColor(LlFirstStripe, R.color.black)
            setFirstValueAndCalculate(0)
        }
        BtFirstValueOne.setOnClickListener {
            setStripeColor(LlFirstStripe, R.color.brown)
            setFirstValueAndCalculate(1)
        }
        BtFirstValueTwo.setOnClickListener {
            setStripeColor(LlFirstStripe, R.color.red)
            setFirstValueAndCalculate(2)
        }
        BtFirstValueThree.setOnClickListener {
            setStripeColor(LlFirstStripe, R.color.orange)
            setFirstValueAndCalculate(3)
        }
        BtFirstValueFour.setOnClickListener {
            setStripeColor(LlFirstStripe, R.color.yellow)
            setFirstValueAndCalculate(4)
        }
        BtFirstValueFive.setOnClickListener {
            setStripeColor(LlFirstStripe, R.color.green)
            setFirstValueAndCalculate(5)
        }
        BtFirstValueSix.setOnClickListener {
            setStripeColor(LlFirstStripe, R.color.blue)
            setFirstValueAndCalculate(6)
        }
        BtFirstValueSeven.setOnClickListener {
            setStripeColor(LlFirstStripe, R.color.purple)
            setFirstValueAndCalculate(7)
        }
        BtFirstValueEight.setOnClickListener {
            setStripeColor(LlFirstStripe, R.color.grey)
            setFirstValueAndCalculate(8)
        }
        BtFirstValueNine.setOnClickListener {
            setStripeColor(LlFirstStripe, R.color.white)
            setFirstValueAndCalculate(9)
        }

        // Second row onClickListeners
        BtSecondValueZero.setOnClickListener {
            setStripeColor(LlSecondStripe, R.color.black)
            setSecondValueAndCalculate(0)
        }
        BtSecondValueOne.setOnClickListener {
            setStripeColor(LlSecondStripe, R.color.brown)
            setSecondValueAndCalculate(1)
        }
        BtSecondValueTwo.setOnClickListener {
            setStripeColor(LlSecondStripe, R.color.red)
            setSecondValueAndCalculate(2)
        }
        BtSecondValueThree.setOnClickListener {
            setStripeColor(LlSecondStripe, R.color.orange)
            setSecondValueAndCalculate(3)
        }
        BtSecondValueFour.setOnClickListener {
            setStripeColor(LlSecondStripe, R.color.yellow)
            setSecondValueAndCalculate(4)
        }
        BtSecondValueFive.setOnClickListener {
            setStripeColor(LlSecondStripe, R.color.green)
            setSecondValueAndCalculate(5)
        }
        BtSecondValueSix.setOnClickListener {
            setStripeColor(LlSecondStripe, R.color.blue)
            setSecondValueAndCalculate(6)
        }
        BtSecondValueSeven.setOnClickListener {
            setStripeColor(LlSecondStripe, R.color.purple)
            setSecondValueAndCalculate(7)
        }
        BtSecondValueEight.setOnClickListener {
            setStripeColor(LlSecondStripe, R.color.grey)
            setSecondValueAndCalculate(8)
        }
        BtSecondValueNine.setOnClickListener {
            setStripeColor(LlSecondStripe, R.color.white)
            setSecondValueAndCalculate(9)
        }

        // Multiply row onClickListeners
        BtMultiplyOneOhm.setOnClickListener {
            setStripeColor(LlThirdStripe, R.color.black)
            setMultiplyValueAndCalculate(1)
        }
        BtMultiplyTenOhm.setOnClickListener {
            setStripeColor(LlThirdStripe, R.color.brown)
            setMultiplyValueAndCalculate(10)
        }
        BtMultiplyOneHundredOhm.setOnClickListener {
            setStripeColor(LlThirdStripe, R.color.red)
            setMultiplyValueAndCalculate(100)
        }
        BtMultiplyOneKiloOhm.setOnClickListener {
            setStripeColor(LlThirdStripe, R.color.orange)
            setMultiplyValueAndCalculate(10.0.pow(3).toLong())
        }
        BtMultiplyTenKiloOhm.setOnClickListener {
            setStripeColor(LlThirdStripe, R.color.yellow)
            setMultiplyValueAndCalculate(10.0.pow(4).toLong())
        }
        BtMultiplyOneHundredKiloOhm.setOnClickListener {
            setStripeColor(LlThirdStripe, R.color.green)
            setMultiplyValueAndCalculate(10.0.pow(5).toLong())
        }
        BtMultiplyOneMegaOhm.setOnClickListener {
            setStripeColor(LlThirdStripe, R.color.blue)
            setMultiplyValueAndCalculate(10.0.pow(6).toLong())
        }
        BtMultiplyTenMegaOhm.setOnClickListener {
            setStripeColor(LlThirdStripe, R.color.purple)
            setMultiplyValueAndCalculate(10.0.pow(7).toLong())
        }
        BtMultiplyOneHundredMegaOhm.setOnClickListener {
            setStripeColor(LlThirdStripe, R.color.grey)
            setMultiplyValueAndCalculate(10.0.pow(8).toLong())
        }
        BtMultiplyOneGigaOhm.setOnClickListener {
            setStripeColor(LlThirdStripe, R.color.white)
            setMultiplyValueAndCalculate(10.0.pow(9).toLong())
        }

        // Tolerance row onClickListeners
        BtToleranceOnePer.setOnClickListener {
            setStripeColor(LlFourthStripe, R.color.brown)
            setToleranceValueAndCalculate("±1%")
        }
        BtToleranceTwoPer.setOnClickListener {
            setStripeColor(LlFourthStripe, R.color.red)
            setToleranceValueAndCalculate("±2%")
        }
        BtTolerancePointFiftyPer.setOnClickListener {
            setStripeColor(LlFourthStripe, R.color.green)
            setToleranceValueAndCalculate("±0.50%")
        }
        BtTolerancePointTwentyFivePer.setOnClickListener {
            setStripeColor(LlFourthStripe, R.color.blue)
            setToleranceValueAndCalculate("±0.25%")
        }
        BtTolerancePointTenPer.setOnClickListener {
            setStripeColor(LlFourthStripe, R.color.purple)
            setToleranceValueAndCalculate("±0.10%")
        }
        BtToleranceFivePer.setOnClickListener {
            setStripeColor(LlFourthStripe, R.drawable.gold_gradient)
            setToleranceValueAndCalculate("±5%")
        }
        BtToleranceTenPer.setOnClickListener {
            setStripeColor(LlFourthStripe, R.drawable.silver_gradient)
            setToleranceValueAndCalculate("±10%")
        }

        // EditText onTextChangeListener
        EtResistanceInput.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if(EtResistanceInput.hasFocus())
                {
                    if(correctInputValues())
                    {
                        calculateMultiplier()
                        calculateInput()
                    }
                    else { reset() }
                }
            }
        })

        SpResistance.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                SpResistance.requestFocus()
                if(correctInputValues())
                {
                    when(adapterView?.getItemAtPosition(position).toString())
                    {
                        "Ω" -> {
                            multiplyValue = 1
                            setStripeColor(LlThirdStripe, R.color.black)
                        }
                        "kΩ" -> {
                            multiplyValue = 1000
                            setStripeColor(LlThirdStripe, R.color.orange)
                        }
                        "MΩ" -> {
                            multiplyValue = 1000000
                            setStripeColor(LlThirdStripe, R.color.blue)
                        }
                        "GΩ" -> {
                            multiplyValue = 1000000000
                            setStripeColor(LlThirdStripe, R.color.white)
                        }
                    }

                    calculateInput()
                }
                else
                {
                    reset()
                    SpResistance.setSelection(0)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }

    }
}