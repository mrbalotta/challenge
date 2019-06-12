package br.com.wirecard.base.view.ui

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProviders
import br.com.wirecard.base.gateway.mvvm.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<V: BaseViewModel>: BottomSheetDialogFragment() {
    protected lateinit var viewModel: V

    protected abstract fun getViewModelClass(): Class<V>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViews(view)
    }

    protected open fun setupViews(view: View) {}

    protected open fun setupViewModel() {
        val clazz = getViewModelClass()
        viewModel = ViewModelProviders.of(requireActivity()).get(clazz)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        if (dialog.window == null) return dialog
        dialog.setCanceledOnTouchOutside(false)

        dialog.setOnShowListener { expandSheet(dialog as BottomSheetDialog) }

        return dialog
    }

    private fun expandSheet(dialog: BottomSheetDialog) {
        val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) ?: return
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = bottomSheet.height
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}