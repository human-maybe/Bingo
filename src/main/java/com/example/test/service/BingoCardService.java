package com.example.test.service;

import com.example.test.model.BingoCard;
import com.example.test.model.BingoTile;
import com.example.test.model.Challenge;
import com.example.test.model.Goal;
import com.example.test.model.enums.Diffeculty;
import com.example.test.model.enums.Proirity;
import com.example.test.model.enums.Status;
import com.example.test.repo.BingoCardRepository;
import com.example.test.repo.BingoTileRepository;
import com.example.test.repo.GoalRepository;
import jakarta.annotation.Priority;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor    // this is a constructor injection
public class BingoCardService {
    private final BingoCardRepository bingoCardRepository;
    private final GoalRepository goalRepository;
    private final BingoTileRepository bingoTileRepository;

    public BingoCard formCard() {
        List<Goal> allgoals = goalRepository.findAll();
        List<BingoCard> todaysCards = bingoCardRepository.findAllCreatedOnDate(LocalDate.now());
        if (todaysCards != null) { // if there is a card from today
            LocalDateTime latestCardTime = todaysCards.get(0).getDay();
            LocalDateTime currentTime = LocalDateTime.now();
            Duration timeSinceLastCard = Duration.between(latestCardTime, currentTime);
            if (timeSinceLastCard.toHours() >= 24) {
                BingoCard bingoCard = todaysCards.get(0);
                int bingos = countBingos(bingoCard.getBingoTiles());
                if (bingos == 0) {
                    bingoCard.setStatus(Status.FAILED);
                } else if (bingos == 1) {
                    bingoCard.setStatus(Status.Completed);
                } else if (bingos >= 3) {
                    bingoCard.setStatus(Status.FINISHED);
                }
            }
        }
        List<Goal> yesterDaysGoals = new ArrayList<>();
        todaysCards.get(0).getBingoTiles().forEach(tile -> {
            if (tile.getIsDone()) {
                yesterDaysGoals.add(tile.getChallenge().getGoal());
            }
        });
        int high = 0, medium = 0, low = 0;
        for (Goal goal : yesterDaysGoals) {
            switch (goal.getProirity()) {
                case High:
                    high++;
                    break;
                case Medium:
                    medium++;
                    break;
                case Low:
                    low++;
                    break;
            }
        }
        List<Goal> todayhighGoals = new ArrayList<>();
        if (high < 2) { // must get at least 3 high goals
            allgoals.stream()
                    .filter(goal -> goal.getProirity() == Proirity.High && goal.getIsActive() && (!yesterDaysGoals.contains(goal.getId())))
                    .collect(Collectors.toList());

            if (todayhighGoals.size() < 3) {
                todayhighGoals.addAll(
                        yesterDaysGoals.stream()
                                .filter(goal -> goal.getProirity() == Proirity.High)
                                .toList());
            }
        }

        //create  a new card
        List<Goal> midGoals = allgoals.stream().filter(goal -> goal.getProirity() == Proirity.Medium && goal.getIsActive() == true).toList(); // do you cover all goals? or do you focus on some  more

        List<Goal> lowGoal = allgoals.stream().filter(goal -> goal.getProirity() == Proirity.Low && goal.getIsActive() == true).toList(); // do you cover all goals? or do you focus on some  more

        // balance the card challenges
        List<Challenge> challenges = new ArrayList<>();
        while (todayhighGoals.size() > 25) {
            challenges.addAll(createChallenges(todayhighGoals, midGoals, lowGoal));
        }
        List<BingoTile> tiles = new ArrayList<>();
        challenges.forEach(challenge -> {
            BingoTile tile = new BingoTile();
            tile.setChallenge(challenge);
            tile.setIsDone(false);
            tiles.add(tile);
        });

        BingoCard todaysCard = new BingoCard();
        todaysCard.setDay(LocalDateTime.now());
        todaysCard.setStatus(Status.Current);
         todaysCard.addBingoTiles(tiles);

        return bingoCardRepository.save(todaysCard);
    }

    private List<Challenge> createChallenges(List<Goal> highGoals, List<Goal> midGoals, List<Goal> lowGoal) {
        Random random = new Random();
        Diffeculty[] difficulties = Diffeculty.values();
        List<Challenge> challenges = new ArrayList<>();
        highGoals.forEach(goal -> {
            Challenge challenge = new Challenge();
            challenge.setGoal(goal);
            challenge.setDiffeculty(difficulties[random.nextInt(difficulties.length)]);
            challenge.formChallenge(challenge.getDiffeculty());
            if (challenges.size() < 25)
                challenges.add(challenge);
        });
        midGoals.forEach(goal -> {
            Challenge challenge = new Challenge();
            challenge.setGoal(goal);
            challenge.setDescription(goal.getDescription());
            challenge.setDiffeculty(difficulties[random.nextInt(difficulties.length)]);
            challenge.formChallenge(challenge.getDiffeculty());
            if (challenges.size() < 25)
                challenges.add(challenge);

        });

        lowGoal.forEach(goal -> {
            Challenge challenge = new Challenge();
            challenge.setGoal(goal);
            challenge.setDescription(goal.getDescription());
            challenge.setDiffeculty(difficulties[random.nextInt(difficulties.length)]);
            challenge.formChallenge(challenge.getDiffeculty());
            if (challenges.size() < 25)
                challenges.add(challenge);
        });
        return challenges;
    }

    public int countBingos(List<BingoTile> tiles) {
        // Filter only completed tiles
        List<Integer> completedPositions = tiles.stream()
                .filter(tile -> Boolean.TRUE.equals(tile.getIsDone()))
                .map(BingoTile::getPosition)
                .collect(Collectors.toList());

        int bingoCount = 0;

        // Check horizontal rows
        for (int row = 0; row < 5; row++) {
            int rowStart = row * 5 + 1;
            if (completedPositions.contains(rowStart) &&
                    completedPositions.contains(rowStart + 1) &&
                    completedPositions.contains(rowStart + 2) &&
                    completedPositions.contains(rowStart + 3) &&
                    completedPositions.contains(rowStart + 4)) {
                bingoCount++;
            }
        }

        // Check vertical columns
        for (int col = 0; col < 5; col++) {
            int colStart = col + 1;
            if (completedPositions.contains(colStart) &&
                    completedPositions.contains(colStart + 5) &&
                    completedPositions.contains(colStart + 10) &&
                    completedPositions.contains(colStart + 15) &&
                    completedPositions.contains(colStart + 20)) {
                bingoCount++;
            }
        }

        // Check diagonal (top-left to bottom-right)
        if (completedPositions.contains(1) &&
                completedPositions.contains(7) &&
                completedPositions.contains(13) &&
                completedPositions.contains(19) &&
                completedPositions.contains(25)) {
            bingoCount++;
        }

        // Check diagonal (top-right to bottom-left)
        if (completedPositions.contains(5) &&
                completedPositions.contains(9) &&
                completedPositions.contains(13) &&
                completedPositions.contains(17) &&
                completedPositions.contains(21)) {
            bingoCount++;
        }

        return bingoCount;
    }
}
