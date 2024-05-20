package com.app.game.Dao;

import com.app.game.entity.GamePlay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GamePlayDao extends JpaRepository<GamePlay,Integer> {
    public GamePlay findByGameId(int gameId);
    public GamePlay findByPlayerOneUsernameAndGameStatus(String playerOneUsername,String status);
    public GamePlay findByGameIdAndPlayerOneUsernameAndGameStatus(Integer gameId, String playerOneUsername, String status);
    public GamePlay findByGameIdAndPlayerOneUsernameAndPlayer1GameStatus(Integer gameId, String playerOneUsername, String status);
    public GamePlay findByGameIdAndPlayerTwoUsernameAndPlayer2GameStatus(Integer gameId, String playerOneUsername, String status);
    public GamePlay findByPlayerTwoUsernameAndGameStatus(String playerOneUsername, String status);
    public List<GamePlay> findByGameStatus(String gameStatus);
    public List<GamePlay> findByPlayerOneUsernameAndPlayer1Status(String playerOneUsername,String status);
    public List<GamePlay> findByPlayerTwoUsernameAndPlayer2Status(String playerOneUsername,String status);
    public List<GamePlay> findByPlayer1GameStatus(String status);
    public List<GamePlay> findByPlayer2GameStatus(String status);
    public GamePlay findByPlayerOneUsernameAndPlayer1GameStatus(String playerOneUsername,String status);
    public GamePlay findByPlayerTwoUsernameAndPlayer2GameStatus(String playerOneUsername,String status);
}
