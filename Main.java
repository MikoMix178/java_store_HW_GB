import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Toy {
    private int id;
    private String name;
    private int weight;

    public Toy(int id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + weight;
    }
}

class ToyLottery {
    private List<Integer> toyIds;
    private List<String> toyNames;
    private List<Integer> toyWeights;
    private PriorityQueue<Toy> toyQueue;

    public ToyLottery() {
        toyIds = new ArrayList<>();
        toyNames = new ArrayList<>();
        toyWeights = new ArrayList<>();
        toyQueue = new PriorityQueue<>((t1, t2) -> Integer.compare(t2.getWeight(), t1.getWeight()));
    }

    public void put(String toyInfo) {
        String[] parts = toyInfo.split(" ");
        int id = Integer.parseInt(parts[0]);
        int weight = Integer.parseInt(parts[1]);
        String name = parts[2];
        
        toyIds.add(id);
        toyNames.add(name);
        toyWeights.add(weight);
        
        toyQueue.add(new Toy(id, name, weight));
    }

    public int getRandomToyId() {
        int totalWeight = toyQueue.stream().mapToInt(Toy::getWeight).sum();
        int randomIndex = new Random().nextInt(totalWeight);
        int currentWeight = 0;
        for (Toy toy : toyQueue) {
            currentWeight += toy.getWeight();
            if (randomIndex < currentWeight) {
                return toy.getId();
            }
        }
        return -1; // Should never reach here
    }

    public void performLottery(int times) {
        try (FileWriter writer = new FileWriter("lottery_results.txt")) {
            for (int i = 0; i < times; i++) {
                int toyId = getRandomToyId();
                writer.write(toyId + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ToyLottery lottery = new ToyLottery();
        lottery.put("1 2 constructor");
        lottery.put("2 2 robot");
        lottery.put("3 6 doll");

        lottery.performLottery(10);
    }
}
