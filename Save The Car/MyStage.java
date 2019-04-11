import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.application.Platform;
import javafx.stage.WindowEvent;

public class MyStage extends Application{

		public final static double WIDTH=400;
		public final static double HEIGHT=600;	
		private static Scene scene;
		private static Stage stage;
		private static Lebel1Scene lebel1SceneObject;
		private static String name;
		private static Score scoreObject;
		
		//------------------------start method--------------------------
	 public void start(Stage myStage){
		stage=myStage;
		//score object creating for sending score
		scoreObject=new Score();
		
		ImageView backgroundImageView;
	    Image backgroundImage= new Image("intro.jpg");
		backgroundImageView= new ImageView(backgroundImage);
		backgroundImageView.setFitHeight(HEIGHT);
		backgroundImageView.setFitWidth(WIDTH);
		backgroundImageView.setPreserveRatio(false);
		backgroundImageView.setSmooth(true);
        backgroundImageView.setCache(true);	
		

		//--------taking input for name----------
		TextField nameField=new TextField();
		nameField.setTranslateX(60d);
		nameField.setTranslateY(460d);
		nameField.setStyle("-fx-font-weight: bold;");
		
		//---------lebel for text massage----------
		Label l1=new Label("ENTER YOUR NAME");
		l1.setTranslateX(60d);
		l1.setTranslateY(440d);
        l1.setFont(new Font("Arial", 30));
		l1.setTextFill(Color.web("#000406"));
	    l1.setStyle("-fx-font-weight: bold;");
		//---------------------------All Button---------------------
		
		
		//-----------play button-------
		Button playButton = new Button("PLAY");
		playButton.setLayoutX(160d);
        playButton.setLayoutY(320d);
		playButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		//--------Play button action------
		playButton.setOnAction(
			e->{
				changeScene(1);
			}
		);
		
		
		//---------high score button---------
	    Button highScoreButton = new Button("HIGH SCORE");
		highScoreButton.setLayoutX(130d);
        highScoreButton.setLayoutY(390);
	    highScoreButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");		
		//--------High button action------
		highScoreButton.setOnAction(
			e->{
			    changeScene(2);
			}
		);
		
		
		//------ok button------
		Button okButton=new Button("OK");
		okButton.setTranslateX(170d);
		okButton.setTranslateY(480d);
	    okButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		
		//---------VBox for name and ok button------
		VBox v1=new VBox();
		v1.getChildren().addAll(l1,nameField,okButton);
		
		//-----------Group for name and ok button---------
		Group nameGroup = new Group(backgroundImageView,v1);
		//-----------group for play high quit button--------
        Group buttonGroup = new Group(playButton,highScoreButton);
		buttonGroup.setVisible(false);
		//--------ok button action------
		okButton.setOnAction(
			e->{
			
				name=nameField.getText();
				v1.setVisible(false);
				buttonGroup.setVisible(true);
			}
		);
   
         Group root=new Group(nameGroup,buttonGroup);  
	     scene = new Scene(root,WIDTH,HEIGHT) ;
	     stage.setScene(scene) ;
	     stage.show() ;
		 
		 stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
			  if(lebel1SceneObject!=null)
				lebel1SceneObject.stopThread();
			  stage.close();
          }
      });        
	}
	
	
	public static void changeScene(int choise){
		if(choise==0)
		{
			stage.setScene(scene) ;	
			stage.show() ;
		}
		else if(choise==1)
		{
			lebel1SceneObject=new Lebel1Scene();
			stage.setScene(lebel1SceneObject.getScene()) ;	
			stage.show() ;			
		}
		else if(choise==2)
		{
			stage.setScene(scoreObject.getScene()) ;	
			stage.show() ;			
		}
	
	}
	
	public static void sendScore(int score)
	{
		scoreObject.checkNewHighScore(name,score);
	}
	
}