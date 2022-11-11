package com.example.electronicspockettools

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class MainMenu : Fragment() {

    private lateinit var BtResistorRecognition : Button;
    private lateinit var BtWaveCalculator : Button;
    private lateinit var BtLogicGates : Button;
    private lateinit var BtDigitalFilters : Button;
    private lateinit var BtPreferredValues : Button;
    private lateinit var BtAmplificationSuppression : Button;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        BtResistorRecognition = view.findViewById(R.id.BtResistorRecognition)
        BtWaveCalculator = view.findViewById(R.id.BtWaveCalculator)
        BtLogicGates = view.findViewById(R.id.BtLogicGates)
        BtDigitalFilters = view.findViewById(R.id.BtDigitalFilters)
        BtPreferredValues = view.findViewById(R.id.BtPreferredValues)
        BtAmplificationSuppression = view.findViewById(R.id.BtAmplificationSuppression)

        BtResistorRecognition.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_mainMenu_to_resistorRecognition2) }

        BtWaveCalculator.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_mainMenu_to_waveCalculator2) }

        BtLogicGates.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_mainMenu_to_logicGates2) }

        BtDigitalFilters.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_mainMenu_to_digitalFilters2) }

        BtPreferredValues.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_mainMenu_to_preferredValues2) }

        BtAmplificationSuppression.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_mainMenu_to_amplificationSuppression2) }
    }
}