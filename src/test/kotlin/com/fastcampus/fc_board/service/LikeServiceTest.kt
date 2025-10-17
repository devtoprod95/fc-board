package com.fastcampus.fc_board.service

import com.fastcampus.fc_board.exception.PostNotFoundException
import com.fastcampus.fc_board.repository.LikeRepository
import com.fastcampus.fc_board.service.dto.PostCreateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class LikeServiceTest(
    private val postService: PostService,
    private val likeService: LikeService,
    private val likeRepository: LikeRepository,
) : BehaviorSpec({
    given("좋아요 생성시") {
        val createdPostId = postService.createPost(PostCreateRequestDto("제목", "내용", "harris", listOf("tag1", "tag2")))
        When("인풋이 정상적으로 들어오면") {
            val likeId = likeService.createLike(createdPostId, "harris")
            then("좋아요가 정상적으로 생성됨을 확인한다.") {
                val like = likeRepository.findByIdOrNull(likeId)
                like shouldNotBe null
                like?.createdBy shouldBe "harris"
            }
        }
        When("게시글이 존재하지 않으면") {
            then("존재하지 않는 게시글 예외가 발생한다.") {
                shouldThrow<PostNotFoundException> {
                    likeService.createLike(9999L, "harris")
                }
            }
        }
    }
})
