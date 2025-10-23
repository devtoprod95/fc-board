package com.fastcampus.fc_board.service

import com.fastcampus.fc_board.exception.PostNotDeletableException
import com.fastcampus.fc_board.exception.PostNotFoundException
import com.fastcampus.fc_board.repository.PostRepository
import com.fastcampus.fc_board.repository.TagRepository
import com.fastcampus.fc_board.service.dto.PostCreateRequestDto
import com.fastcampus.fc_board.service.dto.PostDetailResponseDto
import com.fastcampus.fc_board.service.dto.PostSearchRequestDto
import com.fastcampus.fc_board.service.dto.PostSummaryResponseDto
import com.fastcampus.fc_board.service.dto.PostUpdateRequestDto
import com.fastcampus.fc_board.service.dto.toDetailResponseDto
import com.fastcampus.fc_board.service.dto.toEntity
import com.fastcampus.fc_board.service.dto.toSummaryResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
    private val likeService: LikeService,
    private val tagRepository: TagRepository,
) {
    fun getPost(id: Long): PostDetailResponseDto {
        return postRepository.findByIdOrNull(id)?.toDetailResponseDto(likeService::countLike) ?: throw PostNotFoundException()
    }

    fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<PostSummaryResponseDto> {
        postSearchRequestDto.tag?.let {
            return tagRepository.findPageBy(pageRequest, it).toSummaryResponseDto(likeService::countLike)
        }
        return postRepository.findPageBy(pageRequest, postSearchRequestDto).toSummaryResponseDto(likeService::countLike)
    }

    @Transactional
    fun createPost(requestDto: PostCreateRequestDto): Long {
        return postRepository.save(requestDto.toEntity()).id
    }

    @Transactional
    fun updatePost(id: Long, requestDto: PostUpdateRequestDto): Long {
        val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
        post.update(requestDto)
        return id
    }

    @Transactional
    fun deletePost(id: Long, deletedBy: String): Long {
        val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()

        if (deletedBy != post.createdBy) {
            throw PostNotDeletableException()
        }
        postRepository.delete(post)
        return id
    }
}
