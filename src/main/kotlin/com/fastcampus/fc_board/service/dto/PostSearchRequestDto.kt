package com.fastcampus.fc_board.service.dto

import com.fastcampus.fc_board.controller.dto.PostSearchRequest

data class PostSearchRequestDto(
    val title: String? = null,
    val createdBy: String? = null,
)

fun PostSearchRequest.toDto() = PostSearchRequestDto(
    title = title,
    createdBy = createdBy
)
