package com.example.electronicspockettools

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import kotlin.math.abs
import kotlin.math.pow

class PreferredValues : Fragment() {

    // Values
    private lateinit var TvE3Value : TextView
    private lateinit var TvE6Value : TextView
    private lateinit var TvE12Value : TextView
    private lateinit var TvE24Value : TextView
    private lateinit var TvE48Value : TextView
    private lateinit var TvE96Value : TextView
    private lateinit var TvE192Value : TextView

    // Errors
    private lateinit var TvE3ValueErr : TextView
    private lateinit var TvE6ValueErr : TextView
    private lateinit var TvE12ValueErr : TextView
    private lateinit var TvE24ValueErr : TextView
    private lateinit var TvE48ValueErr : TextView
    private lateinit var TvE96ValueErr : TextView
    private lateinit var TvE192ValueErr : TextView

    // Errors %
    private lateinit var TvE3Err : TextView
    private lateinit var TvE6Err : TextView
    private lateinit var TvE12Err : TextView
    private lateinit var TvE24Err : TextView
    private lateinit var TvE48Err : TextView
    private lateinit var TvE96Err : TextView
    private lateinit var TvE192Err : TextView

    private lateinit var EtPrefValuesIdeal : EditText

    val E3Series =  listOf(10.0F, 22.0F, 47.0F)
    val E6Series =  listOf(10.0F, 15.0F, 22.0F, 33.0F, 47.0F, 68.0F)
    val E12Series =  listOf(10.0F, 12.0F, 15.0F, 18.0F, 22.0F, 27.0F, 33.0F, 39.0F, 47.0F, 56.0F, 68.0F, 82.0F)
    val E24Series =  listOf(10.0F, 11.0F, 12.0F, 13.0F, 15.0F, 16.0F, 18.0F, 20.0F, 22.0F, 24.0F, 27.0F, 30.0F,
                            33.0F, 36.0F, 39.0F, 43.0F, 47.0F, 51.0F, 56.0F, 62.0F, 68.0F, 75.0F, 82.0F, 91.0F)

