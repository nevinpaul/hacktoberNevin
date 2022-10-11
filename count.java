
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

// Java program to print each word exactly once
public class Unique {
    public static void main(String args[]) throws IOException{

        File file = new File("/home/dxuser/gitTraining/activity-one/test.txt");
        Scanner read1 = new Scanner(file);
        String word;
        HashSet<String> unique =  new HashSet<>();
        ArrayList<String> allWord = new ArrayList<String>();
        while(read1.hasNext())
        {
            word = read1.next();
            word = word.toLowerCase();
            word = word.replaceAll("\\p{Punct}", " ");
            allWord.add(word);
            unique.add(word);
        }
        System.out.println("\nThese are the original words : \n" + allWord);
        int wLength = allWord.size();


        ArrayList<String> wordCount = new ArrayList<>(unique);
        int lenWordCount = wordCount.size();
        int[] len = new int[lenWordCount];

        System.out.println("\n");
        for(int i = 0; i<lenWordCount ; i++)
        {
            int count = 0;
            for(int j = 0; j<wLength ; j++)
            {
                if(wordCount.get(i).equals(allWord.get(j)))
                {
                    count = count +1;
                }
            }
            len[i] = count;
        }
        System.out.println("\n These are the words which appear only once");
        for(int i = 0; i<lenWordCount ; i++)
        {
            if(len[i] > 1)
            {
                continue;
            }
            System.out.println(wordCount.get(i));
        }
    }
}
