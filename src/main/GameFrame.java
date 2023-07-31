package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MyPanel extends JPanel { // 프레임에 들어갈 패널 정의 / 메서드 정의
    private List<ImageIcon> imageList;
    private JLabel imageLabel;
    private int currentIndex;
    private List<Integer> usedIndexes;
    private JTextField titleTextField;
    private List<String> nameList;
    private String userAnswer;
    private String correctAnswer;
    private int totalImages;
    private int correctCount;
    private JTextArea logTextArea;

    public void loadImages() {// 파일의 절대경로를 지정해서 이미지를 불러오는 과정 / 이미지와 이름에 랜덤 인덱스 부여
        imageList = new ArrayList<>();
        usedIndexes = new ArrayList<>();
        nameList = new ArrayList<>();
        File directory = new File("picture");// 파일의 절대경로 지정
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".jpg")) {
                    ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
                    imageList.add(imageIcon);
                    String str = file.getName();
                    str = str.replaceAll("\\.jpg$", "");
                    nameList.add(str);
                }
            }
        }
        totalImages = imageList.size(); // 이미지 파일의 총갯수 지정 -> 게임 종료를 위함
    }

    public void showNextImage() {// 난수를 생성해서 이미지와 이름을 랜덤하게 표시

        // 중복되지 않는 다음 이미지 선택
        Random random = new Random();
        do {
            currentIndex = random.nextInt(imageList.size());
        } while (usedIndexes.contains(currentIndex));

         // 이미지 표시
        ImageIcon imageIcon = imageList.get(currentIndex);
        imageLabel.setIcon(imageIcon);

        // 0.3초 후 이미지 제거
        Timer timer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageLabel.setIcon(null);
            }
        });
        timer.setRepeats(false);
        timer.start();

        // 사용된 인덱스 기록
        usedIndexes.add(currentIndex);
    }

    public MyPanel() { // 마이패널 생성자
        setLayout(new GridLayout(1, 3));

        // 왼쪽 패널 - 이미지를 표시할 레이블 생성
        JPanel leftPanel = new JPanel(new BorderLayout());
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        leftPanel.add(imageLabel, BorderLayout.CENTER);
        add(leftPanel);
        
        // 오른쪽 패널 생성
        JPanel rightPanel = new JPanel(new BorderLayout());

         // 텍스트 필드 생성
        titleTextField = new JTextField();
        titleTextField.setPreferredSize(new Dimension(200, 50));// 텍스트 필드 크기 조정
        titleTextField.setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬
        Font textFieldFont = new Font("맑은고딕", Font.BOLD, 30); // 폰트 설정 (폰트 이름, 스타일, 크기)
        titleTextField.setFont(textFieldFont); // 폰트 적용
        rightPanel.add(titleTextField, BorderLayout.SOUTH);

        // 로그 텍스트 영역 생성
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        rightPanel.add(new JScrollPane(logTextArea), BorderLayout.CENTER);

        add(rightPanel, BorderLayout.CENTER);

        titleTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        loadImages(); // 실행할 때 모든 이미지를 미리 로드 시킴
        showNextImage();
    }

    public void checkAnswer() {
        userAnswer = titleTextField.getText();
        correctAnswer = nameList.get(currentIndex);

        // 정답 처리
        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            JOptionPane.showMessageDialog(this, "정답!", "정답", JOptionPane.INFORMATION_MESSAGE);
            correctCount++;
            if (correctCount == totalImages) {
                gameEndDialog();
            } else {// 다음 이미지 표시
                showNextImage();
            }
        }

        // 오답 처리
         else {
            JOptionPane.showMessageDialog(this, "오답!", "오답", JOptionPane.ERROR_MESSAGE);
            correctCount++;
            if (correctCount == totalImages) {
                gameEndDialog();
            } 
            else {
             Timer timer = new Timer(300, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showNextImage();
                }
             });
             timer.setRepeats(false);
             timer.start();
            }
        }

        // 입력 필드 초기화
        titleTextField.setText("");

        // 로그 텍스트 영역에 기록
        logTextArea.append("입력: " + userAnswer + ", 정답: " + correctAnswer + "\n");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }

    public void gameEndDialog() {
        JOptionPane.showMessageDialog(this, "게임이 종료되었습니다.", "게임 종료", JOptionPane.INFORMATION_MESSAGE);
        gameExit();
    }

    public void gameExit() {
        System.exit(1);
    }
}

public class GameFrame extends JFrame {
    MyPanel myPanel;
    public GameFrame() {// 프레임 설정
        setTitle("Random Image Display");
        setExtendedState(JFrame.MAXIMIZED_BOTH);// 전체 화면으로 실행
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myPanel = new MyPanel();
        add(myPanel);

        setVisible(true);
    }
}