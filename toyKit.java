class Person{
    private String firstName;
    private String lastName;
    private int age;
    private double heightM;
    private double weightKg;
    private String biologicalSex;

    public Person(String firstName, String lastName, int age, double heightM, double weightKg, String biologicalSex){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.heightM = heightM;
        this.weightKg = weightKg;
        this.biologicalSex = biologicalSex;
    }

    public String getName(){
        return this.firstName + " " + this.lastName;
    }
    public int getAge(){
        return this.age;
    }

    public String toString(){
        return this.getName();
    }
}

interface Product{
    abstract public double getCost(); // 価格を取得する
    abstract public void setCost(double cost); // 価格を設定する
}

abstract class Parts implements Product{
    protected int difficulty; // 難易度
    protected double cost; // 価格

    public double getCost(){
        return this.cost;
    }
    public void setCost(double cost){
        this.cost = cost;
    }
    public void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }
    // 一人で扱えるかどうか判定する関数
    public boolean canHandleAlone(Person person){
        int age = person.getAge();
        if(age <= 5)return this.difficulty <= 20;
        else if(age <= 8)return this.difficulty <= 50;
        else if(age <= 10)return this.difficulty <= 80;
        else if(age <= 12)return this.difficulty <= 100;
        else return true;
    }
    abstract public boolean assembled(); // 他の部品と組み立てられているかどうか
    abstract public void effect(); // 音を出す、光る、などの効果を発動する
}

class Head extends Parts{
    protected Eyes eyes; // 目
    protected boolean onTorso; // 胴体と組み立てられているかどうか

    public Head(){
        super();
    }

    public void assemble(){
        this.onTorso = true;
    }
    // 目の部品を受けとって組み立てる
    public void assemble(Eyes eyes){
        System.out.println("Assembling the head and eyes.");
        eyes.assemble();
        this.eyes = eyes;
    }
    public boolean assembled(){
        return this.eyes != null && this.eyes.assembled();
    }
    public void effect(){
        System.out.println("No effect.");
    }
    public String toString(){
        return "This is the head.";
    }
}

class Eyes extends Parts{
    protected boolean onHead; // 頭部と組み立てられているかどうか

    public Eyes(){
        super();
    }

    public void assemble(){
        this.onHead = true;
    }
    public boolean assembled(){
        return this.onHead;
    }
    public void effect(){
        System.out.println("No effect.");
    }
    public String toString(){
        return "This is the eyes.";
    }
}

class Torso extends Parts{
    protected Head head; // 頭部
    protected Arm leftArm; // 左腕
    protected Arm rightArm; // 右腕
    protected Leg leftLeg; // 左脚
    protected Leg rightLeg; // 右脚

    public Torso(){
        super();
    }

    // 頭部の部品を受けとって組み立てる
    public void assemble(Head head){
        System.out.println("Assembling the body and head.");
        this.head = head;
        this.head.assemble();
    }
    // 腕の部品を受けとって組み立てる
    public void assemble(Arm arm){
        if(arm.isLeft == arm.isRight)return;
        System.out.println("Assembling the body and " + (arm.isLeft ? "left" : "right") + " arm.");
        arm.assemble();
        if(arm.isLeft)this.leftArm = arm;
        else this.rightArm = arm;
    }
    // 脚の部品を受けとって組み立てる
    public void assemble(Leg leg){
        if(leg.isLeft == leg.isRight)return;
        System.out.println("Assembling the body and " + (leg.isLeft ? "left" : "right") + " leg.");
        leg.assemble();
        if(leg.isLeft)this.leftLeg = leg;
        else this.rightLeg = leg;
    }
    public boolean assembled(){
        return this.head != null && this.leftArm != null && this.rightArm != null && this.leftLeg != null && this.rightLeg != null 
        && this.head.assembled() && this.leftArm.assembled() && this.rightArm.assembled() && this.leftLeg.assembled() && this.rightLeg.assembled();
    }
    public void effect(){
        System.out.println("No effect.");
    }
    public String toString(){
        return "This is the torso.";
    }
}

