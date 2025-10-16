package com.fastcampus.fc_board.controller

import com.fastcampus.fc_board.controller.dto.CommentCreateRequest
import com.fastcampus.fc_board.controller.dto.CommentUpdateRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CommentController {

    @PostMapping("posts/{postId}/comments")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody commentCreateRequest: CommentCreateRequest,
    ): Long {
        println("postId: $postId")
        println("content: ${commentCreateRequest.content}")
        println("createdBy: ${commentCreateRequest.createdBy}")
        return postId
    }

    @PutMapping("comments/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody commentUpdateRequest: CommentUpdateRequest,
    ): Long {
        println("commentId: $commentId")
        println("content: ${commentUpdateRequest.content}")
        println("updatedBy: ${commentUpdateRequest.updatedBy}")
        return commentId
    }

    @DeleteMapping("comments/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
        @RequestParam deletedBy: String,
    ): Long {
        println("commentId: $commentId")
        println("deletedBy: $deletedBy")
        return commentId
    }
}
