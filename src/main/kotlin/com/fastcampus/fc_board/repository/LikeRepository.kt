package com.fastcampus.fc_board.repository

import com.fastcampus.fc_board.domain.Like
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Like, Long>
