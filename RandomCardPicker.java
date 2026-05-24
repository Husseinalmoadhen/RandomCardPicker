import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.Random;
import java.io.File;

public class RandomCardPicker extends Application
{
   private Random rand = new Random();

   private ImageView card;
   private Label cardOutputLabel;

   // Using absolute path because relative paths depend on run location
   // and were not loading images correctly in this environment.
   private final String CARD_FOLDER =
      "C:/Users/almoa/OneDrive/Desktop/vscodes/Spring26 Java/RandomCardPicker/src/PNG-cards-1.3/";

   private String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10",
                             "jack", "queen", "king", "ace"};

   private String[] suits = {"clubs", "diamonds", "hearts", "spades"};

   public static void main(String[] args)
   {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage)
   {
      Image startImage = loadCardImage("2_of_clubs.png");

      card = new ImageView(startImage);
      card.setFitWidth(250);
      card.setFitHeight(350);
      card.setPreserveRatio(true);

      Label cardDescriptor = new Label("Selected Card:");
      cardOutputLabel = new Label("Click the button");
      VBox outputVBox = new VBox(10, cardDescriptor, cardOutputLabel);
      outputVBox.setAlignment(Pos.CENTER);

      Label title = new Label("Pick a Random Card");

      Button pickButton = new Button("Pick a Card");

      pickButton.setOnAction(event ->
      {
         spinCard();
      });

      VBox mainVBox = new VBox(15, title, card, pickButton, outputVBox);
      mainVBox.setAlignment(Pos.CENTER);
      mainVBox.setPadding(new Insets(10));

      Scene scene = new Scene(mainVBox, 400, 500);

      primaryStage.setTitle("Get a random card");
      primaryStage.setScene(scene);
      primaryStage.show();
   }

   private void spinCard()
   {
      Timeline timeline = new Timeline();

      for (int i = 0; i < 4; i++)
      {
         timeline.getKeyFrames().add(
            new KeyFrame(Duration.millis(200 * (i + 1)), event ->
            {
               String fileName = randomCardFile();
               card.setImage(loadCardImage(fileName));
               cardOutputLabel.setText("Picking...");
            })
         );
      }

      timeline.setOnFinished(event ->
      {
         String finalCard = randomCardFile();
         card.setImage(loadCardImage(finalCard));
         cardOutputLabel.setText(cardName(finalCard));
      });

      timeline.play();
   }

   private String randomCardFile()
   {
      String rank = ranks[rand.nextInt(ranks.length)];
      String suit = suits[rand.nextInt(suits.length)];

      return rank + "_of_" + suit + ".png";
   }

   private String cardName(String fileName)
   {
      String name = fileName.replace("_", " ");
      name = name.replace(".png", "");

      return name;
   }

   private Image loadCardImage(String fileName)
   {
      File file = new File(CARD_FOLDER + fileName);
      return new Image(file.toURI().toString());
   }
}