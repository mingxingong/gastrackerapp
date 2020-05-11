package ui.fileio;

import model.creditcards.CreditCard;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class CreditCardReader2 {
    public static List<CreditCard> main() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("c.tmp");
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<CreditCard> card = (List<CreditCard>) ois.readObject();
        ois.close();

        return card;
    }
}
