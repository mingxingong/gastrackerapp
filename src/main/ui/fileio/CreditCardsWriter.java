package ui.fileio;

import model.creditcards.CreditCard;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class CreditCardsWriter {
    public static void write(List<CreditCard> cards) throws IOException {
        FileOutputStream fos = new FileOutputStream("t.tmp");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(cards);
        oos.close();
    }
}
