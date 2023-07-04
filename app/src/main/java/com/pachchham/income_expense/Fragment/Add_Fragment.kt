package com.example.khatabook2.Fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.khatabook2.Model.TransModel
import com.pachchham.income_expense.Database
import com.pachchham.income_expense.databinding.FragmentAddBinding

class Add_Fragment : Fragment() {

    lateinit var binding: FragmentAddBinding
    var isexpense = 0
    lateinit var database: Database

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddBinding.inflate(layoutInflater)

        database = Database(context)

        initView()

        return binding.root

    }

    private fun initView() {

        binding.cadrdIncome.setOnClickListener {

            isexpense = 0
            binding.cadrdIncome.setCardBackgroundColor(Color.parseColor("#FAA43A"))
            binding.cadrdExpense.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        binding.cadrdIncome.setOnClickListener {

            isexpense = 1
            binding.cadrdIncome.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.cadrdExpense.setCardBackgroundColor(Color.parseColor("#FAA43A"))
        }

        binding.btnSubmit.setOnClickListener {
            var amount = binding.edtAmount.text.toString().toInt()
            var category = binding.edtCategory.text.toString()
            var note = binding.edtNote.text.toString()
            var Model = TransModel(1, amount, category,note,isexpense)

            database.addTrans(Model)

            binding.edtAmount.setText("")
            binding.edtCategory.setText("")
            binding.edtNote.setText("")

        }

    }

}