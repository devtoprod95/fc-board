package com.fastcampus.fc_board.service.dto

import com.fastcampus.fc_board.domain.Tag

data class TagResponseDto(
    val id: Long,
    val name: String,
    val createdBy: String,
    val createdAt: String,
)

fun Tag.toResponseDto() = TagResponseDto(
    id = id,
    name = name,
    createdBy = createdBy,
    createdAt = createdAt.toString()
)
