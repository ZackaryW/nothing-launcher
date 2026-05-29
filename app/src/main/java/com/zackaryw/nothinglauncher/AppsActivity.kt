package com.zackaryw.nothinglauncher

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AppsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps)

        val recyclerView = findViewById<RecyclerView>(R.id.apps_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AppsAdapter(getInstalledApps()) { app ->
            val launchIntent = packageManager.getLaunchIntentForPackage(
                app.activityInfo.packageName
            )
            launchIntent?.let { startActivity(it) }
        }
        recyclerView.addOnItemTouchListener(AppMenuBackgroundClickListener(recyclerView))
    }

    private inner class AppMenuBackgroundClickListener(
        private val recyclerView: RecyclerView
    ) : RecyclerView.SimpleOnItemTouchListener() {
        private var isTrackingBackgroundTap = false
        private val gestureDetector = GestureDetector(
            this@AppsActivity,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onDown(e: MotionEvent): Boolean {
                    return true
                }

                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    if (!isTrackingBackgroundTap) {
                        return false
                    }
                    handleMenuClick(AppMenuState.OPEN)
                    return true
                }
            }
        )

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            if (e.actionMasked == MotionEvent.ACTION_DOWN) {
                isTrackingBackgroundTap = isAppMenuBackground(e)
            }
            if (!isTrackingBackgroundTap) {
                return false
            }
            val handled = gestureDetector.onTouchEvent(e)
            if (e.actionMasked == MotionEvent.ACTION_UP || e.actionMasked == MotionEvent.ACTION_CANCEL) {
                isTrackingBackgroundTap = false
            }
            return handled
        }

        private fun isAppMenuBackground(e: MotionEvent): Boolean {
            return recyclerView.findChildViewUnder(e.x, e.y) == null
        }
    }

    private fun handleMenuClick(currentState: AppMenuState) {
        when (AppMenuToggle.nextState(currentState)) {
            AppMenuState.CLOSED -> finish()
            AppMenuState.OPEN -> Unit
        }
    }

    private fun getInstalledApps(): List<ResolveInfo> {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val apps = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            packageManager.queryIntentActivities(
                intent,
                android.content.pm.PackageManager.ResolveInfoFlags.of(0)
            )
        } else {
            @Suppress("DEPRECATION")
            packageManager.queryIntentActivities(intent, 0)
        }
        return apps
            .filter { it.activityInfo.packageName != packageName }
            .sortedBy { it.loadLabel(packageManager).toString().lowercase() }
    }

    private inner class AppsAdapter(
        private val apps: List<ResolveInfo>,
        private val onAppClick: (ResolveInfo) -> Unit
    ) : RecyclerView.Adapter<AppsAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val label: TextView = view.findViewById(R.id.app_label)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_app, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val app = apps[position]
            holder.label.text = app.loadLabel(packageManager)
            holder.itemView.setOnClickListener { onAppClick(app) }
        }

        override fun getItemCount() = apps.size
    }
}
