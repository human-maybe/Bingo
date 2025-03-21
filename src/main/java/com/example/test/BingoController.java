package com.example.test;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.HashMap;

public class BingoController {

    @FXML
    private GridPane bingoGrid;

    private final String[] imagePaths = {
            "C:\\Users\\user\\IdeaProjects\\test\\src\\main\\resources\\animation pic1.png",
            "C:\\Users\\user\\IdeaProjects\\test\\src\\main\\resources\\animation pic 2.png",
            "C:\\Users\\user\\IdeaProjects\\test\\src\\main\\resources\\animation pic 3.png",
            "C:\\Users\\user\\IdeaProjects\\test\\src\\main\\resources\\animation pic 4.png",
            "C:\\Users\\user\\IdeaProjects\\test\\src\\main\\resources\\animation pic 5.png"
    };

    private final HashMap<Button, Integer> buttonState = new HashMap<>();

    @FXML
    public void initialize() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Button btn = createBingoButton();
                bingoGrid.add(btn, col, row);
            }
        }
    }

    private Button createBingoButton() {
        Button btn = new Button();
        btn.setPrefSize(80, 80);

        // Set the initial image
        ImageView imageView = new ImageView(new Image("file:" + imagePaths[0]));
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        btn.setGraphic(imageView);

        buttonState.put(btn, 0);

        // Change the image when clicked
        btn.setOnAction(event -> {
            int currentIndex = buttonState.get(btn);
            int nextIndex = (currentIndex + 1) % imagePaths.length;
            buttonState.put(btn, nextIndex);

            ImageView newImageView = new ImageView(new Image("file:" + imagePaths[nextIndex]));
            newImageView.setFitWidth(80);
            newImageView.setFitHeight(80);
            btn.setGraphic(newImageView);
        });

        return btn;
    }
}
