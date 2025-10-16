package com.fastcampus.fc_board.service.dto

import com.fastcampus.fc_board.domain.Comment
import com.fastcampus.fc_board.domain.Post

data class CommentCreateRequestDto(
    val content: String,
    val createdBy: String,
)

fun CommentCreateRequestDto.toEntity(post: Post) = Comment(
    content = content,
    createdBy = createdBy,
    post = post
)
