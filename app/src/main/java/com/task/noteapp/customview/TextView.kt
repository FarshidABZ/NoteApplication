package com.task.noteapp.customview

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textview.MaterialTextView
import com.task.noteapp.R


/********** Title Text view  ***************/

class TitleTextView(context: Context, attrs: AttributeSet?) :
    MaterialTextView(context, attrs, R.attr.titleMedium)

class NoteTitleTextView(context: Context, attrs: AttributeSet?) :
    MaterialTextView(context, attrs, R.attr.noteTitle)


/********** Body Text view  ***************/

class BodyTextViewTextView(context: Context, attrs: AttributeSet?) :
    MaterialTextView(context, attrs, R.attr.noteBody)