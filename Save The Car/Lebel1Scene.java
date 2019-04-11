import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.lang.Exception;
import javafx.application.Platform;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

class Lebel1Scene implements Runnable{
	
	
		private final Image backgroundImage,backgroundImage1,backgroundImage2,backgroundImage3,carObstacle1Image,carObstacle2Image,carObstacle3Image,playerCarImage;
		private  ImageView backgroundImageView,carObstacle1View,carObstacle2View,carObstacle3View,playerCarImageView;
		private Group root;
		private Scene scene;
		private Thread carThread;
		private Boolean leftKeyPressed,rightKeyPressed,threadTrue;
		public static int count;
		private Group carGroup;
		private Text scoreText,massage;
		private Button nextLevelButton,backButton,tryAgainButton;
		private int levelScore;
		
		public void run()
		{
			int sleep=100;
			count=0;
			int roadMoveCount=1;
			while(threadTrue)
			{
				if(roadMoveCount==5)
					backgroundImageView.setImage(backgroundImage1);
				else if(roadMoveCount==10)
					backgroundImageView.setImage(backgroundImage2);
				else if(roadMoveCount==15)
				{
					backgroundImageView.setImage(backgroundImage3);
					roadMoveCount=0;
				}
				roadMoveCount++;
				//getting position(x,y) for obstacle 
				double carObstacle1Position= carObstacle1View.getTranslateY();
				carObstacle1View.setTranslateY(carObstacle1Position+15);
				double carObstacle2Position= carObstacle2View.getTranslateY();
				double carObstacle3Position= carObstacle3View.getTranslateY();
				
				//getting position(x,y) for player car 
				double carPosition= playerCarImageView.getTranslateX();
				
				//releasing car obstacle 2
				if(count>3)
				{
					carObstacle2View.setTranslateY(carObstacle2Position+15);
				}
				
				//releasing car obstacle 3
				if(count>5)
				{
					carObstacle3View.setTranslateY(carObstacle3Position+15);
				}
				
				
				if(carObstacle1Position>600)
				{
					carObstacle1View.setTranslateY(-145d);
					if(count%3==0)
						carObstacle1View.setTranslateX(250d);
					else
					{
						carObstacle1View.setTranslateX(45d);
						sleep=sleep-5;					
					}
					levelScore=levelScore+5;
					count++;
					Platform.runLater(() -> {
						scoreText.setText("Score: " + String.valueOf(levelScore));
					});
				}
				
				if(carObstacle2Position>600)
				{
					carObstacle2View.setTranslateY(-290d);
					if(count%2==0)
						carObstacle2View.setTranslateX(260d);
					else
						carObstacle2View.setTranslateX(45d);
					
					levelScore=levelScore+10;
					Platform.runLater(() -> {
						scoreText.setText("Score: " + String.valueOf(levelScore));
					});
				}
				
				if(carObstacle3Position>600)
				{
					carObstacle3View.setTranslateY(carObstacle1Position-400d);
					
					levelScore=levelScore+15;
					Platform.runLater(() -> {
						scoreText.setText("Score: " + String.valueOf(levelScore));
					});
				}

				if(leftKeyPressed==true && carPosition>0d)
				{
					playerCarImageView.setTranslateX(carPosition-15);
				}

				if(rightKeyPressed==true && carPosition<310d)
				{	
					playerCarImageView.setTranslateX(carPosition+15);
				}				
				 
				if(carObstacle1View.getBoundsInParent().intersects(playerCarImageView.getBoundsInParent()) || carObstacle2View.getBoundsInParent().intersects(playerCarImageView.getBoundsInParent())  || carObstacle3View.getBoundsInParent().intersects(playerCarImageView.getBoundsInParent()))
				{	
			        threadTrue=false;
					massage.setVisible(true);
                    break;					
				}
				
				//----------sleep----------
				try{
					Thread.sleep(sleep);
				}catch(Exception e){};
					
			}
			tryAgainButton.setVisible(true);
			backButton.setVisible(true);
			MyStage.sendScore(levelScore);
		};
		
		
		
		
		//----------------------------constructor--------------------------
		Lebel1Scene(){
		//load Image
		// resizes the image to have Higt of 500 and width of 800
        // higher quality filtering method; this ImageView is also cached to
        // improve performance
		count=0;
		levelScore=0;	
	    leftKeyPressed=false;
		rightKeyPressed=false;
		threadTrue=true;
		
		backgroundImage= new Image("road(1).jpg");
		backgroundImageView= new ImageView(backgroundImage);
		backgroundImageView.setFitHeight(MyStage.HEIGHT);
		backgroundImageView.setFitWidth(MyStage.WIDTH);
		backgroundImageView.setPreserveRatio(false);
		backgroundImageView.setSmooth(true);
        backgroundImageView.setCache(true);
		
		backgroundImage1= new Image("road(1).jpg");
		backgroundImage2= new Image("road(2).jpg");
		backgroundImage3= new Image("road(3).jpg");	
		
		//player char
		playerCarImage= new Image("player.png");
		playerCarImageView= new ImageView(playerCarImage);
		playerCarImageView.setFitHeight(140d);
		playerCarImageView.setFitWidth(90d);
		playerCarImageView.setTranslateX(200d);
		playerCarImageView.setTranslateY(440d);
		playerCarImageView.setSmooth(true);
        playerCarImageView.setCache(true);	
		
		//obstacle
		
		carObstacle1Image= new Image("car1.png");
		
		carObstacle1View= new ImageView(carObstacle1Image);
		carObstacle1View.setFitHeight(140d);
		carObstacle1View.setFitWidth(90d);
		carObstacle1View.setTranslateX(60d);
		carObstacle1View.setTranslateY(-250d);
		carObstacle1View.setSmooth(true);
        carObstacle1View.setCache(true);
		
		carObstacle2Image= new Image("car2.png");
		
		carObstacle2View= new ImageView(carObstacle2Image);
		carObstacle2View.setFitHeight(140d);
		carObstacle2View.setFitWidth(90d);
		carObstacle2View.setTranslateX(60d);
		carObstacle2View.setTranslateY(-290d);
		carObstacle2View.setSmooth(true);
        carObstacle2View.setCache(true);
		
		carObstacle3Image= new Image("car3.png");
		
		carObstacle3View= new ImageView(carObstacle3Image);
		carObstacle3View.setFitHeight(160d);
		carObstacle3View.setFitWidth(85d);
		carObstacle3View.setTranslateX(140d);
		carObstacle3View.setTranslateY(-600d);
		carObstacle3View.setSmooth(true);
        carObstacle3View.setCache(true);
		

         
		//----------score Text------------
		scoreText=new Text(250d,50d,"SCORE : "+String.valueOf(levelScore));
		scoreText.setFont(new Font(20));
		scoreText.setFill(Color.WHITE);
		//-----------game over Text--------------
		massage=new Text(110,260,"GAME OVER");
		massage.setFont(new Font(40));
		massage.setFill(Color.RED);
		massage.setVisible(false);
		
		

		
	    //--------------------------------------Try Again button---------------------------
		tryAgainButton = new Button("Try Again");
		tryAgainButton.setLayoutX(150d);
        tryAgainButton.setLayoutY(270d);
		tryAgainButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
	
		tryAgainButton.setOnAction(
			e->{
				MyStage.changeScene(1);
			}
		);
		tryAgainButton.setVisible(false);
		
	    backButton = new Button("Back");
		backButton.setLayoutX(160d);
        backButton.setLayoutY(320d);
		backButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
	
		backButton.setOnAction(
			e->{
				MyStage.changeScene(0);
			}
		);
		backButton.setVisible(false);
		
        //make group for background image
		Group backgroundImageGroup= new Group();
		//group for plate
		carGroup=new Group();
		

		carGroup.getChildren().addAll(carObstacle1View,carObstacle2View,carObstacle3View,playerCarImageView);
		backgroundImageGroup.getChildren().addAll(backgroundImageView,scoreText,massage);
		
		//starting Thread
		carThread = new Thread(this);
        carThread.start();
		//player.startCharacter();
		
		//main group for scene
		root=new Group(backgroundImageGroup,carGroup,tryAgainButton,backButton);	
		scene=new Scene(root,MyStage.WIDTH,MyStage.HEIGHT);	
		
		//taking input from keyboard
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
          if(key.getCode()==KeyCode.LEFT)
		  {
			  leftKeyPressed=true;
		  }
		  else if(key.getCode()==KeyCode.RIGHT)
		  {
			  rightKeyPressed=true;
		  }
       });
		
		  scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
          if(key.getCode()==KeyCode.LEFT)
		  {
			  leftKeyPressed=false;
		  }
		  else if(key.getCode()==KeyCode.RIGHT)
		  {
			  rightKeyPressed=false;
		  }
      });
		
		}
		public Scene getScene(){
			return scene;
		}
		
		public void stopThread()
		{
			threadTrue=false;
		}
	
}