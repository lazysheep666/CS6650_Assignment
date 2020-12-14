package client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Caculator {
  List<Integer> posts;
  List<Integer> gets;
  private String fileName;
  public Caculator(String fileName) throws IOException {
    this.fileName = fileName;
    posts = new ArrayList<>();
    gets = new ArrayList<>();
    BufferedReader csvReader = new BufferedReader(new FileReader(this.fileName));
    String row;
    while ((row = csvReader.readLine()) != null) {
      String[] data = row.split(",");
      if (data[1].equals("POST")) {
        posts.add(Integer.parseInt(data[2]));
      } else if (data[1].equals("GET")) {
        gets.add(Integer.parseInt(data[2]));
      }
    }
    csvReader.close();
    Collections.sort(posts);
    Collections.sort(gets);
  }

  public int getPostsMedianResponseTime() {
    return (posts.get(posts.size() / 2) + posts.get((posts.size() - 1)/ 2)) / 2;
  }
  public int getGetsMedianResponseTime() {
    return (gets.get(gets.size() / 2) + gets.get((gets.size() - 1)/ 2)) / 2;
  }
  public int getPostsMeanResponseTime() {
    int total = 0;
    for (int t : posts) {
      total += t;
    }
    return total / posts.size();
  }
  public int getGetsMeanResponseTime() {
    int total = 0;
    for (int t : gets) {
      total += t;
    }
    return total / gets.size();
  }

  public int getMaxPostsResponseTime() {
    int max = 0;
    for (int t : posts) {
      max = Math.max(max, t);
    }
    return max;
  }
  public int getMaxGetsResponseTime() {
    int max = 0;
    for (int t : gets) {
      max = Math.max(max, t);
    }
    return max;
  }
  public int get99GetsResponseTime() {
    return gets.get((int)(gets.size() * 0.99) - 1);
  }
  public int get99PostsResponseTime() {
    return posts.get((int)(posts.size() * 0.99) - 1);
  }
}
