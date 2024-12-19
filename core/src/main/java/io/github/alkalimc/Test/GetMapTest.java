package io.github.alkalimc.Test;

import javax.swing.*;
import io.github.nofe1248.map.map.Map;

public class GetMapTest {

    public static void getMapTest(Map map) {
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setText(map.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JFrame frame = new JFrame("Map Information");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
