package com.fastcampus.fc_board.controller.dto

import com.fastcampus.fc_board.service.dto.CommentCreateRequestDto

data class CommentCreateRequest(
    val content: String,
    val createdBy: String,
)

fun CommentCreateRequest.toDto() = CommentCreateRequestDto(
    content = content,
    createdBy = createdBy
)
