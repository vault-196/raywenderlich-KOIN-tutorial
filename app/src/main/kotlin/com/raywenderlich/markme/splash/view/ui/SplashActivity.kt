/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.markme.splash.view.ui

import android.support.v7.app.AppCompatActivity
import com.raywenderlich.markme.main.view.ui.MainActivity
import com.raywenderlich.markme.splash.SplashContract
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SplashActivity : AppCompatActivity(), SplashContract.View {

    // Before DI
    //private val splashPresenter : SplashContract.Presenter by lazy { SplashPresenter(this) }

    // Using DI

    private val splashPresenter: SplashContract.Presenter by inject { parametersOf(this) }

    // Life

    override fun onResume() {
        super.onResume()

        startActivity<MainActivity>()
        splashPresenter.onViewCreated()
    }

    override fun finishView() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        splashPresenter.onViewDestroyed()
    }
}
