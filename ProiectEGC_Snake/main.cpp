#include <GL/glut.h>
#include <iostream>
#include <vector>
#include <ctime>
#include <math.h>
#include <string>

using namespace std;

const int width = 800;
const int height = 600;
const int gridSize = 20;

int snakeX = width / 2;
int snakeY = height / 2;
int direction = 0;
int score = 0; // Variable to store the score
vector<pair<int, int>> snake;
pair<int, int> fruit;

void init() {
    glClearColor(0.0, 0.0, 0.0, 1.0);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(0, width, 0, height);

    srand(time(NULL));
    fruit.first = (rand() % (width / gridSize)) * gridSize;
    fruit.second = (rand() % (height / gridSize)) * gridSize;
}

void drawRect(int x, int y, float r, float g, float b) {
    glColor3f(r, g, b);
    glBegin(GL_QUADS);
    glVertex2f(x, y);
    glVertex2f(x + gridSize, y);
    glVertex2f(x + gridSize, y + gridSize);
    glVertex2f(x, y + gridSize);
    glEnd();
}

void draw() {
    glClear(GL_COLOR_BUFFER_BIT);

    // Draw snake
    drawRect(snakeX, snakeY, 0.0, 1.0, 0.0);
    for (size_t i = 1; i < snake.size(); ++i) {
        drawRect(snake[i].first, snake[i].second, 0.0, 0.5, 0.0);
    }

    // Draw fruit
    drawRect(fruit.first, fruit.second, 1.0, 0.0, 0.0);

    // Display score
    glColor3f(1.0, 1.0, 1.0);
    glRasterPos2i(10, height - 20);
    string scoreStr = "Score: " + to_string(score);
    for (char const& c : scoreStr) {
        glutBitmapCharacter(GLUT_BITMAP_HELVETICA_18, c);
    }

    glutSwapBuffers();
}

void update(int value) {
    switch (direction) {
    case 0: // Up
        snakeY += gridSize;
        break;
    case 1: // Down
        snakeY -= gridSize;
        break;
    case 2: // Left
        snakeX -= gridSize;
        break;
    case 3: // Right
        snakeX += gridSize;
        break;
    }

    // Check for collision with fruit
    if (snakeX == fruit.first && snakeY == fruit.second) {
        snake.push_back(make_pair(fruit.first, fruit.second));
        fruit.first = (rand() % (width / gridSize)) * gridSize;
        fruit.second = (rand() % (height / gridSize)) * gridSize;
        score++; // Increment score when fruit is collected
    }

    // Move the tail
    if (!snake.empty()) {
        snake.pop_back();
        snake.insert(snake.begin(), make_pair(snakeX, snakeY));
    }

    // Check for self collision
    for (size_t i = 1; i < snake.size(); ++i) {
        if (snake[i].first == snakeX && snake[i].second == snakeY) {
            cout << "Game Over!" << endl << "Your score is: " << score << endl;
            exit(0);
        }
    }

    // Check for border collision
    if (snakeX >= width || snakeX < 0 || snakeY >= height || snakeY < 0) {
        cout << "Game Over!" << endl << "Your score is: " << score << endl;
        exit(0);
    }

    glutPostRedisplay();
    glutTimerFunc(100, update, 0);
}

void handleKeypress(unsigned char key, int x, int y) {
    switch (key) {
    case 'w':
        if (direction != 1) // avoid reversing direction
            direction = 0;
        break;
    case 's':
        if (direction != 0) // avoid reversing direction
            direction = 1;
        break;
    case 'a':
        if (direction != 3) // avoid reversing direction
            direction = 2;
        break;
    case 'd':
        if (direction != 2) // avoid reversing direction
            direction = 3;
        break;
    }
}

int main(int argc, char** argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
    glutInitWindowSize(width, height);
    glutCreateWindow("Snake Game");
    glutDisplayFunc(draw);
    glutKeyboardFunc(handleKeypress);
    glutTimerFunc(100, update, 0);
    init();
    glutMainLoop();
    return 0;
}