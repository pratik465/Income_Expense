package com.example.khatabook2.Fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.khatabook2.Model.TransModel
import com.pachchham.income_expense.Database
import com.pachchham.income_expense.MainActivity
import com.pachchham.income_expense.TransListAdapter
import com.pachchham.income_expense.databinding.FragmentHomeBinding
import com.pachchham.income_expense.databinding.UpdateLayoutBinding

class Home_Fragment : Fragment() {

    var translist = ArrayList<TransModel>()
    lateinit var database: Database
    lateinit var adapter: TransListAdapter
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        database = Database(context)
        translist = database.getTransaction()

        updateTotal(translist)


//        translist = translist

        adapter = TransListAdapter({

            updateDialog(it)

        }, {


            deleteTrans(it)
        })
        adapter.setTrans(translist)

        binding.rcvTransList.layoutManager = LinearLayoutManager(context)
        binding.rcvTransList.adapter = adapter

        binding.addIncome.setOnClickListener {
            MainActivity.change(1)
        }

        return binding.root
    }


    private fun deleteTrans(it: Int) {

        var dialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete Transaction")
            .setMessage("Are you want sure?")
            .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {

                    database.deleteTrans(it)

                    try {
                        adapter.updateData(
                            database.getTransaction().reversed() as java.util.ArrayList<TransModel>
                        )

                    } catch (e: Exception) {

                    }

                }

            }).setNegativeButton("No", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {

                }

            }).create()
        dialog.show()
    }

    fun updateTotal(translist: ArrayList<TransModel>) {
        var totalIncome = 0
        var totalExpense = 0

        for (trans in translist){
            if (trans.isexpense == 0){
                totalIncome +=trans.amount
            }else{
                totalExpense += trans.amount
            }
        }

        binding.TransIncome.text = totalIncome.toString()
        binding.TransExpense.text = totalExpense.toString()
        binding.Total.text = (totalIncome-totalExpense).toString()
    }

    private fun updateDialog(transModel: TransModel) {
        var dialog = Dialog(requireContext())
        var bind = UpdateLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.edtAmount.setText(transModel.amount.toString())
        bind.edtCategory.setText(transModel.category)
        bind.edtNote.setText(transModel.note)

        bind.btnSave.setOnClickListener {
            var amount = bind.edtAmount.text.toString().toInt()
            var category = bind.edtCategory.text.toString()
            var note = bind.edtNote.text.toString()
            var modal = TransModel(transModel.id, amount, category, note,transModel.isexpense)

            database.updateTrans(modal)
            dialog.dismiss()

            adapter.updateData(database.getTransaction().reversed() as java.util.ArrayList<TransModel>)
            updateTotal(database.getTransaction())

        }

        dialog.show()
    }
}