    val E48Series =  listOf(100.0F, 105.0F, 110.0F, 115.0F, 121.0F, 127.0F, 133.0F, 140.0F, 147.0F, 154.0F, 162.0F, 169.0F,
                                    178.0F, 187.0F, 196.0F, 205.0F, 215.0F, 226.0F, 237.0F, 249.0F, 261.0F, 274.0F, 287.0F, 301.0F, 
                                    316.0F, 332.0F, 348.0F, 365.0F, 383.0F, 402.0F, 422.0F, 442.0F, 464.0F, 487.0F, 511.0F, 536.0F, 
                                    562.0F, 590.0F, 619.0F, 649.0F, 681.0F, 715.0F, 750.0F, 787.0F, 825.0F, 866.0F, 909.0F, 953.0F)
    val E96Series =  listOf(100.0F, 102.0F, 105.0F, 107.0F, 110.0F, 113.0F, 115.0F, 118.0F, 121.0F, 124.0F, 127.0F, 130.0F,
                                    133.0F, 137.0F, 140.0F, 143.0F, 147.0F, 150.0F, 154.0F, 158.0F, 162.0F, 165.0F, 169.0F, 174.0F, 
                                    178.0F, 182.0F, 187.0F, 191.0F, 196.0F, 200.0F, 205.0F, 210.0F, 215.0F, 221.0F, 226.0F, 232.0F, 
                                    237.0F, 243.0F, 249.0F, 255.0F, 261.0F, 267.0F, 274.0F, 280.0F, 287.0F, 294.0F, 301.0F, 309.0F, 
                                    316.0F, 324.0F, 332.0F, 340.0F, 348.0F, 357.0F, 365.0F, 374.0F, 383.0F, 392.0F, 402.0F, 412.0F, 
                                    422.0F, 432.0F, 442.0F, 453.0F, 464.0F, 475.0F, 487.0F, 499.0F, 511.0F, 523.0F, 536.0F, 549.0F, 
                                    562.0F, 576.0F, 590.0F, 604.0F, 619.0F, 634.0F, 649.0F, 665.0F, 681.0F, 698.0F, 715.0F, 732.0F, 
                                    750.0F, 768.0F, 787.0F, 806.0F, 825.0F, 845.0F, 866.0F, 887.0F, 909.0F, 931.0F, 953.0F, 976.0F)
    val E192Series =  listOf(100.0F, 101.0F, 102.0F, 104.0F, 105.0F, 106.0F, 107.0F, 109.0F, 110.0F, 111.0F, 113.0F, 114.0F,
                                    115.0F, 117.0F, 118.0F, 120.0F, 121.0F, 123.0F, 124.0F, 126.0F, 127.0F, 129.0F, 130.0F, 132.0F, 
                                    133.0F, 135.0F, 137.0F, 138.0F, 140.0F, 142.0F, 143.0F, 145.0F, 147.0F, 149.0F, 150.0F, 152.0F, 
                                    154.0F, 156.0F, 158.0F, 160.0F, 162.0F, 164.0F, 165.0F, 167.0F, 169.0F, 172.0F, 174.0F, 176.0F, 
                                    178.0F, 180.0F, 182.0F, 184.0F, 187.0F, 189.0F, 191.0F, 193.0F, 196.0F, 198.0F, 200.0F, 203.0F, 
                                    205.0F, 208.0F, 210.0F, 213.0F, 215.0F, 218.0F, 221.0F, 223.0F, 226.0F, 229.0F, 232.0F, 234.0F, 
                                    237.0F, 240.0F, 243.0F, 246.0F, 249.0F, 252.0F, 255.0F, 258.0F, 261.0F, 264.0F, 267.0F, 271.0F, 
                                    274.0F, 277.0F, 280.0F, 284.0F, 287.0F, 291.0F, 294.0F, 298.0F, 301.0F, 305.0F, 309.0F, 312.0F, 
                                    316.0F, 320.0F, 324.0F, 328.0F, 332.0F, 336.0F, 340.0F, 344.0F, 348.0F, 352.0F, 357.0F, 361.0F, 
                                    365.0F, 370.0F, 374.0F, 379.0F, 383.0F, 388.0F, 392.0F, 397.0F, 402.0F, 407.0F, 412.0F, 417.0F, 
                                    422.0F, 427.0F, 432.0F, 437.0F, 442.0F, 448.0F, 453.0F, 459.0F, 464.0F, 470.0F, 475.0F, 481.0F, 
                                    487.0F, 493.0F, 499.0F, 505.0F, 511.0F, 517.0F, 523.0F, 530.0F, 536.0F, 542.0F, 549.0F, 556.0F, 
                                    562.0F, 569.0F, 576.0F, 583.0F, 590.0F, 597.0F, 604.0F, 612.0F, 619.0F, 626.0F, 634.0F, 642.0F, 
                                    649.0F, 657.0F, 665.0F, 673.0F, 681.0F, 690.0F, 698.0F, 706.0F, 715.0F, 723.0F, 732.0F, 741.0F, 
                                    750.0F, 759.0F, 768.0F, 777.0F, 787.0F, 796.0F, 806.0F, 816.0F, 825.0F, 835.0F, 845.0F, 856.0F, 
                                    866.0F, 876.0F, 887.0F, 898.0F, 909.0F, 920.0F, 931.0F, 942.0F, 953.0F, 965.0F, 976.0F, 988.0F)

    private fun correctValues(editText: EditText) : Boolean
    {
        return editText.text.isNotEmpty() && editText.text.toString().last().isDigit() && editText.text.toString().toFloat() > 0.0F
    }

    private fun orderOfMagnitudeDifferenceFromBaseSeriesE3toE24(prefValuesIdealInput : Float) : Pair<Int, Float>
    {
        var magnitudeDifference : Int = 0
        var prefValuesIdealInputModify : Float = prefValuesIdealInput

        while(prefValuesIdealInputModify <= 10) // fix
        {
            prefValuesIdealInputModify *= 10
            magnitudeDifference--
        }

        while(prefValuesIdealInputModify > 100) //fix
        {
            prefValuesIdealInputModify /= 10
            magnitudeDifference++
        }

        return Pair(magnitudeDifference,prefValuesIdealInputModify)
    }

