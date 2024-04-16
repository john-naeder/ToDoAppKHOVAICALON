package com.loc.todoapp.data.local.taskdata

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


// Here is where we define the task entity that will hold the task data

@Entity
data class TaskModel(
    @PrimaryKey val id: Int? = null,
    var title: String,
    var description: String,
    var endDate: LocalDate?,
    var isDone: Boolean,
    var startDate: LocalDate
)