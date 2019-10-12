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

package com.raywenderlich.markme.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.raywenderlich.markme.feature.FeatureContract
import com.raywenderlich.markme.model.Student
import com.raywenderlich.markme.model.database.AppDatabase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.koin.core.KoinComponent
import org.koin.core.inject

object AppRepository : FeatureContract.Model<Student>, KoinComponent {

    private const val MSG_DATA_SAVED_TO_DB = "Data saved to DB"
    private const val MSG_DATA_SAVED_TO_PREFS = "Data saved to prefs"

    private val database: AppDatabase by inject()
    private val sharedPreferences: SharedPreferences by inject()

    override fun add2Db(data: List<Student>, callback: (String) -> Unit) {
        doAsync {
            database.userDao().insertStudentList(data)
            uiThread {
                callback(MSG_DATA_SAVED_TO_DB)
            }
        }
    }

    override fun add2Prefs(data: List<Student>, callback: (String) -> Unit) {
        doAsync {
            data.forEach {
                with(sharedPreferences.edit()) {
                    val jsonString = Gson().toJson(it)
                    putString(it.name, jsonString).commit()
                }
            }
            uiThread {
                callback(MSG_DATA_SAVED_TO_PREFS)
            }
        }
    }

    override fun fetchFromDb(data: List<Student>, callback: (List<Student>) -> Unit) {
        doAsync {
            val list = database.userDao().loadAllStudents()
            uiThread {
                callback(list)
            }
        }
    }

    override fun fetchFromPrefs(data: List<Student>): List<Student> {
        data.forEach {
            val item: Student? = Gson().fromJson(sharedPreferences.getString(it.name, ""), Student::class.java)
            item?.let { persItem ->
                it.attendance = persItem.attendance
                it.grade = persItem.grade
            }
        }

        return data
    }
}