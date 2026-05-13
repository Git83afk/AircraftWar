import javax.swing.*;

public class SelectUI {
    private JButton easyButton;
    private JButton hardButton;
    private JButton normalButton;
}
package edu.hitsz.application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectUI {
    private JPanel mainPanel;
    private JButton easyButton;
    private JButton normalButton;
    private JButton hardButton;

    public SelectUI() {
        // 简单模式按钮点击事件
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(1); // 假设 1 代表简单模式
            }
        });

        // 普通模式按钮点击事件
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(2); // 假设 2 代表普通模式
            }
        });

        // 困难模式按钮点击事件
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(3); // 假设 3 代表困难模式
            }
        });
    }

    private void startGame(int difficulty) {
        // TODO: 这里需要调用 Main 中的 CardLayout 切换到游戏界面，并传递难度参数
        // 我们将在第三阶段实现这个切换逻辑
        System.out.println("选择了难度: " + difficulty);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}