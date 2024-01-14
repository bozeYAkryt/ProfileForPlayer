package boze_kak_ya_kryt.pofile;

public @interface Command {
    String name();

    String[] aliases();

    String permission();
}
