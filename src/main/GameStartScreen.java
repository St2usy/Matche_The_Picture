package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

class GameStartScreen extends JFrame {// 게임시작화면

    private BufferedImage backgroundImage;

    public GameStartScreen() {

        setTitle("Game Start Screen");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // 전체 화면으로 실행
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 동작 설정
        setLayout(new BorderLayout()); // 프레임 레이아웃 설정

        JButton startButton = new JButton(" GAME START ");
        JButton exitButton = new JButton(" END ");

        // 버튼 스타일 설정
        startButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 100)); // "Game Start" 글자 크기와 폰트 설정
        exitButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 100)); // "End" 글자 크기와 폰트 설정
        startButton.setBackground(new Color(59, 89, 182)); // 파란색 배경
        exitButton.setBackground(new Color(255, 71, 87)); // 붉은색 배경
        startButton.setForeground(Color.WHITE); // 흰색 글자색
        exitButton.setForeground(Color.WHITE); // 흰색 글자색
        startButton.setFocusPainted(false); // 포커스 표시 제거
        exitButton.setFocusPainted(false); // 포커스 표시 제거

        //게임 화면으로 전환
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                GameFrame gameFrame = new GameFrame();
                gameFrame.setVisible(true);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(20, 0, 0, 0); // 버튼 위쪽 여백 추가

        constraints.gridx = 1;
        constraints.gridy = 2;
        buttonPanel.add(startButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        buttonPanel.add(exitButton, constraints);

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (backgroundImage != null) {
             g.drawImage(backgroundImage, 300, 50, getWidth(), getHeight(), this); // 배경 이미지 그리기
        }
    }
}