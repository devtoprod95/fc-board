package com.fastcampus.fc_board.service

import com.fastcampus.fc_board.domain.Like
import com.fastcampus.fc_board.exception.PostNotFoundException
import com.fastcampus.fc_board.repository.LikeRepository
import com.fastcampus.fc_board.repository.PostRepository
import com.fastcampus.fc_board.util.RedisUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LikeService(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
    private val redisUtil: RedisUtil,
) {

    @Transactional
    fun createLike(postId: Long, createdBy: String): Long {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()

        redisUtil.increment(redisUtil.getLikeCountKey(postId))
        return likeRepository.save(Like(post = post, createdBy = createdBy)).id
    }

    fun countLike(postId: Long): Long {
        redisUtil.getCount(redisUtil.getLikeCountKey(postId))?.let { return it }

        with(likeRepository.countByPostId(postId)) {
            redisUtil.setData(redisUtil.getLikeCountKey(postId), this)
            return this
        }
    }
}
