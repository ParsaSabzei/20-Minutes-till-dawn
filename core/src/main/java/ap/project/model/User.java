package ap.project.model;

import ap.project.model.enums.Avatars;
import ap.project.model.enums.Texts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class User {
    private String username;
    private String password;
    private String securityQuestion;
    private boolean isGuest = false;
    private int score = 0;
    private Avatars avatar = Avatars.Default;
    private int totalKillCount = 0;
    private float totalTimeSurvived = 0;
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static Result<String> usernameValidity(String username) {
        if(username.length() < 4)
            return new Result<>(false, Texts.Username4Character.getText());
        return new Result<>(true, "");
    }
    public static Result<String> passwordValidity(String password) {
        if(password.length() < 8)
            return new Result<>(false, Texts.Password8Character.getText());
        if(!password.matches(".*[a-z].*"))
            return new Result<>(false, Texts.PasswordOneLower.getText());
        if(!password.matches(".*[A-Z].*"))
            return new Result<>(false, Texts.PasswordOneUpper.getText());
        if(!password.matches(".*[0-9].*"))
            return new Result<>(false, Texts.PasswordOneDigit.getText());
        if(!password.matches(".*[@%$#&*()_].*"))
            return new Result<>(false, Texts.PasswordSpecialChar.getText());
        return new Result<>(true, "");
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }

    public Avatars getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatars avatar) {
        this.avatar = avatar;
    }
    public String getAvatarPath() {
        return "avatar/"+username+".png";
    }

    public float getTotalTimeSurvived() {
        return totalTimeSurvived;
    }

    public void setTotalTimeSurvived(float totalTimeSurvived) {
        this.totalTimeSurvived = totalTimeSurvived;
    }

    public int getTotalKillCount() {
        return totalKillCount;
    }

    public void setTotalKillCount(int totalKillCount) {
        this.totalKillCount = totalKillCount;
    }
}
