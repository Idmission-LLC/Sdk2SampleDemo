package com.idmission.sdk2.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.idmission.client.model.InitializeResponse
import com.idmission.client.model.Response
import com.idmission.client.model.UiCustomizationOptions
import com.idmission.identityproofing.IdentityProofingSDK
import com.idmission.sdk2.R
import com.idmission.utils.LANGUAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    var initializeApiBaseUrl = "https://demo.idmission.com/"
    var apiBaseUrl = "https://apidemo.idmission.com/"
    var loginID = "SDK_C"
    var password = "Idmission#2"
    var merchantID: Long = 12511
    var productID = 4130
    var productName = "Identity_Validation_and_Face_Matching"
    var lang = "EN"
    var isSDKinit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val edtUrl=findViewById<EditText>(R.id.edit_text_url)
            edtUrl.setText(initializeApiBaseUrl)
        val edtLogin=findViewById<EditText>(R.id. edit_text_login_id)
            edtLogin.setText(loginID)
        val edtPassword=findViewById<EditText>(R.id.edit_text_password)
            edtPassword.setText(password)
        val edtMerchantId=findViewById<EditText>(R.id.edit_text_merchant_id)
            edtMerchantId.setText(merchantID.toString(), TextView.BufferType.EDITABLE)
        /*val debugSwitch = findViewById<SwitchCompat>(R.id.switch_btn_debug)
        val gpsSwitch = findViewById<SwitchCompat>(R.id.switch_btn_gps)*/

        findViewById<Button>(R.id.button_continue).setOnClickListener(View.OnClickListener {
            var response: Response<InitializeResponse>
            showProgress();
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    response = IdentityProofingSDK.initialize(
                        this@MainActivity,
                        edtUrl.text.toString(),
                        apiBaseUrl,
                        edtLogin.text.toString(),
                        edtPassword.text.toString(),
                        edtMerchantId.text.toString().toLong(),
                        true,
                        false,
                        UiCustomizationOptions(LANGUAGE.valueOf(lang), null, null, null, null, null, null)
                        )
                }
                if (response.errorStatus==null){
                    isSDKinit = true
                }
            }.invokeOnCompletion {
                startActivity(Intent(this,ServiceCallActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                hideProgress()
            }

        })
    }

    private fun showProgress() {
        findViewById<ProgressBar>(R.id.indeterminateBar).visibility=View.VISIBLE
        //findViewById<LinearLayout>(R.id.ll_main).visibility = View.GONE
    }

    private fun hideProgress() {
        findViewById<ProgressBar>(R.id.indeterminateBar).visibility=View.GONE
       // findViewById<LinearLayout>(R.id.ll_main).visibility = View.VISIBLE
    }
}