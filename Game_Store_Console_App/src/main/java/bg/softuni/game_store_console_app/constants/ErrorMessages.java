package bg.softuni.game_store_console_app.constants;

public enum ErrorMessages {
    ;
    public static final String INVALID_EMAIL = "Incorrect email";
    public static final String INVALID_PASSWORD = "Incorrect username / password";
    public static final String NOT_MATCHING_PASSWORDS = "Password does not match !";
    public static final String USER_ALREADY_EXISTS= "This user already exists !";
    public static final String USER_ALREADY_LOGGED_IN = "There is already logged user !";
    public static final String NO_USER_FOUND = "There is no such user registered ! ";
    public static final String NO_USER_LOGGED_IN = "Cannot log out. No user was logged in !";
    public static final String INVALID_GAME_TITLE = "Invalid Game Title ! Please try again !";
    public static final String INVALID_PRICE = "Price should be a positive number !";
    public static final String INVALID_TRAILER_URL_LENGTH = "We need exactly 11 symbols";
    public static final String INVALID_IMAGE_NAME = "Name should start wit http or https";
    public static final String TOO_SMALL_DESCRIPTION = "Description size should be at least 20 symbols!";
    public static final String NO_PERMISSION = "The logged user is not an admin ! Only Admins have permission!";
    public static final String ALREADY_EXISTING_GAME = "The game already exists !";
    public static final String INVALID_GAME_ID = "The game does not exists !";
    public static final String NO_USER_LOGGED_IN_TO_PURCHASE = "Cannot purchase a game ! Please log into your account first !";
    public static final String ALREADY_OWN_GAME = "Already own the game";
    public static final String NO_ADDED_GAMES_TO_THE_CARD = "The Card is empty !";
}
