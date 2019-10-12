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

package com.raywenderlich.markme.feature.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.raywenderlich.markme.R
import com.raywenderlich.markme.model.Student
import kotlinx.android.synthetic.main.card_feature_grading.view.*

class FeatureGradingAdapter(val dataList: List<Student>?)
    : RecyclerView.Adapter<FeatureGradingAdapter.ViewHolder>(), RwAdapter<Student> {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelName: TextView? = itemView.card_feat_grading__label__name
        val etGrade: TextView? = itemView.card_feat_grading__edittext__grade
    }

    override fun getItemCount(): Int = dataList?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewRow = LayoutInflater.from(parent.context).inflate(R.layout.card_feature_grading, parent, false)
        return ViewHolder(viewRow)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.labelName?.text = dataList?.let { it[position].name }
        holder.etGrade?.apply {
            dataList?.let { list ->
                val value = list[position].grade
                text = if (value != -1) value.toString() else ""
            }
            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    dataList?.get(position)?.grade = text.toString().toInt()
                }
            }
        }
    }

    override fun updateData(data: List<Student>) {
        data.forEach { student ->
            dataList?.first { student.name == it.name }?.attendance = student.attendance
        }
        notifyDataSetChanged()
    }

    override fun getData(): List<Student>? = dataList
}