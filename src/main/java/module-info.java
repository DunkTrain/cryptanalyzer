module cryptanalyzer {
    requires javafx.controls;
    requires javafx.fxml;

    exports ru.cooper.cryptanalyzer;

    opens ru.cooper.cryptanalyzer.controllers.ui to javafx.fxml;

    uses ru.cooper.cryptanalyzer.CryptAnalyzerApp;
}