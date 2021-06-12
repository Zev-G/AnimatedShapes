import com.me.tmw.nodes.util.Dragging;
import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.function.Consumer;

public class AnimatedShape extends Rectangle {

    public static final double LENGTH = 20;

    private final Consumer<AnimatedShape> onAnimationFinished;
    private final Stage stage;

    public AnimatedShape(Consumer<AnimatedShape> onAnimationFinished, Stage stage) {
        this.onAnimationFinished = onAnimationFinished;
        this.stage = stage;

        double width = Math.random() * 100;
        double height = Math.random() * 100;
        double depth = Math.random() * 100;
        width += 25;
        height += 25;
        depth += 25;

        double radius = Math.random() * 50;
        radius += 10;

        setRotate(Math.random() * 360);
        setFill(randomColor());

        setWidth(width);
        setHeight(height);
//        setDepth(depth);
//        setRadius(radius);

        Dragging.draggable(this, layoutXProperty(), layoutYProperty());

    }

    public void play() {
        ScaleTransition scaleTransition = new ScaleTransition(new Duration(((Math.random() * 750) + 100) * LENGTH), this);
        scaleTransition.setToX(Math.random() * 5);
        scaleTransition.setToY(Math.random() * 5);

        FillTransition colorTransition = new FillTransition(new Duration((Math.random() * 500) + 100), this);
        colorTransition.setToValue(randomColor());

        RotateTransition rotateTransition = new RotateTransition(new Duration(((Math.random() * 1000) + 100) * LENGTH), this);
        rotateTransition.setToAngle((Math.random() * (360 * 2)) - 360);
//        rotateTransition.setAxis(new Point3D((Math.random() * (360 * 2)) - 360, (Math.random() * (360 * 2)) - 360, (Math.random() * (360 * 2)) - 360));

        TranslateTransition translateTransition = new TranslateTransition(new Duration(((Math.random() * 1000) + 750) * LENGTH), this);
        translateTransition.toYProperty().bind(Bindings.add(stage.heightProperty(), heightProperty()).add(150));
//        translateTransition.setToZ((Math.random() * 500) - 250);

        FadeTransition fadeTransition = new FadeTransition(new Duration(translateTransition.getDuration().toMillis() - 200), this);
        fadeTransition.setToValue(Math.random() * 0.5);

        ParallelTransition transition = new ParallelTransition(scaleTransition, colorTransition, rotateTransition, translateTransition, fadeTransition);
        transition.setOnFinished(event -> onAnimationFinished.accept(this));
        transition.play();
    }

    public Color randomColor() {
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        return Color.rgb(red, green, blue, (Math.random() * 0.6) + 0.2);
    }

}