    private fun orderOfMagnitudeDifferenceFromBaseSeriesE48toE192(prefValuesIdealInput : Float) : Pair<Int, Float>
    {
        var magnitudeDifference : Int = 0
        var prefValuesIdealInputModify : Float = prefValuesIdealInput

        while(prefValuesIdealInputModify <= 100) // fix
        {
            prefValuesIdealInputModify *= 10
            magnitudeDifference--
        }

        while(prefValuesIdealInputModify > 1000) //fix
        {
            prefValuesIdealInputModify /= 10
            magnitudeDifference++
        }

        return Pair(magnitudeDifference,prefValuesIdealInputModify)
    }

    private fun realValueFromSeries(prefValuesIdealInput : Float, series : List<Float>) : Float
    {
        var magnitudeDifferenceAndPrefValuesIdealInputModify: Pair<Int, Float> =
            if(series.size < 25) { orderOfMagnitudeDifferenceFromBaseSeriesE3toE24(prefValuesIdealInput) }
            else { orderOfMagnitudeDifferenceFromBaseSeriesE48toE192(prefValuesIdealInput) }

        var magnitudeDifference : Float = magnitudeDifferenceAndPrefValuesIdealInputModify.first.toFloat()
        var prefValuesIdealInputModify : Float = magnitudeDifferenceAndPrefValuesIdealInputModify.second
        var positionOfClosestSmallerValue : Int = 0

        while(prefValuesIdealInputModify > series[positionOfClosestSmallerValue])
        {
            if(positionOfClosestSmallerValue == series.lastIndex)
                break

            positionOfClosestSmallerValue++
        }

        if(prefValuesIdealInputModify - series[positionOfClosestSmallerValue - 1] <=
            series[positionOfClosestSmallerValue] - prefValuesIdealInputModify)
            {
                return series[positionOfClosestSmallerValue - 1] * 10.0F.pow(magnitudeDifference)
            }

        return series[positionOfClosestSmallerValue] * 10.0F.pow(magnitudeDifference)
    }

    fun countPreferredValues()
    {
        var TvE3ValueOutput : Float = 0.0F
        var TvE6ValueOutput : Float = 0.0F
        var TvE12ValueOutput : Float = 0.0F
        var TvE24ValueOutput : Float = 0.0F
        var TvE48ValueOutput : Float = 0.0F
        var TvE96ValueOutput : Float = 0.0F
        var TvE192ValueOutput : Float = 0.0F

        if(correctValues(EtPrefValuesIdeal))
        {
            var PrefValuesIdealInput :Float = EtPrefValuesIdeal.text.toString().toFloat()

            TvE3ValueOutput = realValueFromSeries(PrefValuesIdealInput, E3Series)
            TvE6ValueOutput = realValueFromSeries(PrefValuesIdealInput, E6Series)
            TvE12ValueOutput = realValueFromSeries(PrefValuesIdealInput, E12Series)
            TvE24ValueOutput = realValueFromSeries(PrefValuesIdealInput, E24Series)
            TvE48ValueOutput = realValueFromSeries(PrefValuesIdealInput, E48Series)
            TvE96ValueOutput = realValueFromSeries(PrefValuesIdealInput, E96Series)
            TvE192ValueOutput = realValueFromSeries(PrefValuesIdealInput, E192Series)

            TvE3Value.text = TvE3ValueOutput.toString()
            TvE6Value.text = TvE6ValueOutput.toString()
            TvE12Value.text = TvE12ValueOutput.toString()
            TvE24Value.text = TvE24ValueOutput.toString()
            TvE48Value.text = TvE48ValueOutput.toString()
            TvE96Value.text = TvE96ValueOutput.toString()
            TvE192Value.text = TvE192ValueOutput.toString()

            TvE3ValueErr.text = countError(TvE3ValueOutput).toString()
            TvE6ValueErr.text = countError(TvE6ValueOutput).toString()
            TvE12ValueErr.text = countError(TvE12ValueOutput).toString()
            TvE24ValueErr.text = countError(TvE24ValueOutput).toString()
            TvE48ValueErr.text = countError(TvE48ValueOutput).toString()
            TvE96ValueErr.text = countError(TvE96ValueOutput).toString()
            TvE192ValueErr.text = countError(TvE192ValueOutput).toString()

            TvE3Err.text = String.format("%.2f", countPercentage(countError(TvE3ValueOutput)))
            TvE6Err.text = String.format("%.2f", countPercentage(countError(TvE6ValueOutput)))
            TvE12Err.text = String.format("%.2f", countPercentage(countError(TvE12ValueOutput)))
            TvE24Err.text = String.format("%.2f", countPercentage(countError(TvE24ValueOutput)))
            TvE48Err.text = String.format("%.2f", countPercentage(countError(TvE48ValueOutput)))
            TvE96Err.text = String.format("%.2f", countPercentage(countError(TvE96ValueOutput)))
            TvE192Err.text = String.format("%.2f", countPercentage(countError(TvE192ValueOutput)))
        }
        else
        {
            TvE3Value.text = ""
            TvE6Value.text = ""
            TvE12Value.text = ""
            TvE24Value.text = ""
            TvE48Value.text = ""
            TvE96Value.text = ""
            TvE192Value.text = ""

            TvE3ValueErr.text = ""
            TvE6ValueErr.text = ""
            TvE12ValueErr.text = ""
            TvE24ValueErr.text = ""
            TvE48ValueErr.text = ""
            TvE96ValueErr.text = ""
            TvE192ValueErr.text = ""

            TvE3Err.text = ""
            TvE6Err.text = ""
            TvE12Err.text = ""
            TvE24Err.text = ""
            TvE48Err.text = ""
            TvE96Err.text = ""
            TvE192Err.text = ""
        }
    }

