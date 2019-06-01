package com.gamification.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gamification.domain.BadgeCard;

public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {

	public List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(Long userId);
}
