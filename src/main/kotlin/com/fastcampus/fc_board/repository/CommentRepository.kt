package com.fastcampus.fc_board.repository

import com.fastcampus.fc_board.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long>
