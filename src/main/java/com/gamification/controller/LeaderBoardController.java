package com.gamification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamification.domain.LeaderBoardRow;
import com.gamification.service.LeaderBoardService;

@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {

	private final LeaderBoardService leaderBoardService;
	
	@Autowired
	public LeaderBoardController(LeaderBoardService leaderBoardService) {
		this.leaderBoardService = leaderBoardService;
	}
	
	@GetMapping
	public List<LeaderBoardRow> getLeaderBoard(){
		return leaderBoardService.getCurrentLeaderBoard();
	}
}
