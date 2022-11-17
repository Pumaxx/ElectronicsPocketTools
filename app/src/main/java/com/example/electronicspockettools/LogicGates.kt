package com.example.electronicspockettools

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class LogicGates : Fragment() {

    //Buttons
    private lateinit var BtAndInOne : Button
    private lateinit var BtAndInTwo : Button
    private lateinit var BtNandInOne : Button
    private lateinit var BtNandInTwo : Button
    private lateinit var BtOrInOne : Button
    private lateinit var BtOrInTwo : Button
    private lateinit var BtNorInOne : Button
    private lateinit var BtNorInTwo : Button
    private lateinit var BtXorInOne : Button
    private lateinit var BtXorInTwo : Button
    private lateinit var BtXnorInOne : Button
    private lateinit var BtXnorInTwo : Button

    //Buttons
    private lateinit var BtAndInResult : Button
    private lateinit var BtNandInResult : Button
    private lateinit var BtOrInResult : Button
    private lateinit var BtNorInResult : Button
    private lateinit var BtXorInResult : Button
    private lateinit var BtXnorInResult : Button

    private fun setOppositeValue(button: Button)
    {
        button.text = if(button.text.toString() == "0") { "1" } else { "0" }

        if(BtAndInOne.text.toString() == "1" && BtAndInTwo.text.toString() == "1")
        {
            BtAndInResult.text = "1"
        }
        else
        {
            BtAndInResult.text = "0"
        }
    }

    private fun andGateResult(button: Button)
    {
        setOppositeValue(button)


    }

    private fun nandGateResult(button: Button)
    {
        setOppositeValue(button)

        if(BtNandInOne.text.toString() == "0" && BtNandInTwo.text.toString() == "0")
        {
            BtNandInResult.text = "1"
        }
        else
        {
            BtNandInResult.text = "0"
        }
    }

    private fun orGateResult(button: Button)
    {
        setOppositeValue(button)

        if(BtOrInOne.text.toString() == "1" || BtOrInTwo.text.toString() == "1")
        {
            BtOrInResult.text = "1"
        }
        else
        {
            BtOrInResult.text = "0"
        }
    }

    private fun norGateResult(button: Button)
    {
        setOppositeValue(button)

        if(BtNorInOne.text.toString() == "1" || BtNorInTwo.text.toString() == "1")
        {
            BtNorInResult.text = "0"
        }
        else
        {
            BtNorInResult.text = "1"
        }
    }

    private fun xorGateResult(button: Button)
    {
        setOppositeValue(button)

        if(BtXorInOne.text.toString() != BtXorInTwo.text.toString())
        {
            BtXorInResult.text = "1"
        }
        else
        {
            BtXorInResult.text = "0"
        }
    }

    private fun xnorGateResult(button: Button)
    {
        setOppositeValue(button)

        if(BtXnorInOne.text.toString() == BtXnorInTwo.text.toString())
        {
            BtXnorInResult.text = "1"
        }
        else
        {
            BtXnorInResult.text = "0"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logic_gates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Buttons
        BtAndInOne = view.findViewById(R.id.BtAndInOne)
        BtAndInTwo = view.findViewById(R.id.BtAndInTwo)
        BtNandInOne = view.findViewById(R.id.BtNandInOne)
        BtNandInTwo = view.findViewById(R.id.BtNandInTwo)
        BtOrInOne = view.findViewById(R.id.BtOrInOne)
        BtOrInTwo = view.findViewById(R.id.BtOrInTwo)
        BtNorInOne = view.findViewById(R.id.BtNorInOne)
        BtNorInTwo = view.findViewById(R.id.BtNorInTwo)
        BtXorInOne = view.findViewById(R.id.BtXorInOne)
        BtXorInTwo = view.findViewById(R.id.BtXorInTwo)
        BtXnorInOne = view.findViewById(R.id.BtXnorInOne)
        BtXnorInTwo = view.findViewById(R.id.BtXnorInTwo)

        //Buttons
        BtAndInResult = view.findViewById(R.id.BtAndInResult)
        BtNandInResult = view.findViewById(R.id.BtNandInResult)
        BtOrInResult = view.findViewById(R.id.BtOrInResult)
        BtNorInResult = view.findViewById(R.id.BtNorInResult)
        BtXorInResult = view.findViewById(R.id.BtXorInResult)
        BtXnorInResult = view.findViewById(R.id.BtXnorInResult)

        // OnclickListeners
        BtAndInOne.setOnClickListener{
            andGateResult(BtAndInOne)
        }
        BtAndInTwo.setOnClickListener{
            andGateResult(BtAndInTwo)
        }

        BtNandInOne.setOnClickListener{
            nandGateResult(BtNandInOne)
        }
        BtNandInTwo.setOnClickListener{
            nandGateResult(BtNandInTwo)
        }

        BtOrInOne.setOnClickListener{
            orGateResult(BtOrInOne)
        }
        BtOrInTwo.setOnClickListener{
            orGateResult(BtOrInTwo)
        }

        BtNorInOne.setOnClickListener{
            norGateResult(BtNorInOne)
        }
        BtNorInTwo.setOnClickListener{
            norGateResult(BtNorInTwo)
        }

        BtXorInOne.setOnClickListener{
            xorGateResult(BtXorInOne)
        }
        BtXorInTwo.setOnClickListener{
            xorGateResult(BtXorInTwo)
        }

        BtXnorInOne.setOnClickListener{
            xnorGateResult(BtXnorInOne)
        }
        BtXnorInTwo.setOnClickListener{
            xnorGateResult(BtXnorInTwo)
        }

    }
}