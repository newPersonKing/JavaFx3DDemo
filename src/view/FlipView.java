package view;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sun.rmi.runtime.Log;

/**
 * Created by emcc on 2018/12/6.
 */
public class FlipView extends Pane{

    //正面视图
    public Node frontNode;
    //反面视图
    public Node backNode;

    //是否翻转
    boolean flipped = false;

    //翻转角度
    DoubleProperty time = new SimpleDoubleProperty(Math.PI / 2);

    //正面翻转特效
    PerspectiveTransform frontEffect = new PerspectiveTransform();
    //反面翻转特效
    PerspectiveTransform backEffect = new PerspectiveTransform();

    Timeline anim = new Timeline();

    public FlipView(Node frontNode,Node backNode){
          this.frontNode = frontNode;
          this.backNode = backNode;
          create();
    }

    /* create方法返回需要显示的内容:*/
    private void create(){
        time.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.print("============"+oldValue);
                setPT(frontEffect, time.get());
                setPT(backEffect, time.get());
            }
        });

        KeyFrame frame1 = new KeyFrame(Duration.ZERO, new KeyValue(time,
                Math.PI / 2, Interpolator.LINEAR));
        KeyFrame frame2 = new KeyFrame(Duration.seconds(1),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                }, new KeyValue(time, -Math.PI / 2, Interpolator.LINEAR));

        anim.getKeyFrames().addAll(frame1,frame2);

        anim.setCycleCount(Timeline.INDEFINITE);//设置周期运行循环往复
        anim.setAutoReverse(true);//

        backNode.visibleProperty().bind(
                Bindings.when(time.lessThan(0)).then(true).otherwise(false));

        frontNode.visibleProperty().bind(
                Bindings.when(time.lessThan(0)).then(false).otherwise(true));
        setPT(frontEffect, time.get());
        setPT(backEffect, time.get());
        frontNode.setEffect(frontEffect);
        backNode.setEffect(backEffect);
        getChildren().addAll(backNode, frontNode);
        anim.play();
    }

    private void setPT(PerspectiveTransform pt, double t) {
        double width = 200;
        double height = 200;
        double radius = width / 2;
        double back = height / 10;
        pt.setUlx(radius - Math.sin(t) * radius);
        pt.setUly(0 - Math.cos(t) * back);
        pt.setUrx(radius + Math.sin(t) * radius);
        pt.setUry(0 + Math.cos(t) * back);
        pt.setLrx(radius + Math.sin(t) * radius);
        pt.setLry(height - Math.cos(t) * back);
        pt.setLlx(radius - Math.sin(t) * radius);
        pt.setLly(height + Math.cos(t) * back);
    }

}
