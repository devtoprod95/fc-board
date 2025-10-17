package com.fastcampus.fc_board.service

import com.fastcampus.fc_board.domain.Like
import com.fastcampus.fc_board.exception.PostNotFoundException
import com.fastcampus.fc_board.repository.LikeRepository
import com.fastcampus.fc_board.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LikeService(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
) {

    @Transactional
    fun createLike(postId: Long, createdBy: String): Long {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()

        return likeRepository.save(Like(post = post, createdBy = createdBy)).id
    }
}