    private fun countError(calculatedValue : Float) : Float
    {
        var PrefValuesIdealInput :Float = EtPrefValuesIdeal.text.toString().toFloat()
        return abs(PrefValuesIdealInput - calculatedValue)
    }

    private fun countPercentage(calculatedValueError : Float) : Float
    {
        var PrefValuesIdealInput :Float = EtPrefValuesIdeal.text.toString().toFloat()
        return (calculatedValueError/PrefValuesIdealInput) * 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preferred_values, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Values
        TvE3Value = view.findViewById(R.id.TvE3Value)
        TvE6Value = view.findViewById(R.id.TvE6Value)
        TvE12Value = view.findViewById(R.id.TvE12Value)
        TvE24Value = view.findViewById(R.id.TvE24Value)
        TvE48Value = view.findViewById(R.id.TvE48Value)
        TvE96Value = view.findViewById(R.id.TvE96Value)
        TvE192Value = view.findViewById(R.id.TvE192Value)

        // Errors
        TvE3ValueErr = view.findViewById(R.id.TvE3ValueErr)
        TvE6ValueErr = view.findViewById(R.id.TvE6ValueErr)
        TvE12ValueErr = view.findViewById(R.id.TvE12ValueErr)
        TvE24ValueErr = view.findViewById(R.id.TvE24ValueErr)
        TvE48ValueErr = view.findViewById(R.id.TvE48ValueErr)
        TvE96ValueErr = view.findViewById(R.id.TvE96ValueErr)
        TvE192ValueErr = view.findViewById(R.id.TvE192ValueErr)

        // Errors %
        TvE3Err = view.findViewById(R.id.TvE3Err)
        TvE6Err = view.findViewById(R.id.TvE6Err)
        TvE12Err = view.findViewById(R.id.TvE12Err)
        TvE24Err = view.findViewById(R.id.TvE24Err)
        TvE48Err = view.findViewById(R.id.TvE48Err)
        TvE96Err = view.findViewById(R.id.TvE96Err)
        TvE192Err = view.findViewById(R.id.TvE192Err)

        EtPrefValuesIdeal = view.findViewById(R.id.EtPrefValuesIdeal)

        EtPrefValuesIdeal.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                countPreferredValues()
            }
        })
    }
}