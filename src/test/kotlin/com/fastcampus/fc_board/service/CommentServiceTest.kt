package com.fastcampus.fc_board.service

import com.fastcampus.fc_board.domain.Comment
import com.fastcampus.fc_board.domain.Post
import com.fastcampus.fc_board.exception.CommentNotDeletableException
import com.fastcampus.fc_board.exception.CommentNotFoundException
import com.fastcampus.fc_board.exception.CommentNotUpdatableException
import com.fastcampus.fc_board.exception.PostNotFoundException
import com.fastcampus.fc_board.repository.CommentRepository
import com.fastcampus.fc_board.repository.PostRepository
import com.fastcampus.fc_board.service.dto.CommentCreateRequestDto
import com.fastcampus.fc_board.service.dto.CommentUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.testcontainers.perSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.testcontainers.containers.GenericContainer

@SpringBootTest
class CommentServiceTest(
    private val commentService: CommentService,
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) : BehaviorSpec({
    val redisContainer = GenericContainer<Nothing>("redis:5.0.3-alpine")
    beforeSpec {
        redisContainer.portBindings = listOf("16379:6379")
        redisContainer.start()
        listener(redisContainer.perSpec())
    }
    afterSpec {
        redisContainer.stop()
    }
    given("댓글 작성시") {
        val post = postRepository.save(
            Post(
                title = "게시글 제목",
                content = "게시글 내용",
                createdBy = "게시글 작성자"
            )
        )
        When("인풋이 정상적으로 들어오면") {
            val commentId = commentService.createComment(
                post.id,
                CommentCreateRequestDto(
                    content = "댓글 내용",
                    createdBy = "댓글 작성자"
                )
            )
            then("정상 생성됨을 확인한다.") {
                val comment = commentRepository.findByIdOrNull(commentId)
                commentId shouldBeGreaterThan 0L
                comment?.content shouldBe "댓글 내용"
                comment?.createdBy shouldBe "댓글 작성자"
            }
        }
        When("게시글이 존재하지 않을 때") {
            then("게시글 존재하지 않음 예외가 발생한다.") {
                shouldThrow<PostNotFoundException> {
                    commentService.createComment(
                        9999L,
                        CommentCreateRequestDto(
                            content = "댓글 내용",
                            createdBy = "댓글 작성자"
                        )
                    )
                }
            }
        }
    }
    given("댓글 수정시") {
        val post = postRepository.save(
            Post(
                title = "게시글 제목",
                content = "게시글 내용",
                createdBy = "게시글 작성자"
            )
        )
        val savedComment = commentRepository.save(
            Comment(
                content = "댓글 내용",
                createdBy = "댓글 작성자",
                post = post
            )
        )
        When("인풋이 정상적으로 들어오면") {
            val updatedId = commentService.updateComment(
                savedComment.id,
                CommentUpdateRequestDto(
                    content = "수정된 댓글 내용",
                    updatedBy = "댓글 작성자"
                )
            )
            then("정상 수정됨을 확인한다.") {
                val updatedComment = commentRepository.findByIdOrNull(updatedId)
                updatedId shouldBe savedComment.id
                updatedComment?.content shouldBe "수정된 댓글 내용"
                updatedComment?.updatedBy shouldBe "댓글 작성자"
            }
        }
        When("댓글이 없을 때") {
            then("댓글이 존재하지 않는 예외가 발생한다.") {
                shouldThrow<CommentNotFoundException> {
                    commentService.updateComment(
                        9999L,
                        CommentUpdateRequestDto(
                            content = "수정된 댓글 내용",
                            updatedBy = "댓글 작성자"
                        )
                    )
                }
            }
        }
        When("작성자와 수정자가 다르면") {
            then("수정할 수 없는 게시물 예외가 발생한다.") {
                shouldThrow<CommentNotUpdatableException> {
                    commentService.updateComment(
                        savedComment.id,
                        CommentUpdateRequestDto(
                            content = "수정된 댓글 내용",
                            updatedBy = "수정된 댓글 작성자2"
                        )
                    )
                }
            }
        }
    }
    given("댓글 삭제시") {
        val post = postRepository.save(
            Post(
                title = "게시글 제목",
                content = "게시글 내용",
                createdBy = "게시글 작성자"
            )
        )
        val savedComment = commentRepository.save(
            Comment(
                content = "댓글 내용",
                createdBy = "댓글 작성자",
                post = post
            )
        )
        val savedComment2 = commentRepository.save(
            Comment(
                content = "댓글 내용",
                createdBy = "댓글 작성자",
                post = post
            )
        )
        When("인풋이 정상적으로 들어오면") {
            val deletedId = commentService.deleteComment(savedComment.id, "댓글 작성자")
            then("정상 삭제됨을 확인한다.") {
                deletedId shouldBe savedComment.id
                commentRepository.findByIdOrNull(deletedId) shouldBe null
            }
        }
        When("작성자와 삭제자가 다르면") {
            then("삭제할 수 없는 게시물 예외가 발생한다.") {
                shouldThrow<CommentNotDeletableException> {
                    commentService.deleteComment(savedComment2.id, "삭제 작성자")
                }
            }
        }
    }
})
