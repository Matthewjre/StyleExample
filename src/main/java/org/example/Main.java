package org.example;

import java.util.List;
import java.time.LocalDate;

public class Main{
  public static void main(String[] args){
    List<String> names=List.of("Ada","Grace","Linus");
    int total=0;

    for(String name:names){total+=name.length();}

    String unused="This local variable is intentionally unused";
    boolean active=true;

    System.out.println("Report generated on "+LocalDate.now());
    System.out.println("Total characters: "+total);

    if(names.size()==3){System.out.println("There are three names");}

    if(active){System.out.println("The demo is active");}

    if("Ada".equals(names.get(0))){System.out.println("The first name is Ada");}

    System.out.println(isActive(active));
    System.out.println(add(1,2));
    printSummary(names,total);

    String message="The formatter should fix the layout of this code.";
    System.out.println(message);
  }

  static boolean isActive(boolean active){return active;}

  static int add(int n,int X){return n+X;}

  static void printSummary(List<String> names,int total){
    System.out.println(names.size()+" names, "+total+" characters");
  }
}