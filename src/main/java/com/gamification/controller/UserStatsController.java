package com.gamification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gamification.domain.GameStats;
import com.gamification.service.GameService;

@RestController
@RequestMapping("/stats")
public class UserStatsController {
	
	private final GameService gameService;
	
	@Autowired
	public UserStatsController(GameService gameService) {
		this.gameService = gameService;
	}
	
	@GetMapping
	public GameStats getStatsForUser(@RequestParam("userId") Long userId) {
		return gameService.retrieveStatsForUser(userId);
	}

}