class Arm extends Parts{
    protected boolean isLeft; // 左腕かどうか
    protected boolean isRight; // 右腕かどうか
    protected Hand hand; // 手
    protected boolean onTorso; // 胴体と組み立てられているかどうか

    public Arm(){
        super();
    }

    public void assemble(){
        this.onTorso = true;
    }
    // 手の部品を受け取って組み立てる
    public void assemble(Hand hand){
        if(hand.isLeft == hand.isRight)return;
        if(this.isLeft && !this.isRight && hand.isRight)return;
        if(!this.isLeft && this.isRight && hand.isLeft)return;

        System.out.println("Assembling the arm and " + (hand.isLeft ? "left" : "right") + " hand.");
        hand.assemble();
        this.hand = hand;
    }
    public boolean assembled(){
        return this.hand != null && this.hand.assembled();
    }
    public void effect(){
        System.out.println("No effect.");
    }
    public String toString(){
        return "This is the arm.";
    }
}

class Hand extends Parts{
    protected boolean isLeft; // 左手かどうか
    protected boolean isRight; // 右手かどうか
    protected boolean onArm; // 腕と組み立てられているかどうか

    public Hand(){
        super();
    }

    public void assemble(){
        this.onArm = true;
    }
    public boolean assembled(){
        return this.onArm;
    }
    public void effect(){
        System.out.println("No effect.");
    }
    public String toString(){
        return "This is the hand.";
    }
}

class Leg extends Parts{
    protected boolean isLeft; // 左脚かどうか
    protected boolean isRight; // 右脚かどうか
    protected boolean onTorso; // 胴体と組み立てられているかどうか

    public Leg(){
        super();
    }

    public void assemble(){
        this.onTorso = true;
    }
    public boolean assembled(){
        return this.onTorso;
    }
    public void effect(){
        System.out.println("No effect.");
    }
    public String toString(){
        return "This is the leg.";
    }
}

class VampireHead extends Head{
    public static final int VAMPIREHEAD_DIFFICULTY = 50;
    public static final double VAMPIREHEAD_COST = 5.0;

    public VampireHead(){
        super();
        this.setCost(this.VAMPIREHEAD_COST);
        this.setDifficulty(this.VAMPIREHEAD_DIFFICULTY);
    }

    public void effect(){
        System.out.println("Fangs are moving.");
    }
    public String toString(){
        return "This is the vampire's head" + (this.eyes != null ? " with eyes(" + this.eyes + ")." : ".") + " Clicking button make pangs move.";
    }
}

class VampireEyes extends Eyes{
    public static final int VAMPIREEYES_DIFFICULTY = 80;
    public static final double VAMPIREEYES_COST = 2.0;

    public VampireEyes(){
        super();
        this.setCost(this.VAMPIREEYES_COST);
        this.setDifficulty(this.VAMPIREEYES_DIFFICULTY);
    }

    public void effect(){
        System.out.println("Vampire's eyes are growing.");
    }
    public String toString(){
        return "This is the vampire's eyes grow.";
    }
}

class VampireTorso extends Torso{
    public static final int VAMPIRETORSO_DIFFICULTY = 10;
    public static final double VAMPIRETORSO_COST = 6.0;

    public VampireTorso(){
        super();
        this.setCost(this.VAMPIRETORSO_COST);
        this.setDifficulty(this.VAMPIRETORSO_DIFFICULTY);
    }

    public void effect(){
        System.out.println("Screech!");
    }
    public String toString(){
        return "This is the vampire torso with" + (this.head != null ? " head,(" + this.head + ")" : (this.leftArm != null ? " left arm,(" + this.leftArm + ")" : (this.rightArm != null ? " right arm,(" + this.rightArm + ")" : (this.leftLeg != null ? " left leg,(" + this.leftLeg + ")" : (this.rightLeg != null ? " right leg(" + this.rightLeg + ")." : " no parts.")))));
    }
}

class VampireLeftArm extends Arm{
    public static final int VAMPIRELEFTARM_DIFFICULTY = 30;
    public static final double VAMPIRELEFTARM_COST = 3.0;

