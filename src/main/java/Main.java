import com.me.tmw.debug.devtools.DevScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    private double mouseX;
    private double mouseY;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane view = new Pane();

        primaryStage.setScene(new DevScene(view));
        primaryStage.show();

//        view.setOnMouseMoved(event -> {
//            mouseX = event.getSceneX();
//            mouseY = event.getSceneY();
//            AnimatedShape shape = new AnimatedShape(animatedShape -> view.getChildren().remove(animatedShape), primaryStage);
//            shape.setLayoutX(mouseX);
//            shape.setLayoutY(mouseY);
//            view.getChildren().add(shape);
//            shape.play();
//        });

        animateThings(primaryStage, view, 0);

        Thread atMouse = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(25);
                    if (view.isHover()) {
                        Platform.runLater(() -> {
                            AnimatedShape shape = new AnimatedShape(animatedShape -> view.getChildren().remove(animatedShape), primaryStage);
                            shape.setLayoutX(mouseX);
                            shape.setLayoutY(mouseY);
                            view.getChildren().add(shape);
                            shape.play();
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        atMouse.setDaemon(true);
//        atMouse.start();
    }

    private void animateThings(Stage primaryStage, Pane view, double z) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int quickCounter = 0;
            double quickTime = 0;
            while (true) {
                try {
                    if (Math.random() >= 0.99 && quickCounter == 0) {
                        quickCounter = (int) ((Math.random() * 50) + 5);
                        quickTime = (Math.random() * 50) + 10;
                    }
                    if (quickCounter > 0) {
                        Thread.sleep((long) (quickTime * AnimatedShape.LENGTH));
                        quickCounter--;
                    } else {
                        Thread.sleep((long) (Math.random() * 75 * AnimatedShape.LENGTH));
                    }
                    Platform.runLater(() -> {
                        AnimatedShape shape = new AnimatedShape(shape1 -> view.getChildren().remove(shape1), primaryStage);
                        shape.setLayoutX(view.getWidth() * Math.random());
                        view.getChildren().add(shape);
                        shape.play();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

}
