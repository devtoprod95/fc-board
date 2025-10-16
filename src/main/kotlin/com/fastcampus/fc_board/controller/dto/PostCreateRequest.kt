package com.fastcampus.fc_board.controller.dto

data class PostCreateRequest(
    val title: String,
    val content: String,
    val createdBy: String,
)
