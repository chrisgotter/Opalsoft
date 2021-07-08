package access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// This represents the Data Access Layer DAO beans etc...
public class ContactDataInterface {


    private static final Pattern PATTERN = Pattern.compile(
            "^(?:\"([^\"]*)\"|([^,]*)),(?:\"([^\"]*)\"|([^,]*)),(?:\"([^\"]*)\"|([^,]*)),(?:\"([^\"]*)\"|([^,]*))$");

    private final List<Object[]> CONTACTS = new ArrayList<>();
    private final File source;
    public ContactDataInterface(String source) {
        this.source = new File(source);
        upload();
    }

    @SafeVarargs
    private static <E> E coalesce(E... values) {
        for (E value : values) {
            if (value != null) {
                return value;
            }
        }
        return null;
    }


    public void upload() {
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            String line = reader.readLine();
            while (line != null) {

                final Matcher matcher = PATTERN.matcher(line);
                if (matcher.find()) {
                    final String firstName = coalesce(matcher.group(1), matcher.group(2));
                    final String lastName = coalesce(matcher.group(3), matcher.group(4));
                    final String email = coalesce(matcher.group(5), matcher.group(6));
                    final String phoneNumber = coalesce(matcher.group(7), matcher.group(8));
                    CONTACTS.add(new Object[]{firstName, lastName, email, phoneNumber});
                } else {
                    throw new RuntimeException("\"" + line + "\" is improperly formatted");
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Object[][] toArray() {
        return CONTACTS.toArray(new Object[CONTACTS.size()][]);
    }
    
    public void persist(Object[][] data) {
        CONTACTS.clear();
        for (Object[] contact : data) {
            CONTACTS.add(contact);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(source, false));) {
            Iterator<Object[]> itr = CONTACTS.iterator();
            while (itr.hasNext()) {
                writer.append(toCSVString(itr.next()));
                if (itr.hasNext()) {
                    writer.append("\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static String toCSVString(Object... record) {
        if (record.length == 0) {
            return "";
        } else {

            final StringBuffer buff = new StringBuffer();
            for (Object value : record) {
                if (value == null) {
                    buff.append(",");
                } else if (value instanceof CharSequence) {
                    buff.append("\"").append(value).append("\",");
                }
            }
            return buff.substring(0, buff.length() - 1);
        }
        
    }
}
