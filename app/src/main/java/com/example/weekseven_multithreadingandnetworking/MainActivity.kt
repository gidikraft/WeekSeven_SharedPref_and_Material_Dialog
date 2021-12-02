package com.example.weekseven_multithreadingandnetworking

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.example.weekseven_multithreadingandnetworking.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        private lateinit var binding: ResultProfileBinding
//
//        override fun onCreate(savedInstanceState: Bundle) {
//            super.onCreate(savedInstanceState)
//            binding = ResultProfileBinding.inflate(layoutInflater)
//            val view = binding.root
//            setContentView(view)
//        }
        initSharedPreferences()
        binding.mainActivityShowDialogBtn.setOnClickListener {
            showFilterDialog()
        }
    }

    private fun initSharedPreferences() {
        preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    private fun showFilterDialog(){
//        val filterRadioButton = binding.fil
//        init { // inflate binding and add as view
//            binding = ResultProfileBinding.inflate(LayoutInflater.from(context), this, true)
//        }
        val dialog = MaterialDialog(this)
            .noAutoDismiss()
            .customView(R.layout.layout_filter_dialog_activity)

        val filter = preferences.getString(getString(R.string.key_filter), getString(R.string.key_filter))

        if (filter.equals(getString(R.string.filter_date))) {
            dialog.findViewById<RadioGroup>(R.id.filter_group).check(R.id.filter_date)
        } else {
            dialog.findViewById<RadioGroup>(R.id.filter_group).check(R.id.filter_author)
        }

        val order = preferences.getString(getString(R.string.key_order), getString(R.string.order_asc))

        if (order.equals(getString(R.string.order_asc))) {
            dialog.findViewById<RadioGroup>(R.id.order_group).check(R.id.order_asc)
        } else {
            dialog.findViewById<RadioGroup>(R.id.order_group).check(R.id.order_desc)
        }
        //get new preferences
        dialog.findViewById<TextView>(R.id.apply_btn).setOnClickListener {

            val selectedFilter = dialog.getCustomView().findViewById<RadioButton>(
                dialog.findViewById<RadioGroup>(R.id.filter_group).checkedRadioButtonId)

            val selectedOrder = dialog.getCustomView().findViewById<RadioButton>(
                dialog.findViewById<RadioGroup>(R.id.order_group).checkedRadioButtonId)

            //save the new values to shared prefs
            editor.putString(getString(R.string.key_filter), selectedFilter.text.toString())
            editor.apply()

            editor.putString(getString(R.string.key_order), selectedOrder.text.toString())
            editor.apply()

            dialog.dismiss()
        }

        dialog.findViewById<TextView>(R.id.cancel_btn).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}