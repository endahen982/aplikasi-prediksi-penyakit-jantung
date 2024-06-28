package com.example.heartforecast

import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class SimulasiActivity : AppCompatActivity() {

    private lateinit var interpreter: Interpreter
    private val mModelPath = "disease.tflite"

    private lateinit var resultText: TextView
    private lateinit var edtAge: EditText
    private lateinit var edtSex: EditText
    private lateinit var edtChestPainType: EditText
    private lateinit var edtRestingBP: EditText
    private lateinit var edtCholesterol: EditText
    private lateinit var edtFastingBloodSugar: EditText
    private lateinit var edtRestecg: EditText
    private lateinit var edtMaxHR: EditText
    private lateinit var edtExang: EditText
    private lateinit var edtOldpeak: EditText
    private lateinit var edtSlope: EditText
    private lateinit var edtNumMajorVessels: EditText
    private lateinit var edtThal: EditText
    private lateinit var checkButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulasi)

        resultText = findViewById(R.id.txtResult)
        edtAge = findViewById(R.id.editAge)
        edtSex = findViewById(R.id.editSex)
        edtChestPainType = findViewById(R.id.editChestPainType)
        edtRestingBP = findViewById(R.id.editRestingBP)
        edtCholesterol = findViewById(R.id.editCholesterol)
        edtFastingBloodSugar = findViewById(R.id.editFastingBloodSugar)
        edtRestecg = findViewById(R.id.editRestecg)
        edtMaxHR = findViewById(R.id.editMaxHR)
        edtExang = findViewById(R.id.editExang)
        edtOldpeak = findViewById(R.id.editOldpeak)
        edtSlope = findViewById(R.id.editSlope)
        edtNumMajorVessels = findViewById(R.id.editNumMajorVessels)
        edtThal = findViewById(R.id.editThal)
        checkButton = findViewById(R.id.btnCheck)

        checkButton.setOnClickListener {
            if (validateInputs()) {
                try {
                    val result = doInference(
                        edtAge.text.toString().toFloatOrNull() ?: 0f,
                        edtSex.text.toString().toFloatOrNull() ?: 0f,
                        edtChestPainType.text.toString().toFloatOrNull() ?: 0f,
                        edtRestingBP.text.toString().toFloatOrNull() ?: 0f,
                        edtCholesterol.text.toString().toFloatOrNull() ?: 0f,
                        edtFastingBloodSugar.text.toString().toFloatOrNull() ?: 0f,
                        edtRestecg.text.toString().toFloatOrNull() ?: 0f,
                        edtMaxHR.text.toString().toFloatOrNull() ?: 0f,
                        edtExang.text.toString().toFloatOrNull() ?: 0f,
                        edtOldpeak.text.toString().toFloatOrNull() ?: 0f,
                        edtSlope.text.toString().toFloatOrNull() ?: 0f,
                        edtNumMajorVessels.text.toString().toFloatOrNull() ?: 0f,
                        edtThal.text.toString().toFloatOrNull() ?: 0f
                    )
                    resultText.text = if (result == 0) "No Heart Disease" else "Heart Disease"
                } catch (e: Exception) {
                    resultText.text = "Prediction Failed: ${e.message}"
                }
            } else {
                showAlertDialog()
            }
        }

        initInterpreter()
    }

    private fun validateInputs(): Boolean {
        return edtAge.text.isNotBlank() &&
                edtSex.text.isNotBlank() &&
                edtChestPainType.text.isNotBlank() &&
                edtRestingBP.text.isNotBlank() &&
                edtCholesterol.text.isNotBlank() &&
                edtFastingBloodSugar.text.isNotBlank() &&
                edtRestecg.text.isNotBlank() &&
                edtMaxHR.text.isNotBlank() &&
                edtExang.text.isNotBlank() &&
                edtOldpeak.text.isNotBlank() &&
                edtSlope.text.isNotBlank() &&
                edtNumMajorVessels.text.isNotBlank() &&
                edtThal.text.isNotBlank()
    }

    private fun showAlertDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("WARNING!!")
            .setMessage("Please fill all input fields")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }

    private fun initInterpreter() {
        val options = Interpreter.Options()
        options.setNumThreads(5)
        options.setUseNNAPI(true)
        interpreter = Interpreter(loadModelFile(assets, mModelPath), options)
    }

    private fun doInference(
        age: Float, sex: Float, chestPainType: Float, restingBP: Float,
        cholesterol: Float, fastingBloodSugar: Float, restecg: Float, maxHR: Float,
        exang: Float, oldpeak: Float, slope: Float, numMajorVessels: Float, thal: Float
    ): Int {
        val inputVal = floatArrayOf(
            age, sex, chestPainType, restingBP,
            cholesterol, fastingBloodSugar, restecg, maxHR,
            exang, oldpeak, slope, numMajorVessels, thal
        )
        val output = Array(1) { FloatArray(2) }
        interpreter.run(arrayOf(inputVal), output)
        Log.e("result", output[0].contentToString())
        return output[0].indexOfFirst { it == output[0].maxOrNull() }
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
}
