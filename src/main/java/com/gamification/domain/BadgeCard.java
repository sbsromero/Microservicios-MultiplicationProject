package com.gamification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Entity
public class BadgeCard {

	@Id
	@GeneratedValue
	@Column(name = "BADGE_ID")
	private final Long badgeId;

	private final Long userId;

	private final long badgeTimestap;

	private final Badge badge;

	public BadgeCard() {
		this(null, null, 0, null);
	}

	public BadgeCard(Long userId, Badge badge) {
		this(null, userId, System.currentTimeMillis(), badge);
	}

}
