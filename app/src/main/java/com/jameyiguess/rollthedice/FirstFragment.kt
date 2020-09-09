package com.jameyiguess.rollthedice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), View.OnClickListener, View.OnLongClickListener {
    private var mDice = ArrayList<String>()
    private var mModifier = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val d2Button = view.findViewById<Button>(R.id.d2)
        d2Button.setOnClickListener(this)
        d2Button.setOnLongClickListener(this)
        val d4Button = view.findViewById<Button>(R.id.d4)
        d4Button.setOnClickListener(this)
        d4Button.setOnLongClickListener(this)
        val d6Button = view.findViewById<Button>(R.id.d6)
        d6Button.setOnClickListener(this)
        d6Button.setOnLongClickListener(this)
        val d8Button = view.findViewById<Button>(R.id.d8)
        d8Button.setOnClickListener(this)
        d8Button.setOnLongClickListener(this)
        val d10Button = view.findViewById<Button>(R.id.d10)
        d10Button.setOnClickListener(this)
        d10Button.setOnLongClickListener(this)
        val d12Button = view.findViewById<Button>(R.id.d12)
        d12Button.setOnClickListener(this)
        d12Button.setOnLongClickListener(this)
        val d20Button = view.findViewById<Button>(R.id.d20)
        d20Button.setOnClickListener(this)
        d20Button.setOnLongClickListener(this)
        val d100Button = view.findViewById<Button>(R.id.d100)
        d100Button.setOnClickListener(this)
        d100Button.setOnLongClickListener(this)

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
            updateText()
        }
    }

    override fun onClick(v: View?) {
        mDice.add((v as TextView).text.toString())
        updateText()
    }

    override fun onLongClick(v: View?): Boolean {
        mDice.remove((v as TextView).text.toString())
        updateText()
        return true
    }

    private fun updateText() {
        activity?.runOnUiThread {
            var text = mDice.joinToString(" ")
            if (mModifier > 0) {
                text += " +$mModifier"
            } else if (mModifier < 0) {
                text += " -$mModifier"
            }
            view?.findViewById<TextView>(R.id.diceTextView)?.text = text
        }
    }
}