    public VampireLeftArm(){
        super();
        this.isLeft = true;
        this.setCost(this.VAMPIRELEFTARM_COST);
        this.setDifficulty(this.VAMPIRELEFTARM_DIFFICULTY);
    }

    public String toString(){
        return "This is the vampire left arm" + (this.hand != null ? " with hand(" + this.hand + ")." : ".");
    }
}

class VampireRightArm extends Arm{
    public static final int VAMPIRERIGHTARM_DIFFICULTY = 30;
    public static final double VAMPIRERIGHTARM_COST = 3.0;

    public VampireRightArm(){
        super();
        this.isRight = true;
        this.setCost(this.VAMPIRERIGHTARM_COST);
        this.setDifficulty(this.VAMPIRERIGHTARM_DIFFICULTY);
    }

    public String toString(){
        return "This is the vampire right arm" + (this.hand != null ? " with hand(" + this.hand + ")." : ".");
    }
}

class VampireLeftHand extends Hand{
    public static final int VAMPIRELEFTHAND_DIFFICULTY = 10;
    public static final double VAMPIRELEFTHAND_COST = 2.5;

    public VampireLeftHand(){
        super();
        this.isLeft = true;
        this.setCost(this.VAMPIRELEFTHAND_COST);
        this.setDifficulty(this.VAMPIRELEFTHAND_DIFFICULTY);
    }

    public String toString(){
        return "This is the vampire left hand holding sharp claws.";
    }
}

class VampireRightHand extends Hand{
    public static final int VAMPIRELEFTHAND_DIFFICULTY = 10;
    public static final double VAMPIRERIGHTHAND_COST = 2.5;

    public VampireRightHand(){
        super();
        this.isRight = true;
        this.setCost(this.VAMPIRERIGHTHAND_COST);
        this.setDifficulty(this.VAMPIRELEFTHAND_DIFFICULTY);
    }

    public String toString(){
        return "This is the vampire right hand holding a cup with blood in it.";
    }
}

class VampireLeftLeg extends Leg{
    public static final int VAMPIRELEFTLEG_DIFFICULTY = 40;
    public static final double VAMPIRELEFTLEG_COST = 3.5;

    public VampireLeftLeg(){
        super();
        this.isLeft = true;
        this.setCost(this.VAMPIRELEFTLEG_COST);
        this.setDifficulty(this.VAMPIRELEFTLEG_DIFFICULTY);
    }

    public String toString(){
        return "This is a cold-blooded left leg.";
    }
}

class VampireRightLeg extends Leg{
    public static final int VAMPIRERIGHTLEG_DIFFICULTY = 40;
    public static final double VAMPIRERIGHTLEG_COST = 3.5;

    public VampireRightLeg(){
        super();
        this.isRight = true;
        this.setCost(this.VAMPIRERIGHTLEG_COST);
        this.setDifficulty(this.VAMPIRERIGHTLEG_DIFFICULTY);
    }

    public String toString(){
        return "This is a cold-blooded right leg.";
    }
}

abstract class Toy{
    protected String microchip;
    protected String circuitBoard;
    protected String battery;

    abstract public boolean assembled();
    abstract public double getCosts();
}

interface HumanoidToy{
    abstract public void makeSound();
    abstract public void walk();
}

class HumanoidToyKit extends Toy implements HumanoidToy{
    protected Head head;
    protected Eyes eyes;
    protected Torso torso;
    protected Arm leftArm;
    protected Arm rightArm;
    protected Hand leftHand;
    protected Hand rightHand;
    protected Leg leftLeg;
    protected Leg rightLeg;

    public HumanoidToyKit(Head head, Eyes eyes, Torso torso, Arm leftArm, Arm rightArm, Hand leftHand, Hand rightHand, Leg leftLeg, Leg rightLeg){
        this.head = head;
        this.eyes = eyes;
        this.torso = torso;
        this.leftArm = leftArm;
        this.rightArm = rightArm;
        this.leftHand = leftHand;
        this.rightHand = rightHand;
        this.leftLeg = leftLeg;
        this.rightLeg = rightLeg;
    }

