package br.com.wirecard.base.view.ui

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import br.com.wirecard.R

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
