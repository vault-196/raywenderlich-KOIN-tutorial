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

package com.raywenderlich.markme.feature

import com.raywenderlich.markme.utils.ClassSection

interface FeatureContract {
    interface View<T> {
        fun showToastMessage(msg: String)
        fun onPersistedDataLoaded(data: List<T>)
    }

    interface Presenter<T> {
        fun onViewDestroyed()
        fun loadPersistedData(data: List<T>, featureType: ClassSection)
        // User Actions
        fun onSave2PrefsClick(data: List<T>?)

        fun onSave2DbClick(data: List<T>?)
    }

    interface Model<T> {
        fun add2Db(data: List<T>, callback: (String) -> Unit)
        fun add2Prefs(data: List<T>, callback: (String) -> Unit)
        fun fetchFromDb(data: List<T>, callback: (List<T>) -> Unit)
        fun fetchFromPrefs(data: List<T>): List<T>
    }
}