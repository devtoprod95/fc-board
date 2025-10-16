package com.fastcampus.fc_board.service

import com.fastcampus.fc_board.exception.CommentNotDeletableException
import com.fastcampus.fc_board.exception.CommentNotFoundException
import com.fastcampus.fc_board.exception.PostNotFoundException
import com.fastcampus.fc_board.repository.CommentRepository
import com.fastcampus.fc_board.repository.PostRepository
import com.fastcampus.fc_board.service.dto.CommentCreateRequestDto
import com.fastcampus.fc_board.service.dto.CommentUpdateRequestDto
import com.fastcampus.fc_board.service.dto.toEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) {

    @Transactional
    fun createComment(postId: Long, createRequestDto: CommentCreateRequestDto): Long {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()
        return commentRepository.save(createRequestDto.toEntity(post)).id
    }

    @Transactional
    fun updateComment(commentId: Long, updateRequestDto: CommentUpdateRequestDto): Long {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw CommentNotFoundException()
        comment.update(updateRequestDto)
        return commentId
    }

    @Transactional
    fun deleteComment(commentId: Long, deletedBy: String): Long {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw CommentNotFoundException()
        if (deletedBy != comment.createdBy) {
            throw CommentNotDeletableException()
        }
        commentRepository.delete(comment)
        return commentId
    }
}