    public Head getHead(){
        return this.head;
    }
    public Eyes getEyes(){
        return this.eyes;
    }
    public Torso getTorso(){
        return this.torso;
    }
    public Arm getLeftArm(){
        return this.leftArm;
    }
    public Arm getRightArm(){
        return this.rightArm;
    }
    public Hand getLeftHand(){
        return this.leftHand;
    }
    public Hand getRightHand(){
        return this.rightHand;
    }
    public Leg getLeftLeg(){
        return this.leftLeg;
    }
    public Leg getRightLeg(){
        return this.rightLeg;
    }

    public Parts[] getPartsArr(){
        return new Parts[]{this.head, this.eyes, this.torso, this.leftArm, this.rightArm, this.leftHand, this.rightHand, this.leftLeg, this.rightLeg};
    }

    public boolean assembled(){
        for(Parts parts : this.getPartsArr()){
            if(!parts.assembled())return false;
        }
        return true;
    }
    public double getCosts(){
        double costs = 0;
        if(!this.assembled()){
            System.out.println("This toy kit have not be assembled completely.");
            return costs;
        }
        for(Parts parts : this.getPartsArr()){
            costs += parts.getCost();
        }
        return costs;
    }
    public void makeSound(){
        System.out.println("This humanoid is making sound.");
    }
    public void walk(){
        System.out.println("This humanoid is walking.");
    }
}
interface VampireToy{
    abstract public void clickBackButton();
    abstract public void clickHeadButton();
}
class VampireToyKit extends HumanoidToyKit implements VampireToy{
    public static final String NAME = "Vampire";
    public static final String VAMPIRETOYKIT_MICROCHIP = "PIC18F25K42-I/SP";
    public static final String VAMPIRETOYKIT_CURCUITBOARD = "FCU6-DX450";
    public static final String VAMPIRETOYKIT_BATTERY = "361-00056-50";

    public VampireToyKit(Head head, Eyes eyes, Torso torso, Arm leftArm, Arm rightArm, Hand leftHand, Hand rightHand, Leg leftLeg, Leg rightLeg){
        super(head, eyes, torso, leftArm, rightArm, leftHand, rightHand, leftLeg, rightLeg);
        this.microchip = this.VAMPIRETOYKIT_MICROCHIP;
        this.circuitBoard = this.VAMPIRETOYKIT_CURCUITBOARD;
        this.battery = VAMPIRETOYKIT_BATTERY;
    }

    public Invoice issueInvoice(Person person){
        if(!this.assembled()){
            return null;
        }
        return new Invoice(this.NAME + " toy kit", "This toy has been assembled.", this.getCosts(), person);
    }
    public String toString(){
        String newLine = System.lineSeparator();
        return "This is the vampire toy kit. - - - - - - - - - - - - - - - -" + newLine + 
                this.kitInfo() + 
                "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -";
    }
    public String kitInfo(){
        String newLine = System.lineSeparator();
        return "Microchip: " + this.microchip + newLine + 
                "CircuitBoard: " + this.circuitBoard + newLine + 
                "Battery: " + this.battery + newLine + 
                "Head: " + this.getHead() + newLine + 
                "Eyes: " + this.getEyes() + newLine + 
                "LeftArm: " + this.getLeftArm() + newLine + 
                "RightArm: " + this.getRightArm() + newLine + 
                "LeftHand: " + this.getLeftHand() + newLine + 
                "RightHand: " + this.getRightHand() + newLine +
                "LeftLeg: " + this.getLeftLeg() + newLine + 
                "RightLeg: " + this.getRightLeg() + newLine;
    }
    // 背中のボタンを押す
    public void clickBackButton(){
        this.getTorso().effect();
    }
    // 頭部のボタンを押す
    public void clickHeadButton(){
        this.getHead().effect();
    }
}
interface HumanoidToyKitFactory{
    abstract public Head createHead();
    abstract public Eyes createEyes();
    abstract public Torso createTorso();
    abstract public Arm createLeftArm();
    abstract public Arm createRightArm();
    abstract public Hand createLeftHand();
    abstract public Hand createRightHand();
    abstract public Leg createLeftLeg();
    abstract public Leg createRightLeg();

