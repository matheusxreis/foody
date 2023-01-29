package com.matheusxreis.foody.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.matheusxreis.foody.R
import kotlinx.android.synthetic.main.fragment_instructions.view.*
import com.matheusxreis.foody.models.Result
import com.matheusxreis.foody.utils.Constants

class InstructionsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.fragment_instructions, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        mView.instructions_web_view.webViewClient = object: WebViewClient() {}
        val webUrl:String = myBundle!!.sourceUrl
        mView.instructions_web_view.loadUrl(webUrl)

        return mView
    }
}