package com.fastcampus.fc_board.controller.dto

import com.fastcampus.fc_board.service.dto.PostSearchRequestDto
import org.springframework.web.bind.annotation.RequestParam

data class PostSearchRequest(
    @RequestParam
    val title: String? = null,
    @RequestParam
    val createdBy: String? = null,
    @RequestParam
    val tag: String? = null,
)

fun PostSearchRequest.toDto() = PostSearchRequestDto(
    title = title,
    createdBy = createdBy,
    tag = tag
)
