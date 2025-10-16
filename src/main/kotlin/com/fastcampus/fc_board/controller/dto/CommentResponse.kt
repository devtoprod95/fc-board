package com.fastcampus.fc_board.controller.dto

data class CommentResponse(
    val id: Long,
    val content: String,
    val createdBy: String,
    val updatedBy: String,
)
