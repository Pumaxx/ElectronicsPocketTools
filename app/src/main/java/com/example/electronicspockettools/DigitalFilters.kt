package com.example.electronicspockettools

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class DigitalFilters : Fragment() {

    private lateinit var EtFirstParameters : EditText
    private lateinit var EtSecondParameters : EditText

    private lateinit var TvResultOutput :TextView
    private lateinit var RgRadioGroup : RadioGroup 
    private lateinit var BtCalculateFilter : Button

    var isParallelModeSelected : Boolean = true

    private fun inputToArray(editText: EditText) : List<Double>
    {
        var parameters: String = editText.text.toString()
        parameters = parameters.replace("[^-?0-9?.]+".toRegex(), " ")
        parameters.trim().split(" ").map { it.toDouble() }
        val stringArray : List<String> = parameters.trim().split(" ").map { it -> it.trim() }

        return stringListToDoubleList(stringArray)
    }

    private fun fillSmallerArrayWithZeros(smallerArray : List<Double>, diff : Int) : List<Double>
    {
        var extendedSorterArray = mutableListOf<Double>()

        repeat(diff/2) { extendedSorterArray.add(0.0) }
            extendedSorterArray.addAll(smallerArray)
        repeat(diff/2) { extendedSorterArray.add(0.0) }

        return extendedSorterArray
    }

    private fun fillParallelArray(firstArray : List<Double>, secondArray : List<Double>) : MutableList<Double>
    {
        var iterator : Int = 0

        var result =  mutableListOf<Double>()
        repeat(firstArray.size) { result.add(0.0) }

        while(iterator < result.size)
        {
            result[iterator] = firstArray[iterator] + secondArray[iterator]
            iterator++
        }

        return result
    }

    private fun calculateParallel(firstFilterParams : List<Double>, secondFilterParams : List<Double>) : List<Double>
    {
        var firstSize : Int = firstFilterParams.size
        var secondSize : Int = secondFilterParams.size

        var result: MutableList<Double>

        when
        {
            firstSize > secondSize ->
            {
                var diff = firstSize - secondSize
                var extendedSorterArray = fillSmallerArrayWithZeros(secondFilterParams, diff)

                result = fillParallelArray(firstFilterParams, extendedSorterArray)
            }

            secondSize > firstSize ->
            {
                var diff = secondSize - firstSize
                var extendedSorterArray = fillSmallerArrayWithZeros(firstFilterParams, diff)

                result = fillParallelArray(secondFilterParams, extendedSorterArray)
            }

            else ->
            {
                result = fillParallelArray(firstFilterParams, secondFilterParams)
                
            }
        }

        return result
    }

    private fun calculateSeries(firstFilterParams : List<Double>, secondFilterParams : List<Double>) : List<Double>
    {
        var firstSize : Int = firstFilterParams.size
        var secondSize : Int = secondFilterParams.size
        var resultSize : Int = firstSize + secondSize - 1

        var result =  mutableListOf<Double>()
        repeat(resultSize) { result.add(0.0) }

        result.forEachIndexed{ n, _ ->
            var c : Double = 0.0
            
            secondFilterParams.forEachIndexed { k, _ ->
                if (n - k >= 0 && n - k < firstSize)
                {
                    c += firstFilterParams[n-k] * secondFilterParams[k]
                }
            }
            
            result[n] = c
        }

        return result
    }

    private fun stringListToDoubleList(stringList : List<String>) : List<Double>
    {
        var doubleList = mutableListOf<Double>()

        for(string in stringList)
        {
            doubleList.add(string.toDouble())
        }

        return doubleList
    }

    private fun bothEvenOrBothOdd (sizeOne : Int, sizeTwo : Int) :Boolean
    {
        return sizeOne % 2 == sizeTwo % 2
    }

    private fun checkInput(editText: EditText) : Boolean
    {
        val returnValue : Boolean = editText.text.isNotEmpty() && !editText.text.toString().contains("..")
        if(!returnValue)
        {
            TvResultOutput.setTextColor(Color.parseColor("#D83636"))

            if(editText.text.isEmpty())
            {
                TvResultOutput.text = "Both filters need parameters"
            }
            else if(editText.text.toString().contains(".."))
            {
                TvResultOutput.text = "Double dot detected in text field"
            }
        }

        return returnValue
    }

    private fun calculate() {
        if(checkInput(EtFirstParameters) && checkInput(EtSecondParameters))
        {
             kotlin.runCatching {
                var firstParameters: List<Double> = inputToArray(EtFirstParameters)
                var secondParameters: List<Double> = inputToArray(EtSecondParameters)

                if (isParallelModeSelected)
                {
                    if(bothEvenOrBothOdd(firstParameters.size, secondParameters.size))
                    {
                        var resultArray: List<Double> = calculateParallel(firstParameters, secondParameters)

                        TvResultOutput.setTextColor(Color.parseColor("#FFFFFFFF"))
                        TvResultOutput.text = resultArray.toString()
                    }
                    else
                    {
                        TvResultOutput.setTextColor(Color.parseColor("#D83636"))
                        TvResultOutput.text = "For parallel realisation number of both filter parameters can be the same " +
                                              "or can be different if both numbers are simultaneously even or simultaneously odd"
                    }
                }
                else
                {
                    var resultArray: List<Double> = calculateSeries(firstParameters, secondParameters)

                    TvResultOutput.setTextColor(Color.parseColor("#FFFFFFFF"))
                    TvResultOutput.text = resultArray.toString()
                }
            }.onFailure {
                 TvResultOutput.setTextColor(Color.parseColor("#D83636"))
                 TvResultOutput.text = "Please check if parameters input is correct"  }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_digital_filters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        EtFirstParameters = view.findViewById(R.id.EtFirstParameters)
        EtSecondParameters = view.findViewById(R.id.EtSecondParameters)

        TvResultOutput = view.findViewById(R.id.TvResultOutput)
        RgRadioGroup = view.findViewById(R.id.RgRadioGroup)
        BtCalculateFilter = view.findViewById(R.id.BtCalculateFilter)

        RgRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            run {
                   var radioButton : RadioButton = view.findViewById(checkedId)

                   when (radioButton.text)
                   {
                       "Parallel" -> isParallelModeSelected = true
                       "Series" -> isParallelModeSelected = false
                   }
                }
        }

        BtCalculateFilter.setOnClickListener {
            calculate() 
        }
    }
}