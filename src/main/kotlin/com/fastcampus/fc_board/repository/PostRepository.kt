package com.fastcampus.fc_board.repository

import com.fastcampus.fc_board.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
}
