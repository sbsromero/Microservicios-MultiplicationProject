package com.gamification.service;

import com.gamification.domain.GameStats;

public interface GameService {

	public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct);
	public GameStats retrieveStatsForUser(Long userId);

}
