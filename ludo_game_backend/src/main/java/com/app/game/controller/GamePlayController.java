package com.app.game.controller;

import com.app.game.Dao.GameDebitCreditDao;
import com.app.game.Dao.GamePlayDao;
import com.app.game.Dao.ReferralInfoDao;
import com.app.game.Dao.UserDao;
import com.app.game.dto.ResponseMessage;
import com.app.game.entity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;

import java.util.*;

@RestController
@RequestMapping(value ="/game", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class GamePlayController {

    @Value("${image.dir}") // Define the upload directory in your application.properties
    private String imageDirectory;

    @Autowired
    GamePlayDao gamePlayDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ReferralInfoDao referralInfoDao;

    @Autowired
    GameDebitCreditDao gameDebitCreditDao;

    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");

    @PostMapping("/start")
    public ResponseEntity<?> startGame(@RequestBody GamePlay gamePlay){
        if(gamePlayDao.findByPlayerOneUsernameAndPlayer1GameStatus(gamePlay.getPlayerOneUsername(),"Ongoing") != null || gamePlayDao.findByPlayerOneUsernameAndPlayer1GameStatus(gamePlay.getPlayerOneUsername(),"Waiting") != null){
            return new ResponseEntity<Error>(new Error("You Can Start Or Play Only One Game, Please Cancel The Previous Game To Start A New One"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(gamePlayDao.findByPlayerTwoUsernameAndPlayer2GameStatus(gamePlay.getPlayerOneUsername(),"Ongoing") != null || gamePlayDao.findByPlayerTwoUsernameAndPlayer2GameStatus(gamePlay.getPlayerOneUsername(),"Waiting") != null){
            return new ResponseEntity<Error>(new Error("You Can Start Or Play Only One Game, Please Cancel The Previous Game To Start A New One"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(gamePlay.getBettingAmount() < 50){
            return new ResponseEntity<Error>(new Error("Betting Amount Should Be 50 Or More"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User u = userDao.findByUserName(gamePlay.getPlayerOneUsername());
        if(u == null){
            return new ResponseEntity<Error>(new Error("Please Set Username"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(u.getBalance() - gamePlay.getBettingAmount() < 0){
            return new ResponseEntity<Error>(new Error("Insufficient Balance"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        u.setBalance(u.getBalance() - gamePlay.getBettingAmount());
        userDao.save(u);
        gamePlay.setPlayer1GameStatus("Waiting");
        gamePlay.setGameStatus("Waiting");
        gamePlay.setPlatformFee((gamePlay.getBettingAmount()*2)*0.05);
        gamePlay.setWinningAmount((gamePlay.getBettingAmount()*2)-((gamePlay.getBettingAmount()*2)*0.05));
        gamePlayDao.save(gamePlay);
        return new ResponseEntity<GamePlay>(gamePlay,HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinGame(@RequestBody GamePlay gamePlay){
        if(gamePlayDao.findByPlayerOneUsernameAndPlayer1GameStatus(gamePlay.getPlayerTwoUsername(),"Ongoing") != null || gamePlayDao.findByPlayerOneUsernameAndPlayer1GameStatus(gamePlay.getPlayerTwoUsername(),"Waiting") != null){
            return new ResponseEntity<Error>(new Error("You Can Join Only One Game, Please Exit From The Previous Game To Join A New One"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(gamePlayDao.findByPlayerTwoUsernameAndPlayer2GameStatus(gamePlay.getPlayerTwoUsername(),"Ongoing") != null || gamePlayDao.findByPlayerTwoUsernameAndPlayer2GameStatus(gamePlay.getPlayerTwoUsername(),"Waiting") != null){
            return new ResponseEntity<Error>(new Error("You Can Join Only One Game, Please Exit From The Previous Game To Join A New One"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User u = userDao.findByUserName(gamePlay.getPlayerTwoUsername());
        if(u == null){
            return new ResponseEntity<Error>(new Error("Please Set Username"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(u.getBalance() - gamePlay.getBettingAmount() < 0){
            return new ResponseEntity<Error>(new Error("Insufficient Balance"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        GamePlay g = gamePlayDao.findByGameIdAndPlayerOneUsernameAndGameStatus(gamePlay.getGameId(),gamePlay.getPlayerOneUsername(), gamePlay.getGameStatus());
        g.setPlayerTwoUsername(gamePlay.getPlayerTwoUsername());
        g.setPlayer1GameStatus("Ongoing");
        g.setGameStatus("Ongoing");
        g.setPlayer2GameStatus("Ongoing");
        gamePlayDao.save(g);
        u.setBalance(u.getBalance() - g.getBettingAmount());
        userDao.save(u);
        return new ResponseEntity<GamePlay>(g,HttpStatus.OK);
    }

    @PostMapping("/cancel-my-game")
    public ResponseEntity<?> cancelMyGame(@RequestBody GamePlay gamePlay){
        GamePlay g = gamePlayDao.findByGameIdAndPlayerOneUsernameAndPlayer1GameStatus(gamePlay.getGameId(),gamePlay.getPlayerOneUsername(), gamePlay.getGameStatus());
        if(g == null){
            return new ResponseEntity<Error>(new Error("Game Cannot Be Cancelled"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            User u = userDao.findByUserName(g.getPlayerOneUsername());
            u.setBalance(u.getBalance() + g.getBettingAmount());
            userDao.save(u);
            g.setPlayer1GameStatus("Cancelled");
            g.setGameStatus("Cancelled");
            gamePlayDao.save(g);
            return new ResponseEntity<ResponseMessage>(new ResponseMessage("Successfully Cancelled"), HttpStatus.OK);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelGame(@RequestBody GamePlay gamePlay){
        GamePlay g = gamePlayDao.findByGameIdAndPlayerOneUsernameAndPlayer1GameStatus(gamePlay.getGameId(),gamePlay.getPlayerOneUsername(), gamePlay.getPlayer1GameStatus());
        if(g != null){
            User u = userDao.findByUserName(gamePlay.getPlayerOneUsername());
            u.setBalance(u.getBalance() + g.getBettingAmount());
            userDao.save(u);
            g.setPlayer1GameStatus("Cancelled");
            g.setGameStatus("Pending");
            gamePlayDao.save(g);
        }

        g = gamePlayDao.findByGameIdAndPlayerTwoUsernameAndPlayer2GameStatus(gamePlay.getGameId(),gamePlay.getPlayerOneUsername(), gamePlay.getPlayer2GameStatus());
        if(g != null){
            User u = userDao.findByUserName(gamePlay.getPlayerOneUsername());
            u.setBalance(u.getBalance() + g.getBettingAmount());
            userDao.save(u);
            g.setPlayer2GameStatus("Cancelled");
            g.setGameStatus("Pending");
            gamePlayDao.save(g);
        }

        if(g != null) return new ResponseEntity<ResponseMessage>(new ResponseMessage("Successfully Cancelled"), HttpStatus.OK);

        return new ResponseEntity<Error>(new Error("Game Cannot Be Cancelled"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/final-cancel")
    public ResponseEntity<?> finalCancelGame(@RequestBody GamePlay gamePlay){
        GamePlay g = gamePlayDao.findByGameIdAndPlayerOneUsernameAndPlayer1GameStatus(gamePlay.getGameId(),gamePlay.getPlayerOneUsername(), gamePlay.getPlayer1GameStatus());
        if(g != null){
            User u = userDao.findByUserName(g.getPlayerOneUsername());
            u.setBalance(u.getBalance() + g.getBettingAmount());
            userDao.save(u);
            u = userDao.findByUserName(g.getPlayerTwoUsername());
            u.setBalance(u.getBalance() + g.getBettingAmount());
            userDao.save(u);
            g.setGameStatus("Cancelled");
            g.setPlayer1GameStatus("Cancelled");
            g.setPlayer2GameStatus("Cancelled");
            g.setPlayer1Status("Cancel");
            g.setPlayer2Status("Cancel");
            gamePlayDao.save(g);
            return new ResponseEntity<ResponseMessage>(new ResponseMessage("Successfully Cancelled"), HttpStatus.OK);
        }

        return new ResponseEntity<Error>(new Error("Game Cannot Be Cancelled"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/ongoing-games")
    public List<GamePlay> getOnGoingGames(){
        return gamePlayDao.findByGameStatus("Ongoing");
    }

    @GetMapping("/pending-games")
    public List<GamePlay> getCompletedGames(){
        List<GamePlay> g = gamePlayDao.findByGameStatus("Pending");
        if(!g.isEmpty() || g.size() > 0) return g;
        g = gamePlayDao.findByPlayer1GameStatus("Pending");
        if(!g.isEmpty() || g.size() > 0) return g;
        g = gamePlayDao.findByPlayer2GameStatus("Pending");
        return g;
    }

    @PostMapping("/waiting-games")
    public List<GamePlay> getWaitingGames(@RequestBody Map<String,Object> data){
        List<GamePlay> gList = gamePlayDao.findByGameStatus("Waiting");
        List rList = new ArrayList<GamePlay>();
        for(GamePlay g : gList){
            if(g.getPlayerOneUsername().equals(data.get("playerOneUsername").toString())){
                continue;
            }
            rList.add(g);
        }
        return rList;
    }

    @PostMapping("/my-battle")
    public GamePlay getMyBattle(@RequestBody Map<String,Object> data){
        GamePlay gamePlay = gamePlayDao.findByPlayerOneUsernameAndPlayer1GameStatus(data.get("playerOneUsername").toString(),"Waiting");
        if(gamePlay != null) return gamePlay;
        gamePlay =  gamePlayDao.findByPlayerOneUsernameAndPlayer1GameStatus(data.get("playerOneUsername").toString(),"Ongoing");
        if(gamePlay != null) return gamePlay;
        gamePlay = gamePlayDao.findByPlayerTwoUsernameAndPlayer2GameStatus(data.get("playerOneUsername").toString(),"Waiting");
        if(gamePlay != null) return gamePlay;
        return gamePlayDao.findByPlayerTwoUsernameAndPlayer2GameStatus(data.get("playerOneUsername").toString(),"Ongoing");
    }

    @PostMapping("/set-room-id")
    public ResponseEntity<?> setRoomId(@RequestBody GamePlay gamePlay){
        GamePlay g = gamePlayDao.findByPlayerOneUsernameAndGameStatus(gamePlay.getPlayerOneUsername(),"Ongoing");
        if(g == null) return new ResponseEntity<Error>(new Error("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        g.setRoomId(gamePlay.getRoomId());
        gamePlayDao.save(g);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Successful"), HttpStatus.OK);
    }

    @PostMapping("/played")
    public List<GamePlay> getGamePlayed(@RequestBody Map<String,Object> data){
        Comparator<GamePlay> idComparator = Comparator.comparingInt(GamePlay::getGameId).reversed();
        List<GamePlay> finalList = new ArrayList<GamePlay>();
        List<GamePlay> gamePlay = gamePlayDao.findByPlayerOneUsernameAndPlayer1Status(data.get("playerOneUsername").toString(),"Winner");
        if(gamePlay.size() != 0) finalList.addAll(gamePlay);
        gamePlay =  gamePlayDao.findByPlayerOneUsernameAndPlayer1Status(data.get("playerOneUsername").toString(),"Lose");
        if(gamePlay.size() != 0) finalList.addAll(gamePlay);
        gamePlay =  gamePlayDao.findByPlayerOneUsernameAndPlayer1Status(data.get("playerOneUsername").toString(),"Cancel");
        if(gamePlay.size() != 0) finalList.addAll(gamePlay);
        gamePlay = gamePlayDao.findByPlayerTwoUsernameAndPlayer2Status(data.get("playerOneUsername").toString(),"Winner");
        if(gamePlay.size() != 0) finalList.addAll(gamePlay);
        gamePlay =  gamePlayDao.findByPlayerTwoUsernameAndPlayer2Status(data.get("playerOneUsername").toString(),"Lose");
        if(gamePlay.size() != 0) finalList.addAll(gamePlay);
        gamePlay =  gamePlayDao.findByPlayerTwoUsernameAndPlayer2Status(data.get("playerOneUsername").toString(),"Cancel");
        if(gamePlay.size() != 0) finalList.addAll(gamePlay);
        Collections.sort(finalList,idComparator);
        return finalList;
    }

    @PostMapping("/update-status")
    public void updateGameStatus(@RequestBody Map<String,Object> data){
        GamePlay gamePlay = gamePlayDao.findByPlayerOneUsernameAndGameStatus(data.get("playerOneUsername").toString(),"Ongoing");
        if(gamePlay != null){
            gamePlay.setPlayer1Status(data.get("player1Status").toString());
            gamePlay.setPlayer1GameStatus("Pending");
            gamePlay.setGameStatus("Pending");
        }
        else{
            gamePlay = gamePlayDao.findByPlayerTwoUsernameAndGameStatus(data.get("playerOneUsername").toString(),"Ongoing");
            if(gamePlay != null) {
                gamePlay.setPlayer2Status(data.get("player1Status").toString());
                gamePlay.setPlayer2GameStatus("Pending");
                gamePlay.setGameStatus("Pending");
            }
        }
        if(gamePlay != null){
            gamePlayDao.save(gamePlay);
        }
    }

    @PostMapping("/update-final-status")
    public ResponseEntity<?> updateFinalStatus(@RequestBody Map<String,Object> data){
        GamePlay gamePlay = gamePlayDao.findByGameId(Integer.parseInt(data.get("gameId").toString()));
        if(gamePlay == null){
            return new ResponseEntity<Error>(new Error("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(data.get("winner").toString().equals("")){
            return new ResponseEntity<Error>(new Error("Enter Winner!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(data.get("winner").toString().equals(gamePlay.getPlayerOneUsername())){
            gamePlay.setFinalWinner(gamePlay.getPlayerOneUsername());
            gamePlay.setFinalLoser(gamePlay.getPlayerTwoUsername());
        }else{
            gamePlay.setFinalWinner(gamePlay.getPlayerTwoUsername());
            gamePlay.setFinalLoser(gamePlay.getPlayerOneUsername());
        }

        if(gamePlay.getFinalWinner().equals("") || gamePlay.getFinalLoser().equals("")){
            return new ResponseEntity<Error>(new Error("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        gamePlay.setPlayer1GameStatus("Completed");
        gamePlay.setPlayer2GameStatus("Completed");
        gamePlay.setGameStatus("Completed");
        gamePlayDao.save(gamePlay);
        User u1 = userDao.findByUserName(gamePlay.getFinalWinner());
        ReferralInfo rf = referralInfoDao.findByRefereePhoneNumber(u1.getPhoneNumber());
        double commission = (gamePlay.getWinningAmount()*rf.getReferrerCommission())/100;
        u1.setBalance(u1.getBalance()+(gamePlay.getWinningAmount()-commission));
        userDao.save(u1);
        User u2 = userDao.findByPhoneNumber(rf.getReferrerPhoneNumber());
        if(u2 != null) {
            double winAmount = u2.getBalance() + commission;
            u2.setBalance(winAmount);
            userDao.save(u2);
        }
        User u3 = userDao.findByUserName(gamePlay.getFinalLoser());
        u3.setBalance(u3.getBalance()-gamePlay.getBettingAmount());
        userDao.save(u3);

        GameDebitCredit gdc1 = new GameDebitCredit();
        gdc1.setRoomId(gamePlay.getRoomId());
        gdc1.setMe(gamePlay.getFinalWinner());
        gdc1.setOpponent(gamePlay.getFinalLoser());
        gdc1.setAmount(gamePlay.getWinningAmount()-commission);
        gdc1.setGameStatus("Winner");
        gdc1.setStatus("Credit");

        gameDebitCreditDao.save(gdc1);

        GameDebitCredit gdc2 = new GameDebitCredit();
        gdc2.setRoomId(gamePlay.getRoomId());
        gdc2.setMe(gamePlay.getFinalLoser());
        gdc2.setOpponent(gamePlay.getFinalWinner());
        gdc2.setAmount(gamePlay.getBettingAmount());
        gdc2.setGameStatus("Lose");
        gdc2.setStatus("Debit");

        gameDebitCreditDao.save(gdc2);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Successfully Updated!"), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get( imageDirectory + file.getOriginalFilename());
            Files.write(path, bytes);
            return "Image uploaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload image";
        }
    }

    @GetMapping("/image")
    public ResponseEntity<Resource> getImage(@RequestParam String name) {
        try {
            for (String extension : IMAGE_EXTENSIONS) {
                String imageNameWithExtension = name + extension;
                Path imagePath = Paths.get(imageDirectory, imageNameWithExtension);
                Resource resource = new UrlResource(imagePath.toUri());
                if (resource.exists()) {
                    String contentType = Files.probeContentType(imagePath);
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(contentType))
                            .body(resource);
                }
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
