package final_task; //ディレクトリの関係で記述
import java.util.*;

// デバッグモード
public class LogMode{
  public static boolean isLogMode = false;
}

// すべての生物が備える機能
interface Life {
    void eat();

    void sleep();

    void play(Creature p);
}

// 生物の基底クラス
abstract class Creature implements Life {
    private String label;
    private int hitPoint;
    private int point;

    public Creature(String label, int hitPoint, int happyPoint) {
        this.setName(label);
        this.setHp(hitPoint);
        this.setHappy(happyPoint);
    }

    public void stress(int hpDamage, int happyDamage) {
        if(LogMode.isLogMode ==true){
            System.out.println(this.getName() + "に" + hpDamage + "の体力ストレス");
            System.out.println(this.getName() + "に" + happyDamage + "の幸福度ストレス");
        }
        this.setHp(this.getHp() - hpDamage);
        this.setHappy(this.getHappy() - happyDamage);
        if (this.getHp() == 0) {
            this.die();
        }
    }

    public void happy (int point) {
        if(LogMode.isLogMode ==true){
            System.out.println(this.getName() + "に" + point + "の幸福");
        }
        this.setHappy(this.getHappy() + point);
    }

    public void die() {
        this.setHp(0);
        System.out.println(this.getName() + "は亡くなってしまった...なむさん");
        System.exit(0);
    }

    public void setHp(int hitPoint) {
        if (hitPoint > 0 && hitPoint <= 100) {
            this.hitPoint = hitPoint;
        } 
        else if(hitPoint >= 100){
            this.hitPoint = 100;
        } else if (hitPoint <= 0) {
            this.hitPoint = 0;
        }
    }

    public int getHp() {
        return this.hitPoint;
    }

    public void setHappy(int point){
        if (point > 0 && point <= 100) {
            this.point = point;
        }
        else if(point >= 100){
            this.point = 100;
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
    public Character(String label, int hitPoint, int happyPoint) {
        super(label, hitPoint, happyPoint);
    }

    public void eat() {
        if(LogMode.isLogMode ==true){
            System.out.println(this.getName() + "はご飯を食べた");
        }
        setHp(this.getHp() + 45);
        setHappy(this.getHappy() + 30);
    }

    public void sleep() {
        if(LogMode.isLogMode ==true){
            System.out.println(this.getName() + "は熟睡した");
        }
        setHp(this.getHp() + 35);
        setHappy(this.getHappy() + 40);
    }
}

// 猫ちゃんクラス
class Cat extends Character {
    public Cat(String label, int hitPoint, int happyPoint) {
        super(label, hitPoint, happyPoint);
    }

    public void play(Creature p){
        if(LogMode.isLogMode ==true){
            System.out.println(this.getName()+ "は沢山遊んだ");
        }
        p.happy(getHappy() + 20);

    }
}

// プレイヤークラス
class Player extends Character {
    public Player(String label, int hitPoint, int happyPoint) {
        super(label, hitPoint, happyPoint);
    }

    public void play(Creature c){
        if(LogMode.isLogMode ==true){
            System.out.println(this.getName()+ "は沢山遊んだ");
        }
        c.happy(getHappy() + 30);
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
        // デバッグモードの切り替え
        if(labels[0].split(" ").length < 2){
            System.out.println("--------------------");
        }
        else if(labels[0].split(" ")[1].equals("debug")){
            LogMode.isLogMode = true;
            System.out.println("----デバッグモード----");
            labels[0] = "testUser";
        }
        else{
            System.out.println("--------------------");
        }
        // 入力値の説明
        System.out.println("行動コマンド入力方法");
        System.out.println("猫ちゃん，プレイヤーの順に食事，遊び，睡眠の有無を入力してください．");
        System.out.println("例)110010->猫ちゃんは食事，遊び，プレイヤーは遊びを行う．");
        System.out.println("--------------------");
        // sc.close();


        // 具体的な処理
        // 登場人物の生成
        int day = 1;
        Creature[] lives = new Creature[2];
        lives[0] = new Player(labels[0], 100, 100);
        lives[1] = new Cat(labels[1], 100, 100);

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

       // 体力がある限り実行
        while (lives[0].getHp() > 0 || lives[1].getHp() > 0) {
         
            String command = "";

            //入力の処理
            boolean isValidCommand = false;
            while (!isValidCommand) {
                System.out.println("day" + day + "の動作を入力");
                command = sc.nextLine();

                // 正規表現を使用して条件をチェック
                if (command.matches("[01]{6}")) {
                    isValidCommand = true;
                } else {
                    System.out.println("無効な入力です．1か0の6文字で入力してください．");
                }
            }
            
            // 登場人物ごとの動作
            for (Creature live : lives) {
                
                for(int i = 0; i < 3; i++){
                    char action = command.charAt(i + (lives[0] == live ? 0 : 3));
                    // それぞれの動作
                    switch (i % 3) {
                        case 0:
                            if(action == '1'){
                                live.eat();
                            }else{
                                live.stress(25,20);
                            }
                            break;
                        case 1:
                            if(action == '1'){
                                //猫ちゃんの時はプレイヤーを，プレイヤーの時は猫ちゃんを引数に渡す
                                live.play(lives[(lives[0] == live) ? 1 : 0]);
                            }
                            else{
                                live.stress(15,10);
                            }
                            break;
                        case 2:
                            if(action == '1'){
                                live.sleep();
                            }
                            else{
                                live.stress(20,10);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            // 相対的な状態の計算
            float playerState = (((float)lives[0].getHp() / 100) + ((float)lives[0].getHappy() / 100)) / 2;
            float catState = (((float)lives[1].getHp() / 100) + ((float)lives[1].getHappy() / 100)) / 2;
            float totalState = (playerState + catState) / 2;
            // その日の状態表示
            if (totalState >= 0.95) {
                System.out.println("お互い幸せです！！");
            }
            else if(totalState < 0.95 && totalState >= 0.8){
                System.out.println("その調子です！");
            }
            else if(totalState < 0.8 && totalState >= 0.5){
                System.out.println("嫌な予感がしますね．．．");
            }
            else if(totalState < 0.5){
                System.out.println("非常にまずいです");
            }
            // 上記のデバッグ用表示
            if(LogMode.isLogMode ==true){
                for (Creature life : lives) {
                    System.out.println(life.getName() + "の体力: " + life.getHp() + "%");
                    System.out.println(life.getName() + "の幸福度: " + life.getHappy() + "%");
                }
                System.out.println("playerState" + playerState);
                System.out.println("catState" + catState);
                System.out.println("totalState" + totalState);
            } 
            day++;
            System.out.println("--------------------");
            // sc.close();
        }
    }
}