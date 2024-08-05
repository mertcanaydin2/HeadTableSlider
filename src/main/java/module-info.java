module greedz.corp.headtablemasaustu {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires java.desktop;
    requires javafx.swing;


    opens greedz.corp.headtablemasaustu to javafx.fxml;
    exports greedz.corp.headtablemasaustu;
}
