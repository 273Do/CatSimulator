import java.util.*;

// すべての生物が備える機能
interface Life {
    void eat();

    void sleep();

    void play(Creature p);

    // void run();

    // void damaged(int damage);

    // void attack(Creature m, int damage);
}

// 生物の基底クラス
abstract class Creature implements Life {
    private String label;
    private int hitPoint;
    private int point;

    public Creature(String label, int hitPoint) {
        this.setName(label);
        this.setHp(hitPoint);
    }

    public void stress(int damage) {
        System.out.println(this.getName() + "に" + damage + "のストレス");
        this.setHp(this.getHp() - damage);
        if (this.getHp() == 0) {
            this.die();
        }
    }

    public void happy (int point) {
        System.out.println(this.getName() + "に" + point + "の幸福");
        this.setHappy(this.getHappy() + point);
    }

    public void die() {
        this.setHp(0);
        System.out.println(this.getName() + "は亡くなってしまった...なむさん");
    }

    public void setHp(int hitPoint) {
        if(hitPoint >= 100){
            this.hitPoint = 100;
        }
        else if (hitPoint > 0) {
            this.hitPoint = hitPoint;

        } else if (hitPoint <= 0) {
            this.hitPoint = 0;
        }
    }

    public int getHp() {
        return this.hitPoint;
    }

    public void setHappy(int point){
        if(point >= 100){
            this.point = 100;
        }
        else if (point > 0) {
            this.point = point;

        } else if (point <= 0) {
            this.point = 0;
        }
    }

    public int getHappy() {
        return this.point;
    }

    public void setName(String label) {
        this.label = label;
    }

    public String getName() {
        return this.label;
    }
}

// 登場人物の抽象クラス
abstract class Character extends Creature {
    public Character(String label, int hitPoint) {
        super(label, hitPoint);
    }

    public void eat() {
        System.out.println(this.getName() + "はご飯を食べた");
        setHp(this.getHp() + 40);
        setHappy(this.getHappy() + 30);
    }

    public void sleep() {
        System.out.println(this.getName() + "は熟睡した");
        setHp(100);
        setHappy(this.getHappy() + 30);
    }
}

// 猫ちゃんクラス
class Cat extends Character {
    public Cat(String label, int hitPoint) {
        super(label, hitPoint);
    }

    public void play(Creature p){
        System.out.println(this.getName()+ "は沢山遊んだ");
        p.happy(getHappy() + 20);

    }
}

// プレイヤークラス
class Player extends Character {
    public Player(String label, int hitPoint) {
        super(label, hitPoint);
    }

    public void play(Creature c){
        System.out.println(this.getName()+ "は沢山遊んだ");
        c.happy(getHappy() + 40);
    }
}

// 現実クラス
public class Main {
    public static void main(String[] args) throws Exception {

        // 入力値の取得
        Scanner sc = new Scanner(System.in);
        String[] labels = new String[4];
        String[] name_description = {"プレイヤー名", "猫ちゃんの名前"};
        for (int i = 0; i < 2; i++) {
            System.out.println(name_description[i]+"を入力してください．");
            String items = sc.nextLine();
            labels[i] = items;
        }
        System.out.println("--------------------");
        System.out.println("行動コマンド入力方法");
        System.out.println("猫ちゃん，プレイヤーの順に食事，遊び，睡眠の有無を入力してください．");
        System.out.println("例)110010->猫ちゃんは食事，遊び，プレイヤーは遊びを行う");
        System.out.println("--------------------");
        // sc.close();


        // 具体的な処理
        int day = 1;
        Creature[] lives = new Creature[2];
        lives[0] = new Player(labels[0], 100);
        lives[1] = new Cat(labels[1], 100);

       //コマンド入力に応じて処理を繰り返す
       // - 猫ちゃんとプレイヤーの1日ごとの行動をeat，play，sleepの順でコマンド入力で行う
       //例1)
       //ターミナル出力cat/player
       //ターミナル入力111111->猫ちゃんとプレイヤーに対してeat,play,sleepが実行される
       //例2)
       //ターミナルターミナル出力cat/player
       //入力101110->猫ちゃんはeat，sleep，プレイヤーはeat，play，が実行される
       //実行されてい項目に対してはstressが実行される
       //ターミナルの流れとしては以下の通り
       //--------------------
       //day1
       //cat/player
       //111110
       //いい調子です
       //day2
       //猫ちゃんかプレイヤーのhpが0になるまで以下同様
       //--------------------

        while (lives[0].getHp() > 0 || lives[1].getHp() > 0) {
            System.out.println("day" + day + "の動作を入力");
            for (Creature creature : lives) {
                String command = sc.nextLine();

                // Execute commands based on user input
                for (char action : command.toCharArray()) {
                    switch (action) {
                        case '1':
                            creature.eat();
                            break;
                        case '2':
                            creature.play(lives[(lives[0] == creature) ? 1 : 0]);
                            break;
                        case '3':
                            creature.sleep();
                            break;
                        default:
                            break;
                    }
                }

                // 猫ちゃんかプレイヤーのhpが0になったら終了
                for (Creature life : lives) {
                if (life.getHp() <= 0) {
                    life.die();
                    System.out.println("終了します");
                    return;
                }
                // System.out.println(life.getName() + "のいい調子です");
            }

            day++;
        }

        sc.close();
    }
}}