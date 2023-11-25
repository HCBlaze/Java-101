import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class ItemFromMysql implements Serializable {
    private int id;
    private String tipus;
    private int suly;
    private String szin;

    public ItemFromMysql(int id, String tipus, int suly, String szin){
        this.id = id;
        this.tipus = tipus;
        this.suly = suly;
        this.szin = szin;
    }
    public ItemFromMysql(String tipus, int suly, String szin){
        this.id = ItemDatabaseOperation.getNewId();
        this.tipus = tipus;
        this.suly = suly;
        this.szin = szin;
    }

    public static ItemFromMysql createItemFromSet(Set<String> itemInfo){
        if(itemInfo.size() != 4){
            throw new IllegalArgumentException("Invalid set size! Excepted size: 4");
        }

        int id = Integer.parseInt(itemInfo.iterator().next());
        itemInfo.remove(String.valueOf(id));

        String tipus = itemInfo.iterator().next();
        itemInfo.remove(String.valueOf(tipus));

        int suly = Integer.parseInt(itemInfo.iterator().next());
        itemInfo.remove(String.valueOf(suly));

        String szin = itemInfo.iterator().next();
        itemInfo.remove(String.valueOf(szin));

        return new ItemFromMysql(id,tipus,suly,szin);
    }
    public static ItemFromMysql createItemFromSet(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt(1);
        String tipus = resultSet.getString(2);
        int suly = resultSet.getInt(3);
        String szin = resultSet.getString(4);

        return new ItemFromMysql(id,tipus,suly,szin);

    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", spec='" + tipus + '\'' +
                ", weight=" + suly +
                ", color='" + szin + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public int getSuly() {
        return suly;
    }

    public void setSuly(int suly) {
        this.suly = suly;
    }

    public String getSzin() {
        return szin;
    }

    public void setSzin(String szin) {
        this.szin = szin;
    }
}
