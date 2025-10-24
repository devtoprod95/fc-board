package com.fastcampus.fc_board.domain

import com.fastcampus.fc_board.exception.CommentNotUpdatableException
import com.fastcampus.fc_board.service.dto.CommentUpdateRequestDto
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class Comment(
    content: String,
    post: Post,
    createdBy: String,
) : BaseEntity(createdBy = createdBy) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var content: String = content
        protected set

    //    @JoinColumn(foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ManyToOne(fetch = FetchType.LAZY)
    var post: Post = post
        protected set

    fun update(commentUpdateRequestDto: CommentUpdateRequestDto) {
        if (commentUpdateRequestDto.updatedBy != this.createdBy) {
            throw CommentNotUpdatableException()
        }
        this.content = commentUpdateRequestDto.content
        super.updatedBy(commentUpdateRequestDto.updatedBy)
    }
}
