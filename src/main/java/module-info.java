module com.example.pic_style_changer
{
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;
    requires javafx.swing;

    opens com.example.pic_style_changer to javafx.fxml;
    exports com.example.pic_style_changer;
}
