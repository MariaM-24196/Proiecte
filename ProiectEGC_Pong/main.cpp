#include <GL/glut.h>
#include <bits/stdc++.h>

// Game states
enum GameState { MENU, GAME, END, PAUSE };

// Current game state
GameState gameState = MENU;

// Define the initial positions and sizes of the paddles and the ball
float paddle1Y = 0.0f, paddle2Y = 0.0f;
float paddleWidth = 0.1f, paddleHeight = 0.4f;
float ballX = 0.0f, ballY = 0.0f, ballRadius = 0.05f;
float ballXSpeed = 0.005f, ballYSpeed = 0.002f;

// Define the dimensions of the screen
const int screenWidth = 800, screenHeight = 600;

// Number of rounds and current score
int score1 = 0, score2 = 0;

// Winner player
int winner = 0;

// Function to draw a rectangle
void drawRectangle(float x, float y, float width, float height)
{
    glPushMatrix();
    glTranslatef(x, y, 0.0f);
    glBegin(GL_QUADS);
    glVertex2f(-width / 2, -height / 2);
    glVertex2f(width / 2, -height / 2);
    glVertex2f(width / 2, height / 2);
    glVertex2f(-width / 2, height / 2);
    glEnd();
    glPopMatrix();
}

// Function to draw the ball
void drawBall()
{
    glColor3f(1.0f, 1.0f, 1.0f); // White color
    drawRectangle(ballX, ballY, ballRadius * 2, ballRadius * 2);
}

// New function to reset the ball's position
void resetBallPosition()
{
    ballX = 0.0f;
    ballY = 0.0f;
}

// Function to check game over
void checkGameOver()
{
    if (score1 >= 5 || score2 >= 5)
    {
        gameState = END;
        winner = (score1 > score2) ? 1 : 2;
    }
}

// Function to draw the paddles
void drawPaddles()
{
    glColor3f(1.0f, 1.0f, 1.0f); // White color
    drawRectangle(-0.9f, paddle1Y, paddleWidth, paddleHeight);
    drawRectangle(0.9f, paddle2Y, paddleWidth, paddleHeight);
}

// Function to handle key presses
void handleKeypress(unsigned char key, int x, int y)
{
    switch (gameState) 
    {
    case MENU:
        if (key == 13) // 13 is ASCII code for Enter
            gameState = GAME;
        else if (key == 27) // ESC key
            exit(0);
        break;
    case GAME:
        // Handling game keypress
        if (key == 'w' && paddle1Y < 0.8)
            paddle1Y += 0.1f;
        else if (key == 's' && paddle1Y > -0.8)
            paddle1Y -= 0.1f;
        else if (key == 'i' && paddle2Y < 0.8)
            paddle2Y += 0.1f;
        else if (key == 'k' && paddle2Y > -0.8)
            paddle2Y -= 0.1f;
        else if (key == 27) // ESC key
            gameState = PAUSE;
        break;
    case PAUSE:
        if (key == 27) // ESC key
            gameState = GAME;
        break;
    case END:
        if (key == 'r' || key == 'R')
        {
            gameState = MENU;
            score1 = 0;
            score2 = 0;
            resetBallPosition();
        }
        else if (key == 'e' || key == 'E')
            exit(0);
        break;
    }
}


// Function to update the position of the ball
void updateBall()
{
    if (gameState != PAUSE)
    {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        // Check for collision with walls
        if (ballY + ballRadius > 1.0f || ballY - ballRadius < -1.0f)
            ballYSpeed = -ballYSpeed;

        // Check for collision with paddles
        if ((ballX - ballRadius < -0.85f && ballY > paddle1Y - paddleHeight / 2 && ballY < paddle1Y + paddleHeight / 2) ||
            (ballX + ballRadius > 0.85f && ballY > paddle2Y - paddleHeight / 2 && ballY < paddle2Y + paddleHeight / 2))
            ballXSpeed = -ballXSpeed;

        // Check if ball went out of bounds
        if (ballX + ballRadius < -1.0f)
        {
            score2++;
            resetBallPosition();
            checkGameOver();
        }
        else if (ballX - ballRadius > 1.0f)
        {
            score1++;
            resetBallPosition();
            checkGameOver();
        }
    }
}

// Function to update the screen
void update(int value)
{
    if (gameState == GAME) updateBall();
    glutPostRedisplay();
    glutTimerFunc(10, update, 0);
}

// Function to draw text
void drawText(const char* text, float x, float y)
{
    glPushMatrix();
    glColor3f(1.0f, 1.0f, 1.0f);
    glRasterPos2f(x, y);
    while (*text)
    {
        glutBitmapCharacter(GLUT_BITMAP_8_BY_13, *text);
        text++;
    }
    glPopMatrix();
}

// Function to draw the scene
void drawMenu()
{
    drawText("Welcome to Pong! =)", -0.3f, 0.4f);
    drawText("First one to 5 points wins!", -0.3f, 0.3f);
    drawText("Press Enter to start", -0.3f, 0.1f);
    drawText("Press ESC to exit", -0.3f, 0.0f);
}


// Function to draw the pause logo in the top left corner
void drawPauseLogo()
{
    glColor3f(1.0f, 1.0f, 1.0f); // Set color to white
    // Calculate normalized coordinates for the top left corner
    float normX = -1.0f + 20.0f / screenWidth; // Adjust x position a bit right from the edge
    float normY = 1.0f - 20.0f / screenHeight; // Adjust y position a bit down from the top
    drawText("PAUSE", normX, normY);
}

// Main drawing function
void draw()
{
    glClear(GL_COLOR_BUFFER_BIT);
    glLoadIdentity();

    if (gameState == MENU) drawMenu();
    else if (gameState == GAME)
    {
        char scoreDisplay[50];
        sprintf(scoreDisplay, "Player 1: %d  Player 2: %d", score1, score2);
        drawText(scoreDisplay, -0.1f, 0.9f);
        drawBall();
        drawPaddles();
    }
    else if (gameState == PAUSE)
    {
        drawPauseLogo(); // Draw pause logo when the game is paused
        drawBall();
        drawPaddles();
    }
    else if (gameState == END)
    {
        char winText[30];
        sprintf(winText, "Player %d wins! :-p", winner);
        drawText(winText, -0.1f, 0.0f);
        drawText("Press R to Restart", -0.15f, -0.1f);
        drawText("Press E to Exit", -0.12f, -0.2f);
    }
    glutSwapBuffers();
}


// Main function
int main(int argc, char** argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
    glutInitWindowSize(screenWidth, screenHeight);
    glutCreateWindow("Pic-Poc :)");
    glutDisplayFunc(draw);
    glutKeyboardFunc(handleKeypress);
    glutTimerFunc(10, update, 0);
    glutMainLoop();
    return 0;
}
