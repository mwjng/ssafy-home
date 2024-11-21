package ssafy.ssafyhome.image.application;

public enum ImageDirectory {

    PROFILE("member/"),
    HOUSE("house/"),
    DEAL("deal/");

    private final String directory;

    ImageDirectory(final String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }
}
