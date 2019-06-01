package com.gamification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamification.domain.Badge;
import com.gamification.domain.BadgeCard;
import com.gamification.domain.GameStats;
import com.gamification.domain.ScoreCard;
import com.gamification.repository.BadgeCardRepository;
import com.gamification.repository.ScoreCardRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GameServiceImpl implements GameService {

	public static final int LUCKY_NUMBER = 42;

	private ScoreCardRepository ScoreCardRepository;
	private BadgeCardRepository BadgeCardRepository;

	@Autowired
	public GameServiceImpl(ScoreCardRepository scoreCardRepository, BadgeCardRepository badgeCardRepository) {
		this.ScoreCardRepository = scoreCardRepository;
		this.BadgeCardRepository = badgeCardRepository;
	}

	@Override
	public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {
		if (correct) {
			ScoreCard scoreCard = new ScoreCard(userId, attemptId);
			ScoreCardRepository.save(scoreCard);
			log.info("User with id {} scored {} points for attempt id {}", userId, scoreCard.getScore(), attemptId);
			List<BadgeCard> badgeCards = processForBadges(userId, attemptId);
			return new GameStats(userId, scoreCard.getScore(),
					badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
		}

		return GameStats.emptyStats(userId);
	}

	private List<BadgeCard> processForBadges(Long userId, Long attemptId) {
		List<BadgeCard> badgeCards = new ArrayList<>();
		int totalScore = ScoreCardRepository.getTotalScoreForUser(userId);
		log.info("New score for user {} is {}", userId, totalScore);

		List<ScoreCard> scoreCardList = ScoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
		List<BadgeCard> badgeCardList = BadgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);

		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId)
				.ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.SILVER_MULTIPLICATOR, totalScore, 500, userId)
				.ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.GOLD_MULTIPLICATOR, totalScore, 999, userId)
				.ifPresent(badgeCards::add);

		if (scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.FIRST_WON)) {
			BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, userId);
			badgeCardList.add(firstWonBadge);
		}

		return badgeCards;
	}

	@Override
	public GameStats retrieveStatsForUser(Long userId) {
		int score = ScoreCardRepository.getTotalScoreForUser(userId);
		List<BadgeCard> badgeCards = BadgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
		return new GameStats(userId, score, badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
	}

	private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(List<BadgeCard> badgeCardList, Badge badge,
			int totalScore, int scoreThreshold, Long userId) {
		if (totalScore >= scoreThreshold && !containsBadge(badgeCardList, badge)) {
			return Optional.of(giveBadgeToUser(badge, userId));
		}
		return Optional.empty();
	}

	private boolean containsBadge(List<BadgeCard> badgeCardList, Badge badge) {
		return badgeCardList.stream().anyMatch(badgeFromList -> badgeFromList.getBadge().equals(badge));
	}

	private BadgeCard giveBadgeToUser(Badge badge, Long userId) {
		BadgeCard badgeCard = new BadgeCard(userId, badge);
		BadgeCardRepository.save(badgeCard);
		log.info("User with id {} won a new badge: {}", userId, badge);
		return badgeCard;
	}

}
