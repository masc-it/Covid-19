package com.onipot.covid19.dialog

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.setPeekHeight
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.onipot.covid19.Dimension
import com.onipot.covid19.R
import com.onipot.covid19.adapter.DimensionsRvAdapter
import com.onipot.covid19.callbacks.Chart


class DimensionsDialog(private val context: Context, private val dimensions: List<Dimension>) {


    fun showNew(callback: Chart) {
        val rvDimensions: RecyclerView

        var dialog = MaterialDialog(context, BottomSheet(LayoutMode.MATCH_PARENT))

        dialog.setPeekHeight( context.resources.displayMetrics.heightPixels/2)
        dialog.customView(R.layout.dialog_dimensions)

        dialog = dialog.noAutoDismiss()

        val customView = dialog.getCustomView()

        rvDimensions = customView.findViewById(R.id.dimensions)
        rvDimensions.layoutManager = LinearLayoutManager(context)
        rvDimensions.adapter = DimensionsRvAdapter(dimensions)

        dialog.positiveButton(R.string.ok) { d ->

            callback.onDimensionsChanged()
            dialog.dismiss()


        }


        dialog.show()
    }

}