import java.util.List;

public class Main {
    public static void main(String[] args) {
        ItemDatabaseOperation.getAllData();
        List<ItemFromMysql> itemList = ItemDatabaseOperation.getItemList();
        ItemFileHandler.writeItemToFile(itemList, "Items.txt");
        List<ItemFromMysql> itemsRead = ItemFileHandler.readItemsFromFile("Items.txt");

        System.out.println("Fájlból beolvasott adatok:");
        for(ItemFromMysql item : itemsRead){

            System.out.println(item);
        }

        ItemFromMysql existingItem = new ItemFromMysql(8,"szemelyauto", 1080, "lila");
        ItemDatabaseOperation.updateItem(existingItem);
        ItemDatabaseOperation.updateItemColumn(8,"szin", "cian");
        //ItemDatabaseOperation.deleteItem(8);
        ItemDatabaseOperation.insertNewItem(new ItemFromMysql("Hybrid",2700,"PlasticBlue"));
    }
}