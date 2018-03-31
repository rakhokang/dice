package rakang.com.dice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private View cubeOne, cubeTwo, cubeThree;
    private View cubeAdd, cubeRemove;
    private View backView;
    private View diceOne, diceTwo, diceThree;
    private Effect animationEffects;
    private AnimatorSet animatorSetOne, animatorSetTwo, animatorSetTree;
    private Drawable[] diceDrawable = new Drawable[6];

    private boolean flag = true;
    private int num_of_cube = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        setEffect();
        setDiceDrawable();
        setCubeView(num_of_cube);

        cube_number_control();
        gambling();

        clearDiceNum();
    }

    private void setViews() {
        cubeOne = (View) this.findViewById(R.id.cubicOne);
        cubeTwo = (View) this.findViewById(R.id.cubicTwo);
        cubeThree = (View) this.findViewById(R.id.cubicThree);

        cubeAdd = (View) this.findViewById(R.id.cubePlus);
        cubeRemove = (View) this.findViewById(R.id.cubeMinus);
        backView = (View) this.findViewById(R.id.backLayer);

        diceOne = (View) this.findViewById(R.id.diceOne);
        diceTwo = (View) this.findViewById(R.id.diceTwo);
        diceThree = (View) this.findViewById(R.id.diceThree);
    }

    private void setEffect() {
        animationEffects = new Effect(this);
        animatorSetOne = animationEffects.CubicAnim(cubeOne, 3000);
        animatorSetTwo = animationEffects.CubicAnim(cubeTwo, 3000);
        animatorSetTwo.setStartDelay(200);
        animatorSetTree = animationEffects.CubicAnim(cubeThree, 3000);
        animatorSetTree.setStartDelay(400);
    }

    private void clearDiceNum() {
        diceOne.setVisibility(View.INVISIBLE);
        diceTwo.setVisibility(View.INVISIBLE);
        diceThree.setVisibility(View.INVISIBLE);
    }

    private void setDiceDrawable() {
        diceDrawable[0] = getDrawable(R.drawable.dice_one);
        diceDrawable[1] = getDrawable(R.drawable.dice_two);
        diceDrawable[2] = getDrawable(R.drawable.dice_three);
        diceDrawable[3] = getDrawable(R.drawable.dice_four);
        diceDrawable[4] = getDrawable(R.drawable.dice_five);
        diceDrawable[5] = getDrawable(R.drawable.dice_six);
    }


    private void cube_number_control() {
        cubeAdd.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    clearDiceNum();
                    if (num_of_cube < 3) {
                        num_of_cube++;
                    } else {
                        num_of_cube = 3;
                    }
                    setCubeView(num_of_cube);
                }
            }
        });
        cubeRemove.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    clearDiceNum();
                    if (num_of_cube > 1) {
                        num_of_cube--;
                    } else {
                        num_of_cube = 1;
                    }
                    setCubeView(num_of_cube);
                }
            }
        });
    }

    private void setCubeView(int getCubeNum) {
        switch (getCubeNum) {
            case 1:
                cubeOne.setVisibility(View.VISIBLE);
                cubeTwo.setVisibility(View.INVISIBLE);
                cubeThree.setVisibility(View.INVISIBLE);
                break;
            case 2:
                cubeOne.setVisibility(View.VISIBLE);
                cubeTwo.setVisibility(View.VISIBLE);
                cubeThree.setVisibility(View.INVISIBLE);
                break;
            case 3:
                cubeOne.setVisibility(View.VISIBLE);
                cubeTwo.setVisibility(View.VISIBLE);
                cubeThree.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void setCubeAnim(int getCubeNum) {
        clearAnimListener();
        switch (getCubeNum) {
            case 1:
                animatorSetOne.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        setDiceNum(1);
                        flag = true;
                    }
                });
                animatorSetOne.start();
                break;
            case 2:
                animatorSetTwo.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        setDiceNum(2);
                        flag = true;
                    }
                });
                animatorSetOne.start();
                animatorSetTwo.start();
                break;
            case 3:
                animatorSetTree.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        setDiceNum(3);
                        flag = true;
                    }
                });
                animatorSetOne.start();
                animatorSetTwo.start();
                animatorSetTree.start();
                break;
            default:
                break;
        }
    }

    private void setDiceNum(int getCubeNum) {
        clearDiceNum();
        switch (getCubeNum) {
            case 1:
                diceResult(diceOne);
                break;
            case 2:
                diceResult(diceOne);
                diceResult(diceTwo);
                break;
            case 3:
                diceResult(diceOne);
                diceResult(diceTwo);
                diceResult(diceThree);
                break;
            default:
                break;
        }
    }

    private void diceResult(View targetView) {
        int get_num = getDiceNumber();
        targetView.setAlpha(0f);
        targetView.setVisibility(View.VISIBLE);
        targetView.setBackground(diceDrawable[get_num]);
        animationEffects.FadeIn(targetView);
    }

    private void clearAnimListener() {
        animatorSetOne.removeAllListeners();
        animatorSetTwo.removeAllListeners();
        animatorSetTree.removeAllListeners();
    }

    private void gambling() {
        backView.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    flag = false;
                    clearDiceNum();
                    setCubeAnim(num_of_cube);
                }
            }
        });
    }

    private int getDiceNumber() {
        int diceNumber = -1;
        diceNumber = (int) ((Math.random() * 36));
        diceNumber = (diceNumber % 6);
        return diceNumber;
    }
}
