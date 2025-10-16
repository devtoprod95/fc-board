package com.fastcampus.fc_board.controller.dto

data class CommentUpdateRequest(
    val content: String? = null,
    val updatedBy: String? = null,
)
