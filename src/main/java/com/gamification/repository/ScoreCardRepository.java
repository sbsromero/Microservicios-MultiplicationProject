package com.gamification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gamification.domain.LeaderBoardRow;
import com.gamification.domain.ScoreCard;

public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long> {

	@Query("SELECT SUM(s.score) FROM com.microservice.gamification.domain.ScoreCard s "
			+ "WHERE s.userId = :userId GROUP BY s.userId")
	public int getTotalScoreForUser(@Param("userId") Long userId);

	@Query("SELECT NEW com.microservice.gamification.domain.LeaderBoardRow(s.userId, SUM(s.score))"
			+ " FROM com.microservice.gamification.domain.ScoreCard s "
			+ "GROUP BY s.userId ORDER BY SUM(s.score) DESC")
	public List<LeaderBoardRow> findFirst10();

	public List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(Long userId);
}