    abstract public HumanoidToyKit createToy();
}
class VampireToyKitFactory implements HumanoidToyKitFactory{
    public Head createHead(){
        return new VampireHead();
    }
    public Eyes createEyes(){
        return new VampireEyes();
    }
    public Torso createTorso(){
        return new VampireTorso();
    }
    public Arm createLeftArm(){
        return new VampireLeftArm();
    }
    public Arm createRightArm(){
        return new VampireRightArm();
    }
    public Hand createLeftHand(){
        return new VampireLeftHand();
    }
    public Hand createRightHand(){
        return new VampireRightHand();
    }
    public Leg createLeftLeg(){
        return new VampireLeftLeg();
    }
    public Leg createRightLeg(){
        return new VampireRightLeg();
    }
    public HumanoidToyKit createToy(){
        return new VampireToyKit(this.createHead(), this.createEyes(), this.createTorso(), this.createLeftArm(), this.createRightArm(), this.createLeftHand(), this.createRightHand(), this.createLeftLeg(), this.createRightLeg());
    }
}

class FairyWorld{
    public HumanoidToyKit toyStore(Person person, HumanoidToyKitFactory factory, String toyKit){
        HashMap<String, HumanoidToyKit> map = new HashMap<>(){
            {
                // put("robot", factory.createRobot());
                put("vampire", factory.createToy());
                // put("werewolf", factory.createWerewolf());
                // put("transformer", factory.createTransformer());
                // put("alien", factory.createAlien());
            }
        };
        String endl = System.lineSeparator();
        System.out.println(person + " buy toy kits!" + endl);

        HumanoidToyKit toy = map.get(toyKit);
        System.out.println(toy + endl);

        // 対象年齢を確認します
        boolean alone = true;
        for(Parts parts : toy.getPartsArr()){
            if(!parts.canHandleAlone(person))alone = false;
        }
        if(!alone)System.out.println(person.getName() + " can't handle this toy kit alone. Let's assemble with parent. ");

        return toy;
    }
}
class Invoice{
    public String title;
    public String description;
    public double costs;
    public Person person;

    public Invoice(String title, String description, double costs, Person person){
        this.title = title;
        this.description = description;
        this.costs = costs;
        this.person = person;
    }
    
    public String toString(){
        String newLine = System.lineSeparator();
        return newLine + "Invoice - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - " + newLine + 
                "Title: " + this.title + newLine + 
                "Description: " + this.description + newLine + 
                "Cost: " + this.costs + newLine + 
                "Person: " + this.person + newLine + 
                "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ";
    }
}
class Main{
    public static void main(String[] args){
        FairyWorld fairyWorld = new FairyWorld();
        Person jessica = new Person("Jessica", "Roller", 30, 1.65, 95, "female");

        VampireToyKit vampire = (VampireToyKit)fairyWorld.toyStore(jessica, new VampireToyKitFactory(), "vampire");

        vampire.clickBackButton();
        vampire.clickHeadButton();

        System.out.println(vampire.getCosts());

        Head vampireHead = vampire.getHead();
        Eyes vampireEyes = vampire.getEyes();
        Torso vampireTorso = vampire.getTorso();
        Arm vampireLeftArm = vampire.getLeftArm();
        Arm vampireRightArm = vampire.getRightArm();
        Hand vampireLeftHand = vampire.getLeftHand();
        Hand vampireRightHand = vampire.getRightHand();
        Leg vampireLeftLeg = vampire.getLeftLeg();
        Leg vampireRightLeg = vampire.getRightLeg();


        System.out.println(vampireHead);
        System.out.println(vampireHead instanceof Head);
        System.out.println(vampireHead instanceof VampireHead);

        vampireHead.assemble(vampireEyes);
        vampireLeftArm.assemble(vampireLeftHand);
        vampireRightArm.assemble(vampireRightHand);
        vampireTorso.assemble(vampireHead);
        vampireTorso.assemble(vampireLeftArm);
        vampireTorso.assemble(vampireRightArm);
        vampireTorso.assemble(vampireLeftLeg);
        vampireTorso.assemble(vampireRightLeg);

        System.out.println(vampire.getCosts());
        System.out.println(vampire.issueInvoice(jessica));
    }
}
