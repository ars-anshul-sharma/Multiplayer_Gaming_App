package com.app.game.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="game_play_info")
public class GamePlay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int gameId;

    @Column(name = "room_id")
    private String roomId;

    @Column(name = "player_1_username")
    private String playerOneUsername;

    @Column(name = "player_2_username")
    private String playerTwoUsername;

    @Column(name = "betting_amount")
    private double bettingAmount;

    @Column(name = "winning_amount")
    private double winningAmount;

    @Column(name = "game_status")
    private String gameStatus;

    @Column(name = "platform_fee")
    private double platformFee;

    @Column(name = "player1_status")
    private String player1Status;

    @Column(name = "player2_status")
    private String player2Status;

    @Column(name = "player1_game_status")
    private String player1GameStatus;

    @Column(name = "player2_game_status")
    private String player2GameStatus;

    @Column(name = "final_winner")
    private String finalWinner;

    @Column(name = "final_loser")
    private String finalLoser;

    @Column(name = "url")
    private String url;

    GamePlay(){
        this.player1Status = "";
        this.player2Status = "";
        this.finalWinner = "";
        this.finalLoser = "";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getGameId() {
        return gameId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPlayerOneUsername() {
        return playerOneUsername;
    }

    public void setPlayerOneUsername(String playerOneUsername) {
        this.playerOneUsername = playerOneUsername;
    }

    public String getPlayerTwoUsername() {
        return playerTwoUsername;
    }

    public void setPlayerTwoUsername(String playerTwoUsername) {
        this.playerTwoUsername = playerTwoUsername;
    }

    public double getBettingAmount() {
        return bettingAmount;
    }

    public void setBettingAmount(double bettingAmount) {
        this.bettingAmount = bettingAmount;
    }

    public double getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(double winningAmount) {
        this.winningAmount = winningAmount;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public double getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(double platformFee) {
        this.platformFee = platformFee;
    }

    public String getPlayer1Status() {
        return player1Status;
    }

    public void setPlayer1Status(String player1Status) {
        this.player1Status = player1Status;
    }

    public String getPlayer2Status() {
        return player2Status;
    }

    public String getPlayer1GameStatus() {
        return player1GameStatus;
    }

    public void setPlayer1GameStatus(String player1GameStatus) {
        this.player1GameStatus = player1GameStatus;
    }

    public String getPlayer2GameStatus() {
        return player2GameStatus;
    }

    public void setPlayer2GameStatus(String player2GameStatus) {
        this.player2GameStatus = player2GameStatus;
    }

    public void setPlayer2Status(String player2Status) {
        this.player2Status = player2Status;
    }

    public String getFinalWinner() {
        return finalWinner;
    }

    public void setFinalWinner(String finalWinner) {
        this.finalWinner = finalWinner;
    }

    public String getFinalLoser() {
        return finalLoser;
    }

    public void setFinalLoser(String finalLoser) {
        this.finalLoser = finalLoser;
    }
}
