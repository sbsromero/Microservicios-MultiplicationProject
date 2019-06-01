package com.gamification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Entity
public class ScoreCard {

	public static final int DEFAULT_SCORE = 10;

	@Column(name = "CARD_ID")
	private final Long cardId;

	@Column(name = "USER_ID")
	private final Long userId;

	@Column(name = "ATTEMPT_ID")
	private final Long attemptId;

	@Column(name = "SCORE_TS")
	private final long scoreTimestamp;

	@Column(name = "SCORE")
	private final int score;

	public ScoreCard() {
		this(null, null, null, 0, 0);
	}

	public ScoreCard(Long userId, Long attemptId) {
		this(null, userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
	}
}
