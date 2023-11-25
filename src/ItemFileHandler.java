import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemFileHandler {

    public static void writeItemToFile(List<ItemFromMysql> items, String fileName){
        try ( ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(fileName))) {
           write.writeObject(items);
            System.out.println("Item successfully written to the file.");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void writeItemToFile(List<ItemFromMysql> items, String fileName, String encoding){
        try ( BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), encoding))) {
           write.write(items.toString());
            System.out.println("Item successfully written to the file, with encoding "+ encoding);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static List<ItemFromMysql> readItemsFromFile(String fileName){
        List<ItemFromMysql> items = new ArrayList<>();

        try(ObjectInputStream read = new ObjectInputStream(new FileInputStream(fileName))) {

            Object obj = read.readObject();
            if(obj instanceof List){
                items = (List<ItemFromMysql>) obj;
                System.out.println("Items successfully read from the file.");
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return items;
    }
    public static List<ItemFromMysql> readItemsFromFile(String fileName, String encoding){
        List<ItemFromMysql> items = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),encoding))) {

            String line;
           while ((line = reader.readLine()) != null){
               String[] temp = line.split(";");
                items.add(new ItemFromMysql(Integer.parseInt(temp[0]),temp[1],Integer.parseInt(temp[2]),temp[3]));
                System.out.println("Items successfully read from the file.");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return items;
    }
}
