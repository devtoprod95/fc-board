package com.fastcampus.fc_board.controller.dto

import com.fastcampus.fc_board.service.dto.CommentUpdateRequestDto

data class CommentUpdateRequest(
    val content: String,
    val updatedBy: String,
)

fun CommentUpdateRequest.toDto() = CommentUpdateRequestDto(
    content = content,
    updatedBy = updatedBy
)
