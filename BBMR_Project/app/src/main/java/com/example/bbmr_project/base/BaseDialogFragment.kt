package com.example.bbmr_project.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle

open class BaseDialogFragment : DialogFragment() {

    private val tag: String = "DialogFragment Lifecycle"
    private val prefix = "+++"
    private val className = this.javaClass.simpleName

    private fun printLifecycleLog(lifecycle: String) {
        Log.d(tag, "$prefix $className $lifecycle")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        printLifecycleLog("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        printLifecycleLog("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        printLifecycleLog("onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        printLifecycleLog("onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        printLifecycleLog("onDestroy")
        super.onDestroy()
    }
}