package com.jameyiguess.rollthedice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.jameyiguess.rollthedice.util.ProbabilityMachine
import kotlin.reflect.typeOf

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), View.OnClickListener, View.OnLongClickListener {
    private var mDice = ArrayList<String>()
    private var mModifier = 0
    private val mProbabilityMachine: ProbabilityMachine = ProbabilityMachine()

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
        val dcEditText = view.findViewById(R.id.dc) as EditText
        val resultTextView = view.findViewById(R.id.result) as TextView

        view.findViewById<Button>(R.id.calculate).setOnClickListener {
            val text = view.findViewById<TextView>(R.id.diceTextView).text
            if (text.isNotBlank()) {
                val list = text.split(" ")
                val dice = arrayListOf<Int>()
                for (diceString in list) {
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
                mProbabilityMachine.dice = dice
                mProbabilityMachine.modifier = mModifier
                val probability = mProbabilityMachine.getProbabilityToBeatDc(dcEditText.text.toString().toInt())
                resultTextView.text = "${probability.toString()}%"
            }
        }

        view.findViewById<Button>(R.id.modifierMinusButton).setOnClickListener {
            mModifier--
            updateText()
        }

        view.findViewById<Button>(R.id.modifierPlusButton).setOnClickListener {
            mModifier++
            updateText()
        }

        view.findViewById<Button>(R.id.resetButton).setOnClickListener {
            mDice = arrayListOf<String>()
            mModifier = 0
            dcEditText.setText("")
            resultTextView.text = ""
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
        mDice.add(text)
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
        mDice.remove(text)
        updateText()
        return true
    }

    private fun updateText() {
        activity?.runOnUiThread {
            val text = mDice.joinToString(" ")
            var modifier = ""
            if (mModifier > 0) {
                modifier = "+$mModifier"
            } else if (mModifier < 0) {
                modifier = "-$mModifier"
            }
            view?.findViewById<TextView>(R.id.diceTextView)?.text = text
            view?.findViewById<TextView>(R.id.modifierTextView)?.text = modifier
        }
    }
}