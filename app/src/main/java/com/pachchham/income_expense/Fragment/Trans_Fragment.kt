package com.example.khatabook2.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.khatabook2.Model.TransModel
import com.pachchham.income_expense.Database
import com.pachchham.income_expense.TransListAdapter
import com.pachchham.income_expense.databinding.FragmentTransactionBinding
import com.pachchham.income_expense.databinding.UpdateLayoutBinding

import java.lang.Exception


class Trans_Fragment : Fragment() {
    var translist = ArrayList<TransModel>()
    lateinit var database: Database
    lateinit var adapter: TransListAdapter
    lateinit var binding: FragmentTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTransactionBinding.inflate(layoutInflater)
        database = Database(context)
        translist = database.getTransaction()

        translist = translist.reversed() as ArrayList<TransModel>

        adapter = TransListAdapter({

            updateDialoge(it)
        }, {
            deleteTrans(it)
        })

        adapter.setTrans(translist)

        binding.rcvTransList.layoutManager = LinearLayoutManager(context)
        binding.rcvTransList.adapter = adapter

        return binding.root

    }

    private fun deleteTrans(it: Int) {

        var dialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete Transaction")
            .setMessage("Are you sure For Delete This Message?")
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



    private fun updateDialoge(transModel: TransModel) {
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
            var model = TransModel(transModel.id, amount,category,note,transModel.isexpense)

            database.updateTrans(model)
            dialog.dismiss()

            adapter.updateData(database.getTransaction().reversed() as java.util.ArrayList<TransModel>  )
        }

        dialog.show()
    }

}