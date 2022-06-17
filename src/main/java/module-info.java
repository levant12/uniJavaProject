module ge.tsu.demoexamcalc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;
    requires java.naming;
    requires slf4j.api;


    opens ge.tsu.demoexamcalc to javafx.fxml;
    exports ge.tsu.demoexamcalc;
    exports ge.tsu.demoexamcalc.controllers;
    opens ge.tsu.demoexamcalc.controllers to javafx.fxml;
}