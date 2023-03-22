package com.example.androidfundementalsection2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.androidfundementalsection2.databinding.ActivityMainBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var cuboidPreference: CuboidPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       val pref = SettingPreferences.getInstance(dataStore)
       val themeViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
           ThemeViewModel::class.java
       )
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        mainViewModel = MainViewModel(CuboidModel())

        cuboidPreference = CuboidPreference(this)
        activityMainBinding.btnSave.setOnClickListener(this)
        activityMainBinding.btnCalculateSurfaceArea.setOnClickListener(this)
        activityMainBinding.btnCalculateCircumference.setOnClickListener(this)
        activityMainBinding.btnCalculateVolume.setOnClickListener(this)
        activityMainBinding.btnSaveFile.setOnClickListener(this)
        val switchTheme = activityMainBinding.switchTheme
        themeViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
//        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                switchTheme.isChecked = true
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                switchTheme.isChecked = false
//            }
//        }
    }

    override fun onClick(v: View) {
        val length = activityMainBinding.edtLength.text.toString().trim()
        val width = activityMainBinding.edtWidth.text.toString().trim()
        val height = activityMainBinding.edtHeight.text.toString().trim()
        when {
            TextUtils.isEmpty(length) -> {
                activityMainBinding.edtLength.error = "Field ini tidak boleh kosong"
            }
            TextUtils.isEmpty(width) -> {
                activityMainBinding.edtWidth.error = "Field ini tidak boleh kosong"
            }
            TextUtils.isEmpty(height) -> {
                activityMainBinding.edtHeight.error = "Field ini tidak boleh kosong"
            }
            else -> {
                val valueLength = length.toDouble()
                val valueWidth = width.toDouble()
                val valueHeight = height.toDouble()
                when (v.id) {
                    R.id.btn_open_file -> {
                        showList()
                    }
                    R.id.btn_open_saved ->{
                        Log.d("MAIN ACTIVITY", "onClick: ")
                        val cuboidModel = cuboidPreference.getCuboid()
                        activityMainBinding.edtLength.setText(cuboidModel.length.toString())
                        activityMainBinding.edtWidth.setText(cuboidModel.width.toString())
                        activityMainBinding.edtHeight.setText(cuboidModel.height.toString())
                    }
                    R.id.btn_save -> {
                        mainViewModel.save(valueLength, valueWidth, valueHeight)
                        cuboidPreference.setCuboid(mainViewModel.cuboidModel)
                        visible()
                    }
                    R.id.btn_calculate_circumference -> {
                        activityMainBinding.tvResult.text = mainViewModel.getCircumference().toString()
                        gone()
                    }
                    R.id.btn_calculate_surface_area -> {
                        activityMainBinding.tvResult.text = mainViewModel.getSurfaceArea().toString()
                        gone()
                    }
                    R.id.btn_calculate_volume -> {
                        activityMainBinding.tvResult.text = mainViewModel.getVolume().toString()
                        gone()
                    }
                    R.id.btn_save_file -> {
                        val fileModel = FileModel("test${System.currentTimeMillis()}", "Length:$valueLength\nWidth:$valueWidth\nHeight:$valueHeight")
                        FileHelper.writeToFile(fileModel, this)
                        Toast.makeText(this, "Saving " + fileModel.fileName + " file", Toast.LENGTH_SHORT).show()
                        gone()
                    }
                }
            }
        }
    }

    private fun showList(){
        val items = fileList()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih file yang diinginkan")
        builder.setItems(items) { dialog, item -> loadData(items[item].toString()) }
        val alert = builder.create()
        alert.show()
    }
    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        Toast.makeText(this, "Loading " + fileModel.fileName + " data", Toast.LENGTH_SHORT).show()
    }
    private fun visible() {
        activityMainBinding.btnCalculateVolume.visibility = View.VISIBLE
        activityMainBinding.btnCalculateCircumference.visibility = View.VISIBLE
        activityMainBinding.btnCalculateSurfaceArea.visibility = View.VISIBLE
        activityMainBinding.btnSave.visibility = View.GONE
    }
    private fun gone() {
        activityMainBinding.btnCalculateVolume.visibility = View.GONE
        activityMainBinding.btnCalculateCircumference.visibility = View.GONE
        activityMainBinding.btnCalculateSurfaceArea.visibility = View.GONE
        activityMainBinding.btnSave.visibility = View.VISIBLE
    }
}