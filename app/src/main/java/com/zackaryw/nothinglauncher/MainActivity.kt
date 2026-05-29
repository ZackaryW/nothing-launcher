package com.zackaryw.nothinglauncher

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var gestureDetector: GestureDetector
    private var appMenuState = AppMenuState.CLOSED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gestureDetector = GestureDetector(this, SwipeListener())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }

    override fun onResume() {
        super.onResume()
        appMenuState = AppMenuState.CLOSED
    }

    private fun openAppMenu() {
        startActivity(Intent(this, AppsActivity::class.java))
    }

    private fun openAppMenuIfClosed() {
        if (appMenuState == AppMenuState.CLOSED) {
            appMenuState = AppMenuToggle.nextState(appMenuState)
            openAppMenu()
        }
    }

    private inner class SwipeListener : GestureDetector.SimpleOnGestureListener() {
        private val swipeThreshold = 100
        private val swipeVelocityThreshold = 100

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            openAppMenuIfClosed()
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val diffX = e2.x - (e1?.x ?: 0f)
            val diffY = e2.y - (e1?.y ?: 0f)
            if (abs(diffX) > abs(diffY)) {
                if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                    openAppMenu()
                    return true
                }
            } else {
                if (abs(diffY) > swipeThreshold && abs(velocityY) > swipeVelocityThreshold) {
                    openAppMenu()
                    return true
                }
            }
            return false
        }
    }
}
