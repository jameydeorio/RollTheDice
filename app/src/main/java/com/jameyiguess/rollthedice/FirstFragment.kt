package com.jameyiguess.rollthedice

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.jameyiguess.rollthedice.util.ProbabilityMachine
import kotlin.math.absoluteValue

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), View.OnClickListener, View.OnLongClickListener {
    private var diceStrings = ArrayList<String>()
    private var modifier = 0
    private var dc = 0
    private val probabilityMachine: ProbabilityMachine = ProbabilityMachine()
    private var probability = 0
    private var resultTextView: TextView? = null
    private var dcEditText: EditText? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val d2Button = view.findViewById(R.id.d2ImageButton) as ImageButton
        d2Button.setOnClickListener(this)
        d2Button.setOnLongClickListener(this)
        val d4Button = view.findViewById(R.id.d4ImageButton) as ImageButton
        d4Button.setOnClickListener(this)
        d4Button.setOnLongClickListener(this)
        val d6Button = view.findViewById(R.id.d6ImageButton) as ImageButton
        d6Button.setOnClickListener(this)
        d6Button.setOnLongClickListener(this)
        val d8Button = view.findViewById(R.id.d8ImageButton) as ImageButton
        d8Button.setOnClickListener(this)
        d8Button.setOnLongClickListener(this)
        val d10Button = view.findViewById(R.id.d10ImageButton) as ImageButton
        d10Button.setOnClickListener(this)
        d10Button.setOnLongClickListener(this)
        val d12Button = view.findViewById(R.id.d12ImageButton) as ImageButton
        d12Button.setOnClickListener(this)
        d12Button.setOnLongClickListener(this)
        val d20Button = view.findViewById(R.id.d20ImageButton) as ImageButton
        d20Button.setOnClickListener(this)
        d20Button.setOnLongClickListener(this)
        val d100Button = view.findViewById(R.id.d100ImageButton) as ImageButton
        d100Button.setOnClickListener(this)
        d100Button.setOnLongClickListener(this)
        dcEditText = view.findViewById(R.id.dc) as EditText
        resultTextView = view.findViewById(R.id.result)

        reset()

        (dcEditText as EditText).addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val dcString = s?.toString()
                if (dcString != null && dcString.isNotEmpty()) {
                    dc = dcString.toInt()
                    updateProbability()
                    updateText()
                }
            }
        })

        view.findViewById<ImageButton>(R.id.minusOneImageButton).setOnClickListener {
            modifier--
            updateProbability()
            updateText()
        }

        view.findViewById<ImageButton>(R.id.plusOneImageButton).setOnClickListener {
            modifier++
            updateProbability()
            updateText()
        }

        view.findViewById<Button>(R.id.resetButton).setOnClickListener {
            reset()
            updateProbability()
            updateText()
        }
    }

    override fun onClick(v: View?) {
        var text = ""
        when (v?.id) {
            R.id.d2ImageButton -> {
                text = "d2"
            }
            R.id.d4ImageButton -> {
                text = "d4"
            }
            R.id.d6ImageButton -> {
                text = "d6"
            }
            R.id.d8ImageButton -> {
                text = "d8"
            }
            R.id.d10ImageButton -> {
                text = "d10"
            }
            R.id.d12ImageButton -> {
                text = "d12"
            }
            R.id.d20ImageButton -> {
                text = "d20"
            }
            R.id.d100ImageButton -> {
                text = "d100"
            }
        }
        val foundItem = diceStrings.find { it.endsWith(text) }
        if (foundItem != null) {
            diceStrings.remove(foundItem)
            val parts = foundItem.split("d")
            val numDiceString = parts[0]
            var numExistingDice = 1
            if (!numDiceString.isBlank()) {
                numExistingDice = numDiceString.toInt()
            }
            text = "${numExistingDice + 1}$text"
        }
        diceStrings.add(text)
        updateProbability()
        updateText()
    }

    override fun onLongClick(v: View?): Boolean {
        var text = ""
        when (v?.id) {
            R.id.d2ImageButton -> {
                text = "d2"
            }
            R.id.d4ImageButton -> {
                text = "d4"
            }
            R.id.d6ImageButton -> {
                text = "d6"
            }
            R.id.d8ImageButton -> {
                text = "d8"
            }
            R.id.d10ImageButton -> {
                text = "d10"
            }
            R.id.d12ImageButton -> {
                text = "d12"
            }
            R.id.d20ImageButton -> {
                text = "d20"
            }
            R.id.d100ImageButton -> {
                text = "d100"
            }
        }
        val foundItem = diceStrings.find { it.endsWith(text) }
        if (foundItem != null) {
            diceStrings.remove(foundItem)
            val parts = foundItem.split("d")
            val numDiceString = parts[0]
            var numExistingDice = 1
            if (!numDiceString.isBlank()) {
                numExistingDice = numDiceString.toInt()
            }
            val newNumberOfDice = numExistingDice - 1
            if (newNumberOfDice == 0) {
                diceStrings.remove(foundItem)
            } else if (newNumberOfDice == 1) {
                diceStrings.add(text)
            } else {
                text = "$newNumberOfDice$text"
                diceStrings.add(text)
            }
        }
        updateProbability()
        updateText()
        return true
    }

    private fun updateProbability() {
        if (diceStrings.isNullOrEmpty()) {
            return
        }
        val dice = arrayListOf<Int>()
        for (diceString in diceStrings) {
            val parts = diceString.split("d")
            val numDiceString = parts[0]
            var numDice = 1
            val numSides = parts[1].toInt()
            if (!numDiceString.isBlank()) {
                numDice = numDiceString.toInt()
            }
            for (i in 1..numDice) {
                dice.add(numSides)
            }
        }
        probabilityMachine.dice = dice
        probabilityMachine.modifier = modifier
        probability = probabilityMachine.getProbabilityToBeatDc(dc)
        activity?.runOnUiThread { resultTextView?.text = "${probability.toString()}%" }
    }

    private fun updateText() {
        activity?.runOnUiThread {
            val text = diceStrings.joinToString(" ")
            var modifier = ""
            if (this.modifier > 0) {
                modifier = "+${this.modifier}"
            } else if (this.modifier < 0) {
                modifier = "-${this.modifier.absoluteValue}"
            }
            view?.findViewById<TextView>(R.id.diceTextView)?.text = text
            view?.findViewById<TextView>(R.id.modifierTextView)?.text = modifier
        }
    }

    private fun reset() {
        diceStrings = arrayListOf<String>()
        modifier = 0
//        dc = 0
        (resultTextView as TextView).text = "--%"
    }
